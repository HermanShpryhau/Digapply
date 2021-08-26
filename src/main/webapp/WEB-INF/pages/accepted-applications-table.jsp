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
    <title><fmt:message key="application.accepted-applications"/> | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">
        Accepted Applications
    </h1>

    <table id="table" class="table table-striped">
        <caption><fmt:message key="application.accepted-applications"/></caption>
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col"><fmt:message key="application.applicant"/></th>
            <th scope="col"><fmt:message key="application.faculty"/></th>
            <th scope="col" class="text-center"><fmt:message key="application.total-score"/></th>
            <th scope="col"><fmt:message key="application.submission-date"/></th>
            <th scope="col" class="text-center"><fmt:message key="application.is-approved"/></th>
            <th scope="col"><fmt:message key="application.approve-date"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.applications}" var="application">
            <tr>
                <th scope="col">${application.applicationId}</th>
                <td>${application.applicantName}</td>
                <td>${application.facultyName}</td>
                <td class="text-center">${application.totalScore}</td>
                <td>${application.applyDate.toLocaleString()}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${application.approved}">
                            <i class="bi bi-check-circle-fill text-success"></i>
                        </c:when>
                        <c:otherwise>
                            <i class="bi bi-x-circle-fill text-danger"></i>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${application.approved}">
                            ${application.approveDate.toLocaleString()}
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="components/footer.jsp"/>
<%@ include file="components/table-pagiantion.jsp" %>

</body>
</html>
