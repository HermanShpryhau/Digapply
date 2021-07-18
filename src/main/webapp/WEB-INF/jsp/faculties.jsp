<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}" />
<fmt:bundle basename="labels"/>
<!doctype html>
</body>
</html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Faculties | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">Our Faculties</h1>
    <c:forEach var="faculty" items="${faculties}">
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">${faculty.facultyName}</h5>
                <p class="card-text">${faculty.facultyShortDescription}</p>
                <a href="${pageContext.request.contextPath}/controller?command=show-faculty&id=${faculty.facultyId}" class="card-link">
                    Read more
                </a>
                <c:if test="${sessionScope.role == 1}">
                    <br/><a href="#" class="btn btn-outline-primary btn-sm">Edit</a>
                </c:if>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
