<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Applications | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">
        Application Management
    </h1>

    <div class="mt-3 mb-3">
        <c:forEach var="faculty" items="${requestScope.faculties}">
            <c:choose>
                <c:when test="${param['faculty-id'] == faculty.id}">
                    <a href="${pageContext.request.contextPath}/controller?command=manage-applications" class="btn mb-1 btn-sm btn-primary">${faculty.facultyName}</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/controller?command=manage-applications&faculty-id=${faculty.id}" class="btn mb-1 btn-sm btn-outline-primary">${faculty.facultyName}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <div class="min-vh-100">
        <div class="h-100 w-100 overflow-auto">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Applicant</th>
                    <th scope="col">Faculty</th>
                    <th scope="col">Total Score</th>
                    <th scope="col">Submission date</th>
                    <th scope="col">Is Approved</th>
                    <th scope="col">Approval date</th>
                    <th scope="col">Review</th>
                    <th scope="col">Revoke</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.applications}" var="application">
                    <tr>
                        <th>${application.applicationId}</th>
                        <td>${application.applicantName}</td>
                        <td>${application.facultyName}</td>
                        <td>${application.totalScore}</td>
                        <td>${application.applyDate}</td>
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
                                    ${application.approveDate}
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="#">Review</a></td>
                        <td><a href="#" class="text-danger">Revoke</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
