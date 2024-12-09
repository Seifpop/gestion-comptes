package com.formation.jee.filters;

import com.formation.jee.security.SecurityContextImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Ensures the security context is properly set for each incoming HTTP request.
 */
public final class SecurityContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Create a new SecurityContextImpl object and attach it to the request
        request.setAttribute(SecurityContextFilter.class.getName(), new SecurityContextImpl());

        chain.doFilter(request, response);
    }
}
