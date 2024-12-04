<%@ page import="com.formation.jee.models.Operation" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Récupérer les opérations à partir de l'attribut de requête
    List<Operation> operations = (List<Operation>) request.getAttribute("operations");
%>

<h2>Operations for Account ID: <%= request.getParameter("accountId") %>
</h2>

<form action="operations/new-operation.jsp">
    <input type="hidden" name="accountId" value="<%= request.getParameter("accountId") %>">

    <input type="submit" value="Add new operation"/>
</form>

<table border="1">
    <tr>
        <th>Operation</th>
        <th>Date</th>
        <th>Amount</th>
        <th style="min-width: 80px"></th>
    </tr>

    <%
        if (operations != null && !operations.isEmpty()) {
            for (Operation operation : operations) {
    %>
    <tr>
        <td>
            <%= operation.getDescription() %>
        </td>
        <td>
            <%= operation.getDate() %>
        </td>
        <td>
            <%= operation.getAmount() %>
        </td>
        <td style="display: flex; align-items: center; justify-content: center;">
            <form method="post" style="margin: 0;">
                <input type="hidden" name="operationId" value="<%= operation.getId() %>">
                <input type="hidden" name="actionType" value="DELETE">

                <input type="submit" value="Delete"/>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">No operations found for this account.</td>
    </tr>
    <%
        }
    %>
</table>
