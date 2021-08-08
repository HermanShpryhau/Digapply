<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!doctype html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>User Management | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>
<div class="container">
    <h1 class="mt-5 mb-3">
        User Management
    </h1>
    <div class="vh-100">
        <div class="h-100 w-100 overflow-auto">
            <table class="table table-hover table-striped">
                <caption>Users table</caption>
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">email</th>
                    <th scope="col" >Password Hash</th>
                    <th scope="col">Name</th>
                    <th scope="col" >Surname</th>
                    <th scope="col" >Role</th>
                    <th class="text-center" scope="col" >Admin Rights</th>
                    <th scope="col" >Edit</th>
                    <th scope="col" >Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.users}" var="user">
                    <tr>
                        <th scope="col">${user.userId}</th>
                        <td>${user.email}</td>
                        <td>${user.password}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.role}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${user.roleId == 1}">
                                    <span class="text-success"><i class="bi bi-check-circle-fill"></i></span>
                                </c:when>
                                <c:otherwise>
                                    <a class="text-warning" href="${pageContext.request.contextPath}/controller?command=make-admin&id=${user.userId}"><i class="bi bi-arrow-up-circle-fill"></i></a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?command=edit-profile&id=${user.userId}">Edit</a>
                        </td>
                        <td>
                            <a class="text-danger" href="${pageContext.request.contextPath}/controller?command=delete-user&id=${user.userId}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
