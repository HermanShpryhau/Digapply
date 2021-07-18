<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}" />
<fmt:bundle basename="labels"/>

<jsp:useBean id="faculty" scope="request" type="by.epamtc.digapply.entity.Faculty"/>
<!doctype html>

</body>
</html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>${faculty.facultyName} | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>

<div class="container">

    <h1 class="mt-5 mb-3">${faculty.facultyName}</h1>
    <p id="description"></p>
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
    <script>
        document.getElementById('description').innerHTML = marked("${faculty.facultyDescription}");
    </script>
    <a href="position-absolute top-50 start-50 translate-middle btn btn-primary btn-lg mt-5">
        <fmt:message key="home.apply-now"/>
    </a>

</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
