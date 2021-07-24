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
    <title>Faculties | Digapply</title>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-3 mb-3"><fmt:message key="faculty.our-faculties"/></h1>
    <c:if test="${sessionScope.role == 1}">
        <div>
            <a href="${pageContext.request.contextPath}/controller?command=edit-faculty" class="btn btn-success mb-5">
                <fmt:message key="faculty.new-faculty"/>
            </a>
        </div>
    </c:if>
    <form class="d-inline-flex flex-row-reverse mb-3 mt-3" action="${pageContext.request.contextPath}/controller?command=search-faculties" method="get">
        <div class="p-2">
            <button class="btn btn-outline-success" type="submit">Search</button>
        </div>
        <div class="p-2">
            <input class="form-control me-2" type="search" name="search" placeholder="Search" aria-label="Search">
        </div>
    </form>
    <jsp:useBean id="faculties" scope="request" type="java.util.List"/>
    <c:forEach var="faculty" items="${faculties}">
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">${faculty.facultyName}</h5>
                <p class="card-text">${faculty.facultyShortDescription}</p>
                <a href="${pageContext.request.contextPath}/controller?command=show-faculty&id=${faculty.facultyId}"
                   class="card-link">
                    <fmt:message key="faculty.read-more"/>
                </a>
                <c:if test="${sessionScope.role == 1}">
                    <br/>
                    <a href="${pageContext.request.contextPath}/controller?command=edit-faculty&id=${faculty.facultyId}"
                       class="btn btn-outline-primary btn-sm">
                        <fmt:message key="faculty.edit"/>
                    </a>
                    <!-- Button trigger delete modal -->
                    <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal"
                            data-bs-target="#delete-modal">
                        <fmt:message key="faculty.delete"/>
                    </button>
                    <!-- Delete Modal -->
                    <div class="modal fade" id="delete-modal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="delete-modal-label"><fmt:message
                                            key="faculty.delete-modal-header"/></h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <fmt:message key="faculty.delete-modal-body"/>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message
                                            key="faculty.delete-modal-cancel"/></button>
                                    <a href="${pageContext.request.contextPath}/controller?command=delete-faculty&id=${faculty.id}"
                                       class="btn btn-danger"><fmt:message key="faculty.delete-modal-delete"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </c:forEach>

    <nav>
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=list-faculties&page=${requestScope.page-1}"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach begin="1" end="${requestScope.number_of_pages}" var="n">
                <c:choose>
                    <c:when test="${requestScope.page == n}">
                        <li class="page-item active">
                            <a class="page-link">${n}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=list-faculties&page=${n}">${n}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <li class="page-item">
                <a class="page-link" href="controller?command=list-faculties&page=${requestScope.page+1}"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
