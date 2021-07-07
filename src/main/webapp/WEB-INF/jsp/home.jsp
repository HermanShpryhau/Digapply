<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 7.07.21
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <link href="style.css" rel="stylesheet">
    <title>Home | Digapply</title>
</head>

<body>
<header>
    <nav class="navbar navbar-dark navbar-expand-lg bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
                <img src="img/logo.png" alt="" width="30" height="26">
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
                        <li class="nav-item">
                            <a type="button" class="btn btn-outline-light ml-5 me-2" data-bs-toggle="modal"
                               data-bs-target="#sign-in-modal" class="nav-link active" aria-current="page">Sign
                                In</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <div class="modal fade" id="sign-in-modal" tabindex="-1" aria-labelledby="signinModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Sign In</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="mb-3">
                            <label for="exampleInputEmail1" class="form-label">Email address</label>
                            <input type="email" class="form-control" id="exampleInputEmail1"
                                   aria-describedby="emailHelp">
                        </div>
                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Password</label>
                            <input type="password" class="form-control" id="exampleInputPassword1">
                        </div>
                        <button type="submit" class="btn btn-primary">Sign In</button>
                    </form>
                </div>

            </div>
        </div>
    </div>
</header>

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
                <img src="img/logo.png" class="img-fluid" alt="Star Fleet Academy">
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

<footer class="footer mt-auto py-3 bg-light">
    <div class="container">
        <span class="text-muted">Digapply - By Herman Shpryhau</span>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous">
</script>
</body>

</html>
