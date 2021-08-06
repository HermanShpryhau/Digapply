<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>

<c:choose>
    <c:when test="${requestScope.subject != null}">
        <c:set var="action" value="${pageContext.request.contextPath}/controller?command=update-subject"/>
    </c:when>
    <c:otherwise>
        <c:set var="action" value="${pageContext.request.contextPath}/controller?command=add-subject"/>
    </c:otherwise>
</c:choose>

<!doctype html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>
      <c:choose>
          <c:when test="${requestScope.subject != null}">
              ${requestScope.subject.subjectName}
          </c:when>
          <c:otherwise>
              <fmt:message key="subject.new-subject"/>
          </c:otherwise>
      </c:choose> | Digappy
    </title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">
        <c:choose>
            <c:when test="${requestScope.subject != null}">
                ${requestScope.subject.subjectName}
            </c:when>
            <c:otherwise>
                <fmt:message key="subject.new-subject"/>
            </c:otherwise>
        </c:choose>
    </h1>

    <form action="${action}" method="post">
        <c:if test="${requestScope.subject != null}">
            <input type="hidden" name="id" value="${requestScope.subject.id}">
        </c:if>
        <div class="mb-3">
            <label for="subject-name" class="form-label"><fmt:message key="subject.subject-name"/></label>
            <br/>
            <input type="text" id="subject-name" name="subject-name" maxlength="45"
            <c:if test="${requestScope.subject != null}">value="${requestScope.subject.subjectName}"</c:if>>
        </div>
        <c:choose>
            <c:when test="${requestScope.subject != null}">
                <input type="submit" class="btn btn-outline-success" value="<fmt:message key="subject.save-changes"/>"/>
            </c:when>
            <c:otherwise>
                <input type="submit" class="btn btn-outline-success" value="<fmt:message key="subject.add-subject"/>"/>
            </c:otherwise>
        </c:choose>
    </form>
</div>

<jsp:include page="components/footer.jsp"/>
</body>
</html>
