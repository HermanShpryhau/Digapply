<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 7.07.21
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Sign In | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>

<main class="container">
    <div class="row justify-content-center">
        <div class="col"></div>
        <div class="col-6">
            <h1 class="mt-5 fw-bold">Sign In</h1>

            <c:if test="${sessionScope.login_error != null}">
                <div class="alert alert-danger fade show" role="alert">
                    <strong>Error!</strong> You have entered invalid email or password.
                </div>
            </c:if>

            <form action="/controller?command=login" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Email address</label>
                    <input name="email" type="email" class="form-control" id="exampleInputEmail1"
                           aria-describedby="emailHelp" placeholder="Email">
                </div>
                <div class="mb-3">
                    <label for="passwordInput" class="form-label">Password</label>
                    <input name="password" type="password" class="form-control" id="passwordInput" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-primary">Sign In</button>
            </form>
            <hr class="dropdown-divider">
            <p class="text-muted">Don't have an account? <a href="#">Sign Up</a></p>
        </div>
        <div class="col"></div>
    </div>
</main>

<jsp:include page="components/footer.jsp"/>
</body>
</html>
