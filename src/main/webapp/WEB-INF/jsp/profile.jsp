<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 10.07.21
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}" />
<fmt:bundle basename="labels"/>

<!DOCTYPE html>
<head>
  <jsp:include page="components/head-links.jsp"/>

  <title>${sessionScope.username} | Digapply</title>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="components/header.jsp"/>
<div class="container">
  <h1 class="mt-5 mb-2">${sessionScope.username}</h1>
  <h2><fmt:message key="profile.pending"/></h2>
  <a class="btn btn-outline-primary" href="#"><fmt:message key="profile.edit"/></a>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
