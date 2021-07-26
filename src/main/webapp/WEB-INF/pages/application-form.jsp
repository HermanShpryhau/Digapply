<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>

<c:choose>
    <c:when test="${requestScope.application != null}">
        <c:set var="action" value="${pageContext.request.contextPath}/controller?command=update-application"/>
    </c:when>
    <c:otherwise>
        <c:set var="action" value="${pageContext.request.contextPath}/controller?command=submit-applicaion"/>
    </c:otherwise>
</c:choose>

<!doctype html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>New Application | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb=3">New Application for ${requestScope.faculty.facultyName}</h1>
    <p class="fs-4">In order to apply please fill in your results in following subjects and specify result certificate ID.</p>

    <form action="${action}" method="post">
        <c:forEach var="subject" items="${requestScope.subjects}">
            <div class="mb-4">
                <div class="row">
                    <div class="col">
                        <h3>${subject.subjectName}</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="sid-${subject.subjectId}" class="form-label">Score</label>
                        <input required min="0" type="number" class="form-control" id="sid-${subject.subjectId}" name="sid-${subject.subjectId}" placeholder="Score">
                    </div>
                    <div class="col">
                        <label for="cid-${subject.subjectId}" class="form-label">Certificate ID</label>
                        <input required maxlength="9" minlength="9" pattern="^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$" type="text" class="form-control" id="cid-${subject.subjectId}" name="cid-${subject.subjectId}" placeholder="A1B2-C3D4" aria-label="Last name">
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="d-grid gap-2 d-md-block">
            <input type="submit" class="btn btn-lg btn-primary">
        </div>
    </form>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
