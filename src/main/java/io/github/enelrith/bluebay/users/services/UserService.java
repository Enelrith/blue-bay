package io.github.enelrith.bluebay.users.services;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.github.enelrith.bluebay.roles.dto.AddRoleToUserRequest;
import io.github.enelrith.bluebay.roles.exceptions.RoleNotFoundException;
import io.github.enelrith.bluebay.roles.repositories.RoleRepository;
import io.github.enelrith.bluebay.security.exceptions.ForbiddenAccessException;
import io.github.enelrith.bluebay.security.utilities.SecurityUtil;
import io.github.enelrith.bluebay.users.dto.*;
import io.github.enelrith.bluebay.users.entities.UserInformation;
import io.github.enelrith.bluebay.users.exceptions.UserAlreadyExistsException;
import io.github.enelrith.bluebay.users.exceptions.UserInformationAlreadyExistsException;
import io.github.enelrith.bluebay.users.exceptions.UserNotFoundException;
import io.github.enelrith.bluebay.users.mappers.UserInformationMapper;
import io.github.enelrith.bluebay.users.mappers.UserMapper;
import io.github.enelrith.bluebay.users.repositories.UserInformationRepository;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
    private final UserInformationRepository userInformationRepository;
    private final UserInformationMapper userInformationMapper;

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

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public GetUserResponse getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toGetUserResponse(user);
    }

    /**
     * Deletes a user by their id.
     * If not an admin, a user can only delete their own information.
     * @param id The id of the user that is to be deleted
     */
    @Transactional
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public UpdateUserEmailResponse updateUserEmailById(Long id, String email) {
        var user =  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEmail(email);
        userRepository.save(user);

        return userMapper.toUpdateUserEmailResponse(user);
    }

    @Transactional
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public UpdateUserPasswordResponse updateUserPasswordById(Long id, String password) {
        var user =  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return userMapper.toUpdateUserPasswordResponse(user);
    }

    @Transactional
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public void addRoleToUser(Long id, AddRoleToUserRequest request) {
        var role = roleRepository.findByName(request.name()).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.getRoles().add(role);

        userRepository.save(user);
    }

    @Transactional
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public AddUserInformationResponse addUserInformation(Long id, AddUserInformationRequest request) {
        if (userInformationRepository.existsById(id)) throw new UserInformationAlreadyExistsException("This user already has a profile");
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        var userInformation = userInformationMapper.toEntity(request);
        userInformation.setUser(user);
        userInformation.setCompletedAt(Instant.now());
        user.getRoles().add(roleRepository.findByName(RoleNames.COMPLETED_ACCOUNT).orElseThrow(() -> new RoleNotFoundException("Role not found")));

        userInformationRepository.save(userInformation);

        return userInformationMapper.toAddUserInformationResponse(userInformation);
    }

    @Transactional
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public AddUserInformationResponse updateUserInformation(Long id, UpdateUserInformationRequest request) {
        var user = userRepository.findUserWithRolesById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setUserInformation(userInformationMapper.toEntity(request, user.getUserInformation()));
        var userInformation = user.getUserInformation();

        if (nullExistsInUserInformation(userInformation)) {
            var userRoles = user.getRoles();
            var completedAccountRole = roleRepository.findByName(RoleNames.COMPLETED_ACCOUNT).orElseThrow(() ->
                    new RoleNotFoundException("Role not found"));

            userRoles.remove(completedAccountRole);
            userInformation.setCompletedAt(null);
        } else {
            if (userInformation.getCompletedAt() == null) {
                var userRoles = user.getRoles();
                var completedAccountRole = roleRepository.findByName(RoleNames.COMPLETED_ACCOUNT).orElseThrow(() ->
                        new RoleNotFoundException("Role not found"));

                userRoles.add(completedAccountRole);
                userInformation.setCompletedAt(Instant.now());
            }
        }
        return userInformationMapper.toAddUserInformationResponse(userInformation);
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
    private boolean nullExistsInUserInformation(UserInformation userInformation) {
        if (userInformation.getFirstName() != null && userInformation.getLastName() != null && userInformation.getDateOfBirth() != null
                && userInformation.getNationality() != null && userInformation.getIdDocumentType() != null
                && userInformation.getIdDocumentNumber() != null) {
            return false;
        }
        return true;
    }
}
