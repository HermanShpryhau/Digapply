<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 7.07.21
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <h1 class="display-5 fw-bold">To boldly go where no man has gone before</h1>
                <p class="lead">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Neque molestiae
                    voluptatibus in
                    nesciunt harum voluptas incidunt qui, necessitatibus porro doloremque consequuntur quae adipisci
                    excepturi saepe unde veniam asperiores similique maiores? Lorem, ipsum dolor sit amet
                    consectetur adipisicing elit. Nostrum amet eligendi illo consectetur saepe et expedita, cum
                    dignissimos, dolorum commodi fugit quisquam. Iure, maiores velit ipsum dicta placeat molestiae
                    aliquam!</p>
            </div>

            <div class="col-sm">
                <img src="assets/logo.png" class="img-fluid" alt="Star Fleet Academy">
            </div>
        </div>
    </section>

    <div class="container mt-5 px-4 py-5" id="featured-3">
        <h2 class="pb-2 border-bottom fw-bold">Our best Faculties</h2>
        <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
            <div class="feature col">
                <h2>Star Fleet Officer College</h2>
                <p>Paragraph of text beneath the heading to explain the heading. We'll add onto it with another
                    sentence and probably just keep going until we run out of words.</p>
                <a href="#" class="icon-link">
                    Read more
                </a>
            </div>
            <div class="feature col">
                <h2>Star Fleet Medical College</h2>
                <p>Paragraph of text beneath the heading to explain the heading. We'll add onto it with another
                    sentence and probably just keep going until we run out of words.</p>
                <a href="#" class="icon-link">
                    Read more
                </a>
            </div>
            <div class="feature col">
                <h2>Star Fleet Science College</h2>
                <p>Paragraph of text beneath the heading to explain the heading. We'll add onto it with another
                    sentence and probably just keep going until we run out of words.</p>
                <a href="#" class="icon-link">
                    Read more
                </a>
            </div>
        </div>
    </div>

    <div class="px-4 py-5 my-5 text-center">

        <h1 class="display-5 fw-bold">Don't miss your chance</h1>
        <div class="col-lg-6 mx-auto">
            <p class="lead mb-4">Our mission is to explore strange new worlds. To seek out new life and new
                civilizations. To boldly go where no man has gone before! Apply for Star Fleet Academy if you want
                to be part of it.</p>
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <a href="#">
                    <button type="button" class="btn btn-primary btn-lg px-4 gap-3">Apply Now</button>
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

</body>

</html>
