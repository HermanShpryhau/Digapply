<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 10.07.21
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <jsp:include page="components/head-links.jsp"/>

  <title>${sessionScope.username} | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>
<div class="container">
  <h1 class="mt-5 mb-2">${sessionScope.username}</h1>
  <h2>Pending applications</h2>
  <a class="btn btn-outline-primary" href="#">Edit</a>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
