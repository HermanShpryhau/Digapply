<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 7.07.21
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}"/>
<fmt:bundle basename="labels"/>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Home | Digapply</title>
</head>

<body>
<jsp:include page="components/header.jsp"/>

<div class="container">
    <section id="promo">
        <div class="row mt-5">
            <div class="col-sm my-auto">
                <h1 class="display-5 fw-bold"><fmt:message key="home.promo"/></h1>
                <p class="fs-5 fs-normal">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Neque molestiae
                    voluptatibus in nesciunt harum voluptas incidunt qui, necessitatibus porro doloremque consequuntur
                    quae adipisci excepturi saepe unde veniam asperiores similique maiores? Lorem, ipsum dolor sit amet
                    consectetur adipisicing elit. Nostrum amet eligendi illo consectetur saepe et expedita, cum
                    dignissimos, dolorum commodi fugit quisquam. Iure, maiores velit ipsum dicta placeat molestiae
                    aliquam!</p>
            </div>

            <div class="col-sm">
                <img src="assets/logo.png" class="img-fluid" alt="Star Fleet Academy">
            </div>
        </div>
    </section>

    <div class="container mt-5 " id="featured-3">
        <h2 class="border-bottom fw-bold"><fmt:message key="home.best-faculties"/></h2>
        <div class="row g-4 mt-1 row-cols-1 row-cols-lg-3">
            <c:forEach var="faculty" items="${requestScope.best_faculties}">
                <div class="feature col">
                    <h2>${faculty.facultyName}</h2>
                    <p>${faculty.facultyShortDescription}</p>
                    <a href="${pageContext.request.contextPath}/controller?command=show-faculty&id=${faculty.facultyId}" class="icon-link">
                        <fmt:message key="home.read-more"/>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="px-4 py-5 my-5 text-center">

        <h1 class="display-5 fw-bold"><fmt:message key="home.dont-miss"/></h1>
        <div class="col-lg-6 mx-auto">
            <p class="mb-4 fs-3 fs-normal"><fmt:message key="home.dont-miss-text"/></p>
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <a href="#">
                    <button type="button" class="btn btn-primary btn-lg px-4 gap-3"><fmt:message
                            key="home.apply-now"/></button>
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

</body>

</html>
