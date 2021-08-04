<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Subjects Management | Digapply</title>
</head>
<body >
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3">
        Subjects Management
    </h1>
    <a href="/controller?command=new-subject" class="btn btn-success mt-3 mb-3"><i class="bi bi-plus-lg"></i> New Subject</a>
    <div class="min-vh-100">
        <div class="h-100 w-100 overflow-auto">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Edit</th>
                    <th scope="col" >Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.subjects}" var="subject">
                    <tr>
                        <th>${subject.id}</th>
                        <td>${subject.subjectName}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?command=edit-subject&id=${subject.id}">Edit</a>
                        </td>
                        <td>
                            <a class="text-danger" href="${pageContext.request.contextPath}/controller?command=delete-subject&id=${subject.id}">Delete</a>
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
