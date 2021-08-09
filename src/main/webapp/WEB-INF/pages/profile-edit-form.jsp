<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="components/head-links.jsp"/>
  <title>${requestScope.user.name} ${requestScope.user.surname} | Digapply</title>
</head>

<body class="d-flex flex-column h-100">
<jsp:include page="components/header.jsp"/>


<div class="container">
  <div class="row justify-content-center">
    <div>
      <h1 class="mt-5 fw-bold">Edit Profile</h1>

      <form action="${pageContext.request.contextPath}/controller?command=update-profile" method="post">
        <input type="hidden" name="id" value="${requestScope.id}">
        <div class="row mb-3">
          <div class="col-sm mb-3">
            <label for="first-name" class="form-label"><fmt:message key="form.firs-name"/></label>
            <input type="text" id="first-name" name="first-name" class="form-control" value="${requestScope.user.name}"
                   placeholder="<fmt:message key="form.firs-name"/>" required maxlength="45"
                   pattern="\b[A-Z].*?\b">
          </div>
          <div class="col-sm">
            <label for="last-name" class="form-label"><fmt:message key="form.last-name"/></label>
            <input type="text" id="last-name" name="last-name" class="form-control" value="${requestScope.user.surname}"
                   placeholder="<fmt:message key="form.last-name"/>" required maxlength="45"
                   pattern="\b[A-Z].*?\b">
          </div>
        </div>

        <div class="mb-3">
          <label for="email" class="form-label"><fmt:message key="form.email-address"/></label>
          <input type="email" disabled class="form-control" name="email" id="email" value="${requestScope.user.name}" required>
        </div>
        <div class="mb-3">
          <label for="password" class="form-label"><fmt:message key="form.password"/></label>
          <input type="password" class="form-control" name="password" id="password" placeholder="Enter new password here to change it"
                 minlength="8">
        </div>
        <div class="col-12">
          <button class="btn btn-primary" type="submit">Save Changes</button>
        </div>
      </form>
      </div>
  </div>
</div>

<jsp:include page="components/footer.jsp"/>


</body>

</html>