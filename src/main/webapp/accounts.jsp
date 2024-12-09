<%@ page import="com.formation.jee.models.Account" %>
<%@ page import="java.util.List" %>

<%
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
%>

<%
    if (request.isUserInRole("accounts.write")) {
%>
<form action="accounts/new-account.jsp" style="margin-bottom: 5px">
    <input type="submit" value="Add an account"/>
</form>
<% } %>

<table border="1">
    <tr>
        <th>Account Number</th>
        <th>Account Name</th>
        <th style="min-width: 80px;"></th>
    </tr>

    <%
        if (accounts != null) {
            for (Account account : accounts) {
    %>
    <tr>
        <td>
            <%= account.getNumber() %>
        </td>
        <td>
            <a href="operations?accountId=<%= account.getNumber() %>">
                <%= account.getName() %>
            </a>
        </td>
        <td style="display: flex; align-items: center; justify-content: center; min-height: 23px;">
            <%
                if (request.isUserInRole("accounts.write")) {
            %>
            <form action="accounts/edit-account.jsp" style="margin: 0;">
                <input type="submit" value="Edit"/>
                <input type="hidden" name="accountId" value="<%= account.getNumber() %>">
            </form>
            <% } %>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
