<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}"/>
<fmt:bundle basename="labels"/>

<!doctype html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <title>
        <c:choose>
            <c:when test="${requestScope.faculty != null}">
                ${requestScope.faculty.facultyName}
            </c:when>
            <c:when test="${requestScope.faculty == null}">
                New Faculty
            </c:when>
        </c:choose> | Digapply
    </title>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5">
        <c:choose>
            <c:when test="${requestScope.faculty != null}">
                ${requestScope.faculty.facultyName}
            </c:when>
            <c:when test="${requestScope.faculty == null}">
                New Faculty
            </c:when>
        </c:choose>
    </h1>
    <form action="#" method="post">
        <div class="mb-3 col-md-6">
            <label for="faculty-name" class="form-label">Faculty Name</label>
            <input required type="text" class="form-control" id="faculty-name" name="faculty-name"
            <c:if test="${requestScope.faculty != null}">
                   value="${requestScope.faculty.facultyName}"
            </c:if>
                   placeholder="Faculty Name">
        </div>
        <div class="mb-3 col-md-6">
            <label for="places-count">Places</label>
            <input required type="number" class="form-control" id="places-count" name="places-count"
            <c:if test="${requestScope.faculty != null}">
                   value="${requestScope.faculty.places}"
            </c:if>
                   placeholder="Available places">
        </div>
        <div class="mb-3">
            <label for="short-faculty-description" class="form-label">Short Faculty Description</label>
            <textarea required class="form-control" id="short-faculty-description" name="short-faculty-description"
                      rows="10">
        <c:if test="${requestScope.faculty != null}">
            ${requestScope.faculty.facultyShortDescription}
        </c:if>
      </textarea>
        </div>
        <div class="mb-3">
            <label for="faculty-description" class="form-label">Faculty Description (Markdown)</label>
            <textarea required class="form-control" id="faculty-description" name="faculty-description" rows="25">
      </textarea>
        </div>
        <c:if test="${requestScope.faculty != null}">
            <select class="subject-select mb-3" multiple aria-label="multiple select example">
                <c:forEach items="${requestScope.subjects}" var="subject">
                    <option value="${subject.subjectId}">${subject.subjectName}</option>
                </c:forEach>
            </select>
            <br/>
        </c:if>
        <button type="submit" class="btn btn-primary"><fmt:message key="form.submit-button"/></button>
    </form>
</div>

<jsp:include page="components/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
<script>
    let simplemde = new SimpleMDE({element: document.getElementById("faculty-description")});
    simplemde.value(`<c:if test="${requestScope.faculty != null}">${requestScope.faculty.facultyDescription}</c:if>`);
</script>
</body>
</html>
