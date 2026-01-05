package io.github.enelrith.bluebay.security.services;

import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserDetailsService interface.
 * Loads user-specific data during authentication.
 */
@Service
@AllArgsConstructor
public class BbUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads the user details by their email.
     *
     * @param email the email of the user
     * @return the UserDetails object containing user information
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return userRepository.findUserWithRolesByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }
}
