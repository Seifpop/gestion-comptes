package com.formation.jee.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.formation.jee.filters.AuthFilter.AUTHORIZATION_NAME;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 3277701235861806502L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the JWT token from the Authorization cookie.
        Cookie token = Arrays.stream(req.getCookies())
                .filter(cookie -> AUTHORIZATION_NAME.equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        if (token != null) {
            // Invalidate the token by setting its max age to 0.
            token.setMaxAge(0);

            resp.addCookie(token);
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
