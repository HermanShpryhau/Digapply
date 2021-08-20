<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:bundle basename="labels"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="components/head-tags.jsp" %>
    <title>${requestScope.user.name} ${requestScope.user.surname} | Digapply</title>
</head>

<body class="d-flex flex-column min-vh-100">
<jsp:include page="components/header.jsp"/>


<div class="container">
    <div class="row justify-content-center">
        <div>
            <h1 class="mt-5 fw-bold">Edit Profile</h1>

            <a href="${pageContext.request.contextPath}/controller?command=change-password"
               class="mt-3 mb-3 btn btn-primary">
                <fmt:message key="profile.change-password"/>
            </a>

            <c:if test="${requestScope.error_key != null}">
                <div id="error-alert" class="alert alert-danger mt-3 mb-3" role="alert">
                    <strong><i class="bi bi-exclamation-triangle-fill"></i> <fmt:message key="${requestScope.error_key}"/></strong>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/controller?command=update-profile" method="post">
                <input type="hidden" name="id" value="${requestScope.id}">
                <div class="row mb-3">
                    <div class="col-sm mb-3">
                        <label for="first-name" class="form-label"><fmt:message key="form.firs-name"/>*</label>
                        <input type="text" id="first-name" name="first-name" class="form-control"
                               value="${requestScope.user.name}"
                               placeholder="<fmt:message key="form.firs-name"/>" required maxlength="45"
                               pattern="\b[A-Z].*?\b">
                    </div>
                    <div class="col-sm">
                        <label for="last-name" class="form-label"><fmt:message key="form.last-name"/>*</label>
                        <input type="text" id="last-name" name="last-name" class="form-control"
                               value="${requestScope.user.surname}"
                               placeholder="<fmt:message key="form.last-name"/>" required maxlength="45"
                               pattern="\b[A-Z].*?\b">
                    </div>
                </div>
                <div class="col-12">
                    <button id="submit-btn" class="btn btn-primary" type="submit"><fmt:message key="profile.save-changes"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>
<script src="scripts/profile.js"></script>
</body>

</html>