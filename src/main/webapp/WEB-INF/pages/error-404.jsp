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
    <title><fmt:message key="error.error-404-title"/> | Digapply</title>
</head>
<body class="d-flex flex-column vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container h-100">
    <div class="h-100 d-flex flex-column align-items-center justify-content-center">
        <div>
            <h1 class="text-center"><fmt:message key="error.error-404-head"/></h1>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/controller?command=home" class="btn btn-primary btn-lg mt-5">
                <fmt:message key="error.return-home"/>
            </a>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>

</body>
</html>
