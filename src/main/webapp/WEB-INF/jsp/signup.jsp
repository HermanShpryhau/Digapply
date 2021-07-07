<%--
  Created by IntelliJ IDEA.
  User: hermanshpryhau
  Date: 7.07.21
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="components/head-links.jsp"/>
    <title>Sign Up | Digapply</title>
</head>

<body>
<jsp:include page="components/header.jsp"/>


<main class="container">
    <div class="row justify-content-center">
        <div class="col"></div>
        <div class="col-6">
            <h1 class="mt-5 fw-bold">Sign Up Form</h1>

            <form action="/controller?command=signup">
                <div class="row mt-5 mb-4">
                    <div class="col">
                        <label for="first-name" class="form-lable">First Name</label>
                        <input type="text" id="first-name" name="first-name" class="form-control"
                               placeholder="First name" aria-label="First name" required maxlength="45"
                               pattern="\b[A-Z].*?\b">
                    </div>
                    <div class="col">
                        <label for="last-name" class="form-lable">Last Name</label>
                        <input type="text" id="last-name" name="last-name" class="form-control"
                               placeholder="Last name" aria-label="Last name" required maxlength="45"
                               pattern="\b[A-Z].*?\b">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email address</label>
                    <input type="email" class="form-control" name="emial" id="email" placeholder="name@example.com" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Password" required
                           minlength="8">
                </div>
                <div class="col-12">
                    <button class="btn btn-primary" type="submit">Sign Up</button>
                </div>
            </form>
        </div>
        <div class="col"></div>
    </div>
</main>

<jsp:include page="components/footer.jsp"/>


</body>

</html>