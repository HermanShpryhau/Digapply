<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!doctype html>
<html>
<head>
    <%@ include file="components/head-tags.jsp" %>
    <title><fmt:message key="subject.subject-management"/> | Digapply</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">
        <fmt:message key="subject.subject-management"/>
    </h1>
    <a href="${pageContext.request.contextPath}/controller?command=new-subject" class="btn btn-success mt-3 mb-3"><i class="bi bi-plus-lg"></i> <fmt:message key="subject.new-subject"/></a>
    <div class="min-vh-100">
        <div class="h-100 w-100 overflow-auto">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="subject.name"/></th>
                    <th scope="col"><fmt:message key="subject.edit"/></th>
                    <th scope="col" ><fmt:message key="subject.delete"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.subjects}" var="subject">
                    <tr>
                        <th scope="col">${subject.id}</th>
                        <td>${subject.subjectName}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?command=edit-subject&id=${subject.id}"><fmt:message key="subject.edit"/></a>
                        </td>
                        <td>
                            <a class="text-danger" href="${pageContext.request.contextPath}/controller?command=delete-subject&id=${subject.id}"><fmt:message key="subject.delete"/></a>
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
