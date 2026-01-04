package io.github.enelrith.bluebay.security.utilities;

import io.github.enelrith.bluebay.users.entities.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new AccessDeniedException("No authenticated user");
        }
        return user;
    }

    public static long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
