<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!doctype html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title><fmt:message key="error.error-head"/> | Digapply</title>
</head>
<body class="d-flex flex-column vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <div class="vh-100 d-flex align-items-center justify-content-center">
        <h1>You must have messed something up.</h1>
        <a href="/controller?command=home" class="btn btn-primary btn-lg mt-5"><fmt:message key="error.return-home"/></a>
    </div>
</div>

<div class="container h-100">
    <div class="col-6">
        <div class="h-100 d-flex flex-column align-items-center justify-content-center">
            <div class="alert alert-danger" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <h1 class="text-center"><fmt:message key="error.error-head"/></h1>
                <fmt:message key="${requestScope.error_key}"/>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/controller?command=home" class="btn btn-primary btn-lg mt-5">
                    <fmt:message key="error.return-home"/>
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
