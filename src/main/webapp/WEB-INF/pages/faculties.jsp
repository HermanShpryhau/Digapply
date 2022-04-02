<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page import="dev.shph.digapply.entity.UserRole" %>


<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!doctype html>
<html>
<head>
    <%@ include file="components/head-tags.jsp" %>
    <title><fmt:message key="header.faculties"/> | Digapply</title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-3"><fmt:message key="faculty.our-faculties"/></h1>
    <div class="row justify-content-sm-between mt-3 mb-2">
        <c:if test="${sessionScope.role == UserRole.ADMIN}">
            <div class="col-sm mb-3 d-sm-flex d-grid gap-2">
                <a href="${pageContext.request.contextPath}/controller?command=edit-faculty" class="btn btn-success">
                    <i class="bi bi-plus-lg"></i> <fmt:message key="faculty.new-faculty"/>
                </a>
            </div>
        </c:if>
        <form class="col-sm d-grid gap-2"
              action="${pageContext.request.contextPath}/controller" method="get">
            <div class="input-group mb-3">
                <input type="hidden" name="command" value="list-faculties">
                <input type="text" name="search" class="form-control" placeholder="<fmt:message key="form.search"/>"/>
                <button class="btn btn-primary" type="submit"><i class="bi bi-search"></i></button>
            </div>
        </form>
    </div>

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
                <c:if test="${sessionScope.role == UserRole.ADMIN}">
                    <br/>
                    <a href="${pageContext.request.contextPath}/controller?command=edit-faculty&id=${faculty.facultyId}"
                       class="btn btn-outline-primary btn-sm">
                        <i class="bi bi-pencil-square"></i> <fmt:message key="faculty.edit"/>
                    </a>
                    <!-- Button trigger delete modal -->
                    <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal"
                            data-bs-target="#delete-modal-${faculty.id}">
                        <i class="bi bi-trash-fill"></i><fmt:message key="faculty.delete"/>
                    </button>
                    <!-- Delete Modal -->
                    <div class="modal fade" id="delete-modal-${faculty.id}" tabindex="-1" aria-hidden="true">
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
                                       class="btn btn-danger">
                                        <i class="bi bi-trash-fill"></i><fmt:message key="faculty.delete-modal-delete"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${faculty.applicationClosed}">
                            <a href="${pageContext.request.contextPath}/controller?command=accepted-applications&id=${faculty.facultyId}"
                               class="btn btn-outline-success btn-sm">
                                <i class="bi bi-eye"></i> <fmt:message key="faculty.view-accepted"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/controller?command=close-application&id=${faculty.facultyId}"
                               class="btn btn-outline-success btn-sm">
                                <i class="bi bi-check-square"></i> <fmt:message key="faculty.close-application"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </c:forEach>

    <nav>
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=list-faculties&search=${param.search}&page=${requestScope.page-1}"
                   aria-label="Previous">
                    <span aria-hidden="true"><i class="bi bi-caret-left-fill"></i></span>
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
                               href="${pageContext.request.contextPath}/controller?command=list-faculties&search=${param.search}&page=${n}">${n}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <li class="page-item">
                <a class="page-link" href="controller?command=list-faculties&search=${param.search}&page=${requestScope.page+1}"
                   aria-label="Next">
                    <span aria-hidden="true"><i class="bi bi-caret-right-fill"></i></span>
                </a>
            </li>
        </ul>
    </nav>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
