<%@ page import="java.util.Objects" %>
<%@ page import="com.formation.jee.service.OperationService" %>
<%@ page import="static com.formation.jee.utils.BeanInjection.resolve" %>
<%@ page import="com.formation.jee.models.Operation" %>
<%@ page import="com.formation.jee.models.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if ("POST".equals(request.getMethod())) {
        OperationService operationService = resolve(OperationService.class);
        assert operationService != null : "Operation service is necessary for accessing the database.";

        Operation operation = new Operation(request.getParameter("operationDate"), Double.parseDouble(request.getParameter("amount")), request.getParameter("description"));
        Account account = new Account(request.getParameter("accountId"), null);
        operation.setAccount(account);

        try {
            operationService.addOperation(operation);

            response.sendRedirect("../operations?accountId=" + request.getParameter("accountId"));
        } catch (Exception e) {
            request.setAttribute("error-message", e.getMessage());
        }
    }
%>

<jsp:include page="../shared/layout.jsp">
    <jsp:param name="title" value="Add operation | FilRouge"/>
</jsp:include>

<%
    String message = Objects.toString(request.getAttribute("error-message"), null);
    if (message != null && !message.isEmpty()) {
%>
<div class="alert-danger">
    <%=message%>
</div>
<% } %>

<form method="POST">
    <div>
        <label for="amount">Amount</label>
        <input id="amount" name="amount" type="number" required/>
    </div>
    <div>
        <label for="description">Description</label>
        <input id="description" name="description" type="text" required/>
    </div>
    <div>
        <label for="date">Operation date</label>
        <input id="date" name="operationDate" type="date" required/>
    </div>

    <div>
        <input type="submit" value="Add operation" style="margin-top: 5px;"/>
    </div>
</form>

