<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page import="by.epamtc.digapply.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>

<header>
    <nav class="navbar navbar-dark navbar-expand-lg bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=home">
                <img src="assets/logo.png" alt="" width="30" height="26">
                Star Fleet Academy
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggler"
                    aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarToggler">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/controller?command=home">
                            <fmt:message key="header.home"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/controller?command=list-faculties">
                            <fmt:message key="header.faculties"/>
                        </a>
                    </li>
                </ul>

                <div class="d-flex">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <ul class=" navbar-nav me-3">
                            <li class="nav-item">
                                <a class="nav-link <fmt:message key="header.en-active"/>" href="${pageContext.request.contextPath}/controller?${pageContext.request.queryString}&locale=en">
                                    EN
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <fmt:message key="header.ru-active"/>" href="${pageContext.request.contextPath}/controller?${pageContext.request.queryString}&locale=ru">
                                    RU
                                </a>
                            </li>
                        </ul>
                        <c:choose>
                            <c:when test="${sessionScope.username == null}">
                                <li class="nav-item">
                                    <a type="button" href="${pageContext.request.contextPath}/controller?command=show-sign-in" class="btn btn-outline-light ml-5 me-2"  class="nav-link active" aria-current="page">
                                        <fmt:message key="header.sign-in"/>
                                    </a>
                                </li>
                            </c:when>
                            <c:when test="${sessionScope.username != null}">
                                <div class="dropdown">
                                    <a class="nav-link dropdown-toggle" style="color: white !important" href="#"
                                       id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        ${sessionScope.username}
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=profile">
                                            <fmt:message key="header.profile"/>
                                        </a></li>
                                        <c:if test="${sessionScope.role == UserRole.ADMIN}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/controller?command=dashboard" class="dropdown-item">
                                                    <fmt:message key="header.dashboard"/>
                                                </a>
                                            </li>
                                        </c:if>
                                        <li>
                                            <hr class="dropdown-divider">
                                        </li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=logout">
                                            <fmt:message key="header.sign-out"/>
                                        </a></li>
                                    </ul>
                                </div>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</header>