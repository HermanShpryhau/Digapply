<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>

<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Sign In | Digapply</title>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col"></div>
        <div class="col-6">
            <h1 class="mt-5 fw-bold"><fmt:message key="form.sign-in"/> </h1>

            <c:if test="${sessionScope.login_error != null}">
                <div class="alert alert-danger fade show" role="alert">
                    <fmt:message key="form.sign-in-error"/>
                </div>
            </c:if>

            <form action="/controller?command=login" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label"><fmt:message key="form.email-address"/></label>
                    <input name="email" type="email" class="form-control" id="exampleInputEmail1"
                           aria-describedby="emailHelp" placeholder="<fmt:message key="form.email-placeholder"/>">
                </div>
                <div class="mb-3">
                    <label for="passwordInput" class="form-label"><fmt:message key="form.password"/></label>
                    <input name="password" type="password" class="form-control" id="passwordInput" placeholder="<fmt:message key="form.password"/>">
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="form.sign-in-btn"/></button>
            </form>
            <hr class="dropdown-divider">
            <p class="text-muted"><fmt:message key="form.no-account"/><a href="/controller?command=show-sign-up"><fmt:message key="form.sign-up-btn"/></a></p>
        </div>
        <div class="col"></div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>
</body>
</html>
