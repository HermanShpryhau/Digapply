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
    <title><fmt:message key="application.new-app-title"/> | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb=3"><fmt:message key="application.new-app-head"/> ${requestScope.faculty.facultyName}</h1>
    <p class="fs-4"><fmt:message key="application.instruction"/></p>

    <c:if test="${requestScope.error_key != null}">
        <div class="alert alert-danger mt-3 mb-3" role="alert">
            <strong><i class="bi bi-exclamation-triangle-fill"></i> <fmt:message key="${requestScope.error_key}"/></strong>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/controller?command=submit-application" method="post">
        <input type="hidden" name="faculty-id" value="${requestScope.faculty.facultyId}">
        <c:forEach var="subject" items="${requestScope.subjects}">
            <div class="mb-4">
                <div class="row">
                    <div class="col">
                        <h3>${subject.subjectName}</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="sid-${subject.subjectId}" class="form-label"><fmt:message key="application.score"/>*</label>
                        <input required min="0" type="number" class="form-control" id="sid-${subject.subjectId}" name="sid-${subject.subjectId}" placeholder="Score">
                    </div>
                    <div class="col">
                        <label for="cid-${subject.subjectId}" class="form-label"><fmt:message key="application.certificate-id"/>*</label>
                        <input required maxlength="9" minlength="9" pattern="^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$" type="text" class="form-control" id="cid-${subject.subjectId}" name="cid-${subject.subjectId}" placeholder="A1B2-C3D4" aria-label="Last name">
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="d-grid gap-2 d-md-block">
            <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="application.submit"/>">
        </div>
    </form>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
