<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page import="by.epamtc.digapply.entity.UserRole" %>


<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!doctype html>
<html>
<head>
    <%@ include file="components/head-tags.jsp" %>
    <title><fmt:message key="user.user-management"/> | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>
<div class="container">
    <h1 class="mt-5 mb-3">
        <fmt:message key="user.user-management"/>
    </h1>

    <table id="table" class="table table-hover table-striped w-100">
            <caption><fmt:message key="user.user-table"/></caption>
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col"><fmt:message key="user.email"/></th>
                <th scope="col"><fmt:message key="user.name"/></th>
                <th scope="col"><fmt:message key="user.surname"/></th>
                <th scope="col"><fmt:message key="user.role"/></th>
                <th class="text-center" scope="col"><fmt:message key="user.admin-rights"/></th>
                <th scope="col"><fmt:message key="user.edit"/></th>
                <th scope="col"><fmt:message key="user.delete"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <th scope="col">${user.userId}</th>
                    <td>${user.email}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.role}</td>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${user.role == UserRole.ADMIN}">
                                <a class="text-danger"
                                   href="${pageContext.request.contextPath}/controller?command=revoke-admin-rights&id=${user.userId}"><i
                                        class="bi bi-x-circle-fill"></i></a>
                            </c:when>
                            <c:otherwise>
                                <a class="text-warning"
                                   href="${pageContext.request.contextPath}/controller?command=give-admin-rights&id=${user.userId}"><i
                                        class="bi bi-arrow-up-circle-fill"></i></a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/controller?command=edit-profile&id=${user.userId}"><fmt:message
                                key="user.edit"/></a>
                    </td>
                    <td>
                        <a class="text-danger"
                           href="${pageContext.request.contextPath}/controller?command=delete-user&id=${user.userId}"><fmt:message
                                key="user.delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</div>

<jsp:include page="components/footer.jsp"/>
<%@ include file="components/table-pagiantion.jsp" %>

</body>
</html>
