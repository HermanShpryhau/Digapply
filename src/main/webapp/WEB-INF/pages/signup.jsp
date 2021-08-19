<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="components/head-tags.jsp" %>
    <title><fmt:message key="form.sign-up"/> | Digapply</title>
</head>

<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>


<div class="container">
    <div class="row justify-content-center">
        <div class="col-sm"></div>
        <div class="col-sm-6">
            <h1 class="mt-5 mb-3 fw-bold"><fmt:message key="form.sign-up"/></h1>

            <c:if test="${requestScope.error_key != null}">
                <div class="alert alert-danger mt-3 mb-3" role="alert">
                    <strong><i class="bi bi-exclamation-triangle-fill"></i> <fmt:message key="${requestScope.error_key}"/></strong>
                </div>
            </c:if>

            <div id="password-mismatch-alert" class="mt-3 mb-3 alert alert-danger" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <span> <fmt:message key="form.password-match"/></span>
            </div>

            <form action="${pageContext.request.contextPath}/controller?command=signup" method="post">
                <div class="row mb-3">
                    <div class="col-sm mb-3">
                        <label for="first-name" class="form-label"><fmt:message key="form.firs-name"/></label>
                        <input type="text" id="first-name" name="first-name" class="form-control"
                               placeholder="<fmt:message key="form.firs-name"/>" required maxlength="45"
                               pattern="^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]$">
                    </div>
                    <div class="col-sm">
                        <label for="last-name" class="form-label"><fmt:message key="form.last-name"/></label>
                        <input type="text" id="last-name" name="last-name" class="form-control"
                               placeholder="<fmt:message key="form.last-name"/>" required maxlength="45"
                               pattern="^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]$">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label"><fmt:message key="form.email-address"/></label>
                    <input type="email" class="form-control" name="email" id="email" placeholder="<fmt:message key="form.email-placeholder"/>" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label"><fmt:message key="form.password"/></label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="form.password"/>" required
                           minlength="8">
                </div>
                <div class="mb-3">
                    <label for="confirm-password" class="form-label"><fmt:message key="form.confirm-password"/></label>
                    <input type="password" class="form-control" name="confirm-password" id="confirm-password" placeholder="<fmt:message key="form.password"/>" required
                           minlength="8">
                </div>
                <div class="col-12">
                    <button id="sign-up-btn" class="btn btn-primary" type="submit"><fmt:message key="form.sign-up-btn"/></button>
                </div>
            </form>
            <hr class="dropdown-divider">
            <p class="text-muted"><fmt:message key="form.have-account"/><a href="${pageContext.request.contextPath}/controller?command=show-sign-in"><fmt:message key="form.sign-in-btn"/></a></p>
        </div>
        <div class="col-sm"></div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>
<script src="scripts/signup.js"></script>

</body>

</html>