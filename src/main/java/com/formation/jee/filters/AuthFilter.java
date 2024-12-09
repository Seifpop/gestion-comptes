package com.formation.jee.filters;

import com.formation.jee.models.User;
import com.formation.jee.security.JwtProvider;
import com.formation.jee.security.SecurityContextImpl;
import com.formation.jee.service.UserService;
import io.jsonwebtoken.JwtException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Handles authentication and authorization for incoming HTTP requests.
 * <p>
 * It checks the request path, verifies the JWT token in the Authorization cookie,
 * and sets the authenticated user in the security context.
 */
public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class.getName());
    private static final String[] EXCLUDED_PATHS = {"/login", "/signup"};
    public static final String AUTHORIZATION_NAME = "Authorization";

    @Inject
    private JwtProvider jwtProvider;

    @Inject
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String currentPath = httpRequest.getServletPath();
            if (Stream.of(EXCLUDED_PATHS).anyMatch(currentPath::equals)) {
                LOGGER.log(Level.FINE, "The current path {} marked as whitelist.", currentPath);

                // The current path is in the allowlist, so the filter passes the request to the next filter in the chain.
                chain.doFilter(request, response);
                return;
            }

            // Retrieve the JWT token from the Authorization cookie.
            Cookie token = Arrays.stream(httpRequest.getCookies())
                    .filter(cookie -> AUTHORIZATION_NAME.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            if (token != null) {
                try {
                    // Extract the username from the JWT token.
                    String username = jwtProvider.extractUsername(token.getValue());

                    // Retrieve the user from the UserService.
                    User user = userService.findByUsername(username);

                    if (user != null) {
                        // Set the authenticated user in the security context.
                        SecurityContextImpl securityContext = (SecurityContextImpl) request.getAttribute(SecurityContextFilter.class.getName());
                        securityContext.setPrincipal(user);
                    }

                    // Pass the request to the next filter in the chain.
                    chain.doFilter(request, response);
                    return;
                } catch (JwtException e) {
                    LOGGER.log(Level.SEVERE, "The token attempt to parse is invalid: {}", token);
                }
            }

            // If the token is invalid or not present, set the response status to 401 Unauthorized and redirect to the login page.
            if (response instanceof HttpServletResponse) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                if (token != null) {
                    // Invalidate the token by setting its max age to 0.
                    token.setMaxAge(0);
                    httpResponse.addCookie(token);
                }
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");

                return;
            }
        }

        chain.doFilter(request, response);
    }
}
