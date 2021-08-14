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
    <title><fmt:message key="dashboard.title"/></title>
</head class="d-flex flex-column min-vh-100">
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3"><fmt:message key="dashboard.actions"/></h1>
    <div class="row row-cols-sm-4">
        <div class="col-sm mb-2">
            <div class="card">
                <div class="card-body  d-grid gap-2">
                    <h5 class="card-title"><fmt:message key="dashboard.users-actions"/></h5>
                    <a href="${pageContext.request.contextPath}/controller?command=manage-users " class="btn btn-primary"><fmt:message key="dashboard.manage-users"/></a>
                </div>
            </div>
        </div>
        <div class="col-sm mb-2">
            <div class="card">
                <div class="card-body d-grid gap-2">
                    <h5 class="card-title"><fmt:message key="dashboard.faculties-actions"/></h5>
                    <a href="${pageContext.request.contextPath}/controller?command=list-faculties" class="btn btn-primary"><fmt:message key="dashboard.manage-faculties"/></a>
                </div>
            </div>
        </div>
        <div class="col-sm mb-2">
            <div class="card">
                <div class="card-body d-grid gap-2">
                    <h5 class="card-title"><fmt:message key="dashboard.subject-actions"/></h5>
                    <a href="${pageContext.request.contextPath}/controller?command=manage-subjects" class="btn btn-primary"><fmt:message key="dashboard.manage-subjects"/></a>
                </div>
            </div>
        </div>
        <div class="col-sm mb-2">
            <div class="card">
                <div class="card-body d-grid gap-2">
                    <h5 class="card-title"><fmt:message key="dashboard.applications-actions"/></h5>
                    <a href="${pageContext.request.contextPath}/controller?command=manage-applications" class="btn btn-primary"><fmt:message key="dashboard.manage-applications"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
