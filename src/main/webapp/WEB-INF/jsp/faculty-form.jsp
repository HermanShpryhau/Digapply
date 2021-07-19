<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 19.07.21
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
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
      </c:choose>
       | Digapply
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
        <input type="text" class="form-control" id="faculty-name" name="faculty-name" placeholder="Faculty Name">
      </div>
      <div class="form-group col-md-6">
        <label for="places-count">Places</label>
        <input type="number" class="form-control" id="places-count" name="places-count" placeholder="Available places">
      </div>
    </div>
    <div class="mb-3">
      <label for="short-faculty-description" class="form-label">Short Faculty Description</label>
      <textarea class="form-control" id="short-faculty-description" name="short-faculty-description" rows="3"></textarea>
    </div>
    <div class="mb-3">
      <label for="faculty-description" class="form-label">Faculty Description (Markdown)</label>
      <textarea class="form-control" id="faculty-description" name="faculty-description" rows="3"></textarea>
    </div>
    <select class="subject-select" multiple aria-label="multiple select example">
      <c:forEach items="${requestScope.subjects}" var="subject">
        <option value="${subject.subjectId}">${subject.subjectName}</option>
      </c:forEach>
    </select>

    <button type="submit" class="btn btn-primary"><fmt:message key="form.submit-button"/></button>
  </form>
</div>

<jsp:include page="components/footer.jsp"/>
</body>
</html>
