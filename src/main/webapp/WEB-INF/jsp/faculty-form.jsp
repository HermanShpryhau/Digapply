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
<body>
<jsp:include page="components/header.jsp"/>

<div class="container">
  <h1>
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
    <div class="form-row">
      <div class="form-group col-md-6">
        <label for="faculty-name" class="form-label">Faculty Name</label>
        <input type="text" class="form-control" id="faculty-name" name="faculty-name"
        <c:if test="${requestScope.faculty != null}">
        value="${requestScope.faculty.facultyName}"
        </c:if>
        placeholder="Faculty Name">
      </div>
      <div class="form-group col-md-6">
        <label for="places-count">Places</label>
        <input type="number" class="form-control" id="places-count" name="places-count"
        <c:if test="${requestScope.faculty != null}">
               value="${requestScope.faculty.places}"
        </c:if>
        placeholder="Available places">
      </div>
    </div>
    <div class="mb-3">
      <label for="short-faculty-description" class="form-label">Short Faculty Description</label>
      <textarea class="form-control" id="short-faculty-description" name="short-faculty-description" rows="10">
        <c:if test="${requestScope.faculty != null}">
          ${requestScope.faculty.facultyShortDescription}
        </c:if>
      </textarea>
    </div>
    <div class="mb-3">
      <label for="faculty-description" class="form-label">Faculty Description (Markdown)</label>
      <textarea class="form-control" id="faculty-description" name="faculty-description" rows="25">
        <c:if test="${requestScope.faculty != null}">
          ${requestScope.faculty.facultyDescription}
        </c:if>
      </textarea>
    </div>
    <select class="subject-select" multiple aria-label="multiple select example">
      <c:forEach items="${requestScope.subjects}" var="subject">
      <option <c:if test="${requestScope != null}">selected</c:if> value="${subject.subjectId}">${subject.subjectName}</option>
      </c:forEach>
    </select>

    <button type="submit" class="btn btn-primary"><fmt:message key="form.submit-button"/></button>
  </form>
</div>

<jsp:include page="components/footer.jsp"/>
</body>
</html>
