<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 4.06.21
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Home</h1>
<form method="get" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="logout">
    <input type="submit" class="login-btn" value="Logout">
</form>
</body>
</html>
