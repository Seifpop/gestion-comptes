package com.formation.jee.servlets;

import com.formation.jee.models.Operation;
import com.formation.jee.service.OperationService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/operations")
public class OperationServlet extends HttpServlet {
    @Inject
    private OperationService operationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        List<Operation> operations = operationService.getOperations(accountId); // Récupérer les opérations pour un compte

        req.setAttribute("operations", operations);
        req.getRequestDispatcher("/operations.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionType = req.getParameter("actionType");
        if ("DELETE".equals(actionType)) {
            doDelete(req, resp);
        }

        String redirectUrl = req.getHeader("referer");
        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = req.getContextPath() + "/operations";
        }
        resp.sendRedirect(redirectUrl);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operationId = req.getParameter("operationId");
        operationService.removeOperation(operationId);
    }
}
