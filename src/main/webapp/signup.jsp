<%@ page import="com.formation.jee.service.UserService" %>
<%@ page import="static com.formation.jee.utils.BeanInjection.resolve" %>
<%@ page import="java.util.Objects" %>

<jsp:include page="shared/layout.jsp">
    <jsp:param name="title" value="Signup | FilRouge"/>
</jsp:include>

<%
    UserService userService = resolve(UserService.class);
    assert userService != null : "User service is necessary for registration.";

    if ("POST".equals(request.getMethod())) {
        String username = request.getParameter("username");

        try {
            userService.signUp(username, request.getParameter("password"));

            response.addCookie(userService.prepareAuthCookie(username));
            response.sendRedirect(request.getContextPath() + "/");
            return;
        } catch (Exception e) {
            request.setAttribute("error-message", e.getMessage());
        }
    }
%>

<%
    String message = Objects.toString(request.getAttribute("error-message"), null);
    if (message != null && !message.isEmpty()) {
%>
<div class="alert-danger">
    <%=message%>
</div>
<% } %>

<form method="post">
    <div>
        <h1>Register</h1>
    </div>

    <div>
        <label for="username">Enter your username</label>
        <input id="username" type="text" placeholder="Enter Username" name="username" required/>
    </div>

    <div>
        <label for="password">Enter your Password</label>
        <input id="password" type="password" placeholder="Enter Your Password" name="password" required/>
    </div>

    <div>
        <button type="submit">Register</button>
    </div>

    <div>
        <a href="<%=request.getContextPath()+"/login"%>">Login Here!</a>
    </div>
</form>
