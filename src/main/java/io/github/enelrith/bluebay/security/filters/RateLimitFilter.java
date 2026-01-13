package io.github.enelrith.bluebay.security.filters;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.enelrith.bluebay.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private static final Map<String, RateLimitConfig> PATH_LIMITS = Map.of(
            "/properties", new RateLimitConfig(50, 50, 1),
            "/users", new RateLimitConfig(50, 50, 1),
            "/bookings", new RateLimitConfig(50, 50, 1),
            "/amenities", new RateLimitConfig(50, 50, 1),
            "/roles", new RateLimitConfig(50, 50, 1),
            "/reviews", new RateLimitConfig(50, 50, 1)
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        RateLimitConfig config = getConfigForPath(path);

        if (config == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String key = getClientKey(request) + ":" + path;
        Bucket bucket = resolveBucket(key, config);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            filterChain.doFilter(request, response);
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests");
        }
    }

    private record RateLimitConfig(int capacity, int refillTokens, int refillMinutes) {}

    private RateLimitConfig getConfigForPath(String path) {
        return PATH_LIMITS.entrySet().stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    private Bucket resolveBucket(String key, RateLimitConfig config) {
        return cache.computeIfAbsent(key, k -> createNewBucket(config));
    }

    private Bucket createNewBucket(RateLimitConfig config) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(config.capacity)
                .refillGreedy(config.refillTokens, Duration.ofMinutes(config.refillMinutes))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private String getClientKey(HttpServletRequest request) {
        String userId = extractUserIdFromJWT(request);
        return userId != null ? "user:" + userId : "ip:" + request.getRemoteAddr();
    }

    private String extractUserIdFromJWT(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Long userId = jwtService.extractId(token);
                return userId != null ? userId.toString() : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}