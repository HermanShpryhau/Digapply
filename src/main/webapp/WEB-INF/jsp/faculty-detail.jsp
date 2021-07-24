<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}" />
<fmt:bundle basename="labels"/>

<jsp:useBean id="faculty" scope="request" type="by.epamtc.digapply.entity.Faculty"/>
<!doctype html>

<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>${faculty.facultyName} | Digapply</title>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">${faculty.facultyName}</h1>
    <p id="description"></p>

    <h3 class="mt-3"><fmt:message key="faculty.required-subjects"/></h3>
    <ul>
        <c:forEach var="subject" items="${requestScope.faculty_subjects}">
            <li>${subject.subjectName}</li>
        </c:forEach>
    </ul>

    <a class="btn btn-primary btn-lg mt-3" href="#">
        <fmt:message key="home.apply-now"/>
    </a>

</div>

<jsp:include page="components/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
<script>
    document.getElementById('description').innerHTML = marked(`${faculty.facultyDescription}`);
</script>
</body>
</html>
