package io.github.enelrith.bluebay.users.services;

import io.github.enelrith.bluebay.users.dto.RegisterUserRequest;
import io.github.enelrith.bluebay.users.dto.RegisterUserResponse;
import io.github.enelrith.bluebay.users.exceptions.UserAlreadyExistsException;
import io.github.enelrith.bluebay.users.mappers.UserMapper;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related logic.
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     *
     * @param request the registration request containing user details
     * @return a response object containing the registered user's details
     * @throws UserAlreadyExistsException if a user with the given email already exists
     */
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        if (userExists(request.email())) throw new UserAlreadyExistsException("User with email: "+ request.email() + " already exists");

        var user =  userMapper.toEntity(request);
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        var savedUser = userRepository.save(user);

        return userMapper.toRegisterResponse(savedUser);
    }


    /**
     * Checks if a user with a specific email exists
     * @param email Email to check for
     * @return true if the user exists, false if not
     */
    private boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
