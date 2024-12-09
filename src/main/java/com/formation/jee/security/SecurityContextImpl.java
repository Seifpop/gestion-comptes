package com.formation.jee.security;

import com.formation.jee.models.User;

import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import static javax.security.enterprise.AuthenticationStatus.SUCCESS;

/**
 * Providing a basic implementation of the security context.
 */
public class SecurityContextImpl implements SecurityContext {
    // The principal associated with the current security context
    private Principal principal;

    @Override
    public Principal getCallerPrincipal() {
        return principal;
    }

    /**
     * Sets the principal associated with the current security context.
     *
     * @param principal The principal to be associated with the current security context.
     */
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public <T extends Principal> Set<T> getPrincipalsByType(Class<T> pType) {
        return Collections.emptySet();
    }

    @Override
    public boolean isCallerInRole(String role) {
        if (principal instanceof User) {
            User user = (User) principal;
            return user.hasRole(role);
        }

        return true;
    }

    @Override
    public boolean hasAccessToWebResource(String resource, String... methods) {
        return true;
    }

    @Override
    public AuthenticationStatus authenticate(HttpServletRequest request, HttpServletResponse response, AuthenticationParameters parameters) {
        return SUCCESS;
    }
}
