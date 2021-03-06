<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>

<jsp:useBean id="faculty" scope="request" type="dev.shph.digapply.entity.Faculty"/>
<!doctype html>

<html>
<head>
    <%@ include file="components/head-tags.jsp" %>
    <title>${faculty.facultyName} | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">${faculty.facultyName}</h1>

    <div class="mt-3 mb-3">
        <p><strong><fmt:message key="faculty.places"/>:</strong> ${faculty.places}</p>
        <p><strong><fmt:message key="faculty.apps-submitted"/>:</strong> ${requestScope.applications_count}</p>
    </div>

    <p id="description"></p>

    <h3 class="mt-3"><fmt:message key="faculty.required-subjects"/></h3>
    <ul>
        <c:forEach var="subject" items="${requestScope.faculty_subjects}">
            <li>${subject.subjectName}</li>
        </c:forEach>
    </ul>

    <c:choose>
        <c:when test="${faculty.applicationClosed}">
            <fmt:message key="faculty.application-closed"/>
        </c:when>
        <c:otherwise>
            <a class="btn btn-primary btn-lg mt-3" href="${pageContext.request.contextPath}/controller?command=new-application&faculty-id=${faculty.facultyId}">
                <fmt:message key="home.apply-now"/>
            </a>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="components/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
<script>
    document.getElementById('description').innerHTML = marked(`${faculty.facultyDescription}`);
</script>
</body>
</html>
