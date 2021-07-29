<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!doctype html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title><fmt:message key="application.new-app-title"/> | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb=3"><fmt:message key="application.new-app-head"/> ${requestScope.faculty.facultyName}</h1>
    <p class="fs-4"><fmt:message key="application.instruction"/></p>

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
                        <label for="sid-${subject.subjectId}" class="form-label"><fmt:message key="application.score"/></label>
                        <input required min="0" type="number" class="form-control" id="sid-${subject.subjectId}" name="sid-${subject.subjectId}" placeholder="Score">
                    </div>
                    <div class="col">
                        <label for="cid-${subject.subjectId}" class="form-label"><fmt:message key="application.certificate-id"/></label>
                        <input required maxlength="9" minlength="9" pattern="^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$" type="text" class="form-control" id="cid-${subject.subjectId}" name="cid-${subject.subjectId}" placeholder="A1B2-C3D4" aria-label="Last name">
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="d-grid gap-2 d-md-block">
            <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="application.submit"/>">

        <%--            <button class="btn btn-primary btn-lg" data-bs-toggle="modal" data-bs-target="#submit-modal"><fmt:message key="application.submit"/></button>--%>
<%--            <div class="modal" id="submit-modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">--%>
<%--                <div class="modal-dialog modal-dialog-centered modal-fullscreen-sm-down">--%>
<%--                    <div class="modal-content">--%>
<%--                        <div class="modal-body">--%>
<%--                            <h1><fmt:message key="application.submit-modal-head"/></h1>--%>
<%--                            <p><fmt:message key="application.submit-modal-body"/></p>--%>
<%--                        </div>--%>
<%--                        <div class="modal-footer">--%>
<%--                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="application.cancel"/></button>--%>
<%--                            <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="application.submit"/>">--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
        </div>
    </form>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
