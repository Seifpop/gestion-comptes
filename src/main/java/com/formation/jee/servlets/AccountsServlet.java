package com.formation.jee.servlets;

import com.formation.jee.service.AccountService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/accounts")
public class AccountsServlet extends HttpServlet {
    @Inject
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.isUserInRole("accounts.read")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this resource.");
            return;
        }

        req.setAttribute("accounts", accountService.getAccounts());
        req.getRequestDispatcher("/accounts.jsp").forward(req, resp);
    }
}
