<%@ page import="java.util.Objects" %>
<%@ page import="com.formation.jee.service.AccountService" %>
<%@ page import="static com.formation.jee.utils.BeanInjection.resolve" %>
<%@ page import="com.formation.jee.models.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String verb = request.getMethod();
    AccountService accountService = resolve(AccountService.class);
    assert accountService != null : "Account service is necessary for accessing the database.";
    Account account = null;

    if ("POST".equals(verb)) {
        account = new Account(request.getParameter("accountNumber"), request.getParameter("name"));
        try {
            accountService.editAccount(account);

            response.sendRedirect("../accounts");
        } catch (Exception e) {
            request.setAttribute("error-message", e.getMessage());
        }
    } else if ("GET".equals(verb)) {
        account = accountService.getAccountByNumber(request.getParameter("accountId"));
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/accounts");
            return;
        }
    }
%>


<jsp:include page="../shared/layout.jsp">
    <jsp:param name="title" value="Edit account | FilRouge"/>
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
        <input id="number" name="accountNumber" type="text" value="<%=account.getNumber()%>" required/>
    </div>
    <div>
        <label for="name">Name</label>
        <input id="name" name="name" type="text" value="<%=account.getName()%>" required/>
    </div>

    <div>
        <input type="submit" value="Edit account" style="margin-top: 5px;"/>
    </div>
</form>
