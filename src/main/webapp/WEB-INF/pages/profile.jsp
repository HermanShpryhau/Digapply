<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 10.07.21
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>

<!DOCTYPE html>
<head>
    <jsp:include page="components/head-links.jsp"/>

    <title>${sessionScope.username} | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>
<div class="container">
    <h1 class="mt-5 mb-2">${sessionScope.username}</h1>
    <a class="btn btn-outline-primary" href="#"><fmt:message key="profile.edit"/></a>

    <h2 class="mt-5 mb-3"><fmt:message key="profile.application-status"/></h2>
    <c:choose>
        <c:when test="${requestScope.application != null}">
            <div class="card <c:if test="${requestScope.application.approved}">text-white bg-success</c:if> ">
                <span class="card-header">
                    <c:choose>
                        <c:when test="${requestScope.application.approved}">
                            <i class="bi bi-check-circle"></i> <fmt:message key="profile.is-approved"/> ${requestScope.application.approveDate.toLocaleString()}.
                        </c:when>
                        <c:otherwise>
                            <i class="bi bi-hourglass-split"></i> <fmt:message key="profile.is-checked"/>
                        </c:otherwise>
                    </c:choose>
                </span>
                <div class="card-body">
                    <h5 class="card-title"><fmt:message key="profile.application-for"/> ${requestScope.faculty_name}</h5>
                    <p class="card-text">
                        <fmt:message key="profile.submitted-on"/> ${requestScope.application.applyDate.toLocaleString()}.
                        <br/>
                        <fmt:message key="profile.total-score"/>: ${requestScope.total_score}
                    </p>
                    <a href="#" class="btn btn-warning"><i class="bi bi-x-circle"></i> <fmt:message key="profile.cancel-application"/></a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            You haven't submitted any application yet.
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
