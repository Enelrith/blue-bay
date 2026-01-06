package io.github.enelrith.bluebay.users.services;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.github.enelrith.bluebay.roles.exceptions.RoleNotFoundException;
import io.github.enelrith.bluebay.roles.repositories.RoleRepository;
import io.github.enelrith.bluebay.security.exceptions.ForbiddenAccessException;
import io.github.enelrith.bluebay.security.utilities.SecurityUtil;
import io.github.enelrith.bluebay.users.dto.*;
import io.github.enelrith.bluebay.users.exceptions.UserAlreadyExistsException;
import io.github.enelrith.bluebay.users.exceptions.UserNotFoundException;
import io.github.enelrith.bluebay.users.mappers.UserMapper;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing user-related logic.
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Registers a new user in the system.
     *
     * @param request the registration request containing user details
     * @return a response object containing the registered user's details
     * @throws UserAlreadyExistsException if a user with the given email already exists
     */
    @Transactional
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        if (userExists(request.email())) throw new UserAlreadyExistsException("User with email: "+ request.email() + " already exists");

        var user =  userMapper.toEntity(request);
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        var role = roleRepository.findByName(RoleNames.USER).orElseThrow(() -> new RoleNotFoundException("Invalid role"));
        user.getRoles().add(role);

        var savedUser = userRepository.save(user);

        return userMapper.toRegisterResponse(savedUser);
    }

    public GetUserResponse getUserById(Long id) {
        isUserForbidden(id);

        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toGetUserResponse(user);
    }

    /**
     * Deletes a user by their id.
     * If not an admin, a user can only delete their own information.
     * @param id The id of the user that is to be deleted
     */
    @Transactional
    public void deleteUserById(Long id) {
        isUserForbidden(id);

        userRepository.deleteById(id);
    }

    @Transactional
    public UpdateUserEmailResponse updateUserEmailById(Long id, String email) {
        isUserForbidden(id);

        var user =  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEmail(email);
        userRepository.save(user);

        return userMapper.toUpdateUserEmailResponse(user);
    }

    @Transactional
    public UpdateUserPasswordResponse updateUserPasswordById(Long id, String password) {
        isUserForbidden(id);

        var user =  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return userMapper.toUpdateUserPasswordResponse(user);
    }

    /**
     * Checks if a user with a specific email exists
     * @param email Email to check for
     * @return true if the user exists, false if not
     */
    private boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
    private boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Checks if the current user's id matches the id of the user it is trying to access.
     * @param id The id of the user the current user is trying to access
     * @throws ForbiddenAccessException thrown when the current user's id doesn't match the id in the request
     */
    private void isUserForbidden(Long id) {
        var currentUserId = SecurityUtil.getCurrentUserId();
        if (!id.equals(currentUserId)) throw new ForbiddenAccessException("You are not allowed to access this content");
    }
}
