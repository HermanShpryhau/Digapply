<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 7.07.21
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<header>
    <nav class="navbar navbar-dark navbar-expand-lg bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
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
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Faculties</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Apply Now</a>
                    </li>
                </ul>

                <div class="d-flex">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 p-2">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">EN</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">RU</a>
                        </li>
                        <c:choose>
                            <c:when test="${sessionScope.username == null}">
                                <li class="nav-item">
                                    <a type="button" class="btn btn-outline-light ml-5 me-2" data-bs-toggle="modal"
                                       data-bs-target="#sign-in-modal" class="nav-link active" aria-current="page">Sign
                                        In</a>
                                </li>
                                <div class="modal fade" id="sign-in-modal" tabindex="-1" aria-labelledby="signinModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Sign In</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="/controller?command=login" method="post">
                                                    <div class="mb-3">
                                                        <label for="exampleInputEmail1" class="form-label">Email address</label>
                                                        <input name="email" type="email" class="form-control" id="exampleInputEmail1"
                                                               aria-describedby="emailHelp">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="exampleInputPassword1" class="form-label">Password</label>
                                                        <input name="password" type="password" class="form-control" id="exampleInputPassword1">
                                                    </div>
                                                    <button type="submit" class="btn btn-primary">Sign In</button>
                                                </form>
                                                <hr class="dropdown-divider">
                                                <p class="text-muted">Don't have an account? <a href="#">Sign Up</a></p>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.username != null}">
                                <div class="dropdown">
                                    <a class="nav-link dropdown-toggle" style="color: white !important" href="#"
                                       id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        ${sessionScope.username}
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                        <li><a class="dropdown-item" href="#">Profile</a></li>
                                        <li>
                                            <hr class="dropdown-divider">
                                        </li>
                                        <li><a class="dropdown-item" href="/controller?command=logout">Sign Out</a></li>
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