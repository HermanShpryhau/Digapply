<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<jsp:useBean id="application" class="by.epamtc.digapply.entity.Application" scope="request"/>

<!doctype html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title><fmt:message key="application.new-app-title"/> | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb=3">Applicant: ${requestScope.applicant}</h1>
    <h1 class="mt-5 mb=3">Faculty: ${requestScope.faculty.facultyName}</h1>

    <form action="${pageContext.request.contextPath}/controller?command=update-application" method="post">
        <c:forEach var="resultMapEntry" items="${requestScope.resultMap}">
            <div class="mb-4">
                <div class="row">
                    <div class="col">
                        <h3>${resultMapEntry.key}</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="sid-${resultMapEntry.value.subjectId}" class="form-label"><fmt:message key="application.score"/></label>
                        <input required min="0" type="number" class="form-control" id="sid-${resultMapEntry.value.subjectId}" name="sid-${resultMapEntry.value.subjectId}" value="${resultMapEntry.value.score}" placeholder="Score">
                    </div>
                    <div class="col">
                        <label for="cid-${resultMapEntry.value.subjectId}" class="form-label"><fmt:message key="application.certificate-id"/></label>
                        <input required maxlength="9" minlength="9" pattern="^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$" type="text" class="form-control" id="cid-${resultMapEntry.value.subjectId}" name="cid-${resultMapEntry.value.subjectId}" value="${resultMapEntry.value.certificateId}" placeholder="A1B2-C3D4" aria-label="Last name">
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="d-grid gap-2 d-md-block">
            <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="application.submit"/>">
        </div>
    </form>
    <form action="${pageContext.request.contextPath}/controller?command=approve-application" method="post">
        <input type="hidden" name="id" value="${application.id}">
        <input type="submit" class="btn btn-success" value="Approve">
    </form>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
