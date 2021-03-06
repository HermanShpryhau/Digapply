<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!doctype html>
<html>
<head>
    <%@ include file="components/head-tags.jsp" %>
    <title><fmt:message key="profile.change-password"/> | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col"></div>
        <div class="col-6">
            <h1 class="mt-5 fw-bold"><fmt:message key="profile.change-password"/></h1>

            <c:if test="${requestScope.error_key != null}">
                <div class="alert alert-danger mt-3 mb-3" role="alert">
                    <strong><i class="bi bi-exclamation-triangle-fill"></i> <fmt:message key="${requestScope.error_key}"/></strong>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/controller?command=update-password" method="post">
                <div class="mb-3">
                    <label for="passwordInput" class="form-label"><fmt:message key="form.password"/></label>
                    <input name="password" type="password" class="form-control" id="passwordInput" placeholder="<fmt:message key="form.password"/>">
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="profile.change-password"/></button>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>
</body>
</html>
