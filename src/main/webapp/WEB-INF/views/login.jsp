<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring Periodicals App</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="<spring:url value="/resources/css/home.css"/>" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

</head>
<body>

<jsp:include page="../views/fragments/header.jsp"></jsp:include>

<div class="container">
    <c:url value="/login" var="loginVar"/>
    <form id="appointment-form" action="${loginVar}" method="POST">
        <div class="form-group">
            <label for="make">Username</label>
            <input name="custom_username" class="form-control" />
        </div>
        <div class="form-group">
            <label for="model">Password</label>
            <input type="password" name="custom_password" class="form-control" />
        </div>
        <label class="form-group">
            <label for="remember">Stay signed in</label>
            <input type="checkbox" id="remember" name="remember-me"/>
        </label>

        <sec:csrfInput/>

        <c:if test="${param.logout != null }">
            <p>You have successfully been logged out.</p>
        </c:if>

        <c:if test="${param.error != null }">
            <p>Invalid Username or Password.</p>
        </c:if>

        <button type="submit" id="btn-save" class="btn btn-primary">Login</button>
    </form>
</div>
</body>
</html>