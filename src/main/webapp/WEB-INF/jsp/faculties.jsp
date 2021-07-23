<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}" />
<fmt:bundle basename="labels"/>
<!doctype html>
</body>
</html>
<html>
<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Faculties | Digapply</title>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="components/header.jsp"/>

<div class="container">
    <h1 class="mt-5 mb-5"><fmt:message key="faculty.our-faculties"/></h1>
    <c:if test="${sessionScope.role == 1}">
        <div>
            <a href="${pageContext.request.contextPath}/controller?command=edit-faculty" class="btn btn-success mb-5">New Faculty</a>
        </div>
    </c:if>
xxuytrgvjkppokoioikkjiju[=[[nbhqwqwq2323w3212h        <div class="col-11">
            <input class="form-control me-2" type="search" name="search" placeholder="Search" aria-label="Search">
        </div>
        <div class="col-auto">
            <button class="btn btn-outline-success" type="submit">Search</button>
        </div>
    </form>
    <c:forEach var="faculty" items="${faculties}">
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">${faculty.facultyName}</h5>
                <p class="card-text">${faculty.facultyShortDescription}</p>
                <a href="${pageContext.request.contextPath}/controller?command=show-faculty&id=${faculty.facultyId}" class="card-link">
                    <fmt:message key="faculty.read-more"/>
                </a>
                <c:if test="${sessionScope.role == 1}">
                    <br/>
                    <a href="${pageContext.request.contextPath}/controller?command=edit-faculty&id=${faculty.facultyId}" class="btn btn-outline-primary btn-sm">
                        <fmt:message key="faculty.edit"/>
                    </a>
                </c:if>
            </div>
        </div>
    </c:forEach>

    <nav>
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/controller?command=list-faculties&page=${requestScope.page-1}" aria-label="Previous">
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
                            <a class="page-link" href="${pageContext.request.contextPath}/controller?command=list-faculties&page=${n}">${n}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <li class="page-item">
                <a class="page-link" href="controller?command=list-faculties&page=${requestScope.page+1}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>

<jsp:include page="components/footer.jsp"/>

</body>
</html>
