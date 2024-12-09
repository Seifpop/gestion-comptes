package com.formation.jee.filters;

import javax.security.enterprise.SecurityContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.security.Principal;

/**
 * Wraps the incoming {@link ServletRequest} object with a custom implementation.
 * <p>
 * This filter is used to override the behavior of the {@link HttpServletRequest#getUserPrincipal()} and {@link HttpServletRequest#isUserInRole(String)}.
 */
public class RequestWrapperFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Retrieve the SecurityContext from the request attribute
        SecurityContext securityContext = (SecurityContext) request.getAttribute(SecurityContextFilter.class.getName());

        // Wrap the incoming HttpServletRequest with a custom HttpServletRequestWrapper
        chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) request) {

            /**
             * Retrieves the principal from the {@link SecurityContext}.
             *
             * @return The principal from the {@link SecurityContext}.
             */
            @Override
            public Principal getUserPrincipal() {
                return securityContext.getCallerPrincipal();
            }

            /**
             * Check if the caller is in the specified role using the {@link SecurityContext}.
             *
             * @param role The role to check.
             * @return {@code true} if the caller is in the specified role, {@code false} otherwise.
             */
            @Override
            public boolean isUserInRole(String role) {
                return securityContext.isCallerInRole(role);
            }
        }, response);
    }
}
