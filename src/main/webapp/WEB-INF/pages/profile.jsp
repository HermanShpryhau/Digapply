<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>


<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="components/head-tags.jsp" %>

    <title>${sessionScope.username} | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>
<div class="container">
    <div class="row justify-content-between mt-5 mb-2">
        <div class="col">
            <h1>${sessionScope.username}</h1>
        </div>
        <div class="col text-end">
            <a class="btn btn-outline-dark" href="${pageContext.request.contextPath}/controller?command=edit-profile&id=${sessionScope.user_id}"><i class="bi bi-pencil-square"></i></a>
        </div>
    </div>

    <h2 class="mt-4 mb-3"><fmt:message key="profile.application-status"/></h2>
    <c:choose>
        <c:when test="${requestScope.application != null}">
            <div class="card <c:if test="${requestScope.application.approved}">text-white bg-success</c:if> ">
                <span class="card-header">
                    <c:choose>
                        <c:when test="${requestScope.application.approved}">
                            <i class="bi bi-check-circle"></i> <fmt:message key="profile.is-approved"/> ${requestScope.approve_date}.
                        </c:when>
                        <c:otherwise>
                            <i class="bi bi-hourglass-split"></i> <fmt:message key="profile.is-checked"/>
                        </c:otherwise>
                    </c:choose>
                </span>
                <div class="card-body">
                    <h5 class="card-title"><fmt:message key="profile.application-for"/> ${requestScope.faculty_name}</h5>
                    <p class="card-text">
                        <fmt:message key="profile.submitted-on"/> ${requestScope.apply_date}.
                        <br/>
                        <fmt:message key="profile.total-score"/>: ${requestScope.total_score}
                    </p>
                    <a href="${pageContext.request.contextPath}/controller?command=cancel-application&user-id=${sessionScope.user_id}" class="btn btn-warning"><i class="bi bi-x-circle"></i> <fmt:message key="profile.cancel-application"/></a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <fmt:message key="profile.no-application"/>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
