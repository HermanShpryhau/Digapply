<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Digapply | Index</title>
</head>
<body>
    <c:if test="${sessionScope.user == null}">
        <jsp:forward page="WEB-INF/jsp/login.jsp" />
    </c:if>
    <c:if test="${sessionScope.user != null}">
        <jstl:redirect url="/controller?command=show-page&page=WEB-INF/jsp/home.jsp" />
    </c:if>
</body>
</html>