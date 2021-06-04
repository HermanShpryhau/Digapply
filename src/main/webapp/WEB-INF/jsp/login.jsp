<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 4.06.21
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<h1>Login</h1>
<div class="login-form">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="login">
        <input class="input-field" type="text" name="email" placeholder="Login" value="${requestScope.login}" />
        <input class="input-field" type="password" name="password" placeholder="Password" />
        <input class="submit-btn" type="submit" value="Login"/>
    </form>
</div>
</body>
</html>
