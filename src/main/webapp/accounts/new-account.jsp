<%@ page import="com.formation.jee.models.Account" %>
<%@ page import="com.formation.jee.service.AccountService" %>
<%@ page import="static com.formation.jee.utils.BeanInjection.resolve" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if ("POST".equals(request.getMethod())) {
        AccountService accountService = resolve(AccountService.class);
        assert accountService != null : "Account service is necessary for accessing the database.";

        Account account = new Account(request.getParameter("accountNumber"), request.getParameter("name"));
        try {
            accountService.addAccount(account);

            response.sendRedirect("../accounts");
        } catch (Exception e) {
            request.setAttribute("error-message", e.getMessage());
        }
    }
%>

<jsp:include page="../shared/layout.jsp">
    <jsp:param name="title" value="Add account | FilRouge"/>
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
        <label for="number">Account number</label>
        <input id="number" name="accountNumber" type="text" required/>
    </div>
    <div>
        <label for="name">Name</label>
        <input id="name" name="name" type="text" required/>
    </div>

    <div>
        <input type="submit" value="Add an account" style="margin-top: 5px;"/>
    </div>
</form>
