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
    <title><fmt:message key="application.new-app-title"/> | Digapply</title>
</head>
<body class="d-flex flex-column vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container h-100">
    <div class="h-100 d-flex flex-column align-items-center justify-content-center">
        <div>
            <h1 class="text-center"><i class="bi bi-exclamation-circle-fill"></i> <fmt:message key="application.only-one"/></h1>
        </div>
        <div class="d-grid gap-2 d-md-block">
            <a href="${pageContext.request.contextPath}/controller?command=profile" class="btn btn-primary btn-lg mt-5">
                <fmt:message key="application.view-submitted-application"/>
            </a>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>

</body>
</html>
