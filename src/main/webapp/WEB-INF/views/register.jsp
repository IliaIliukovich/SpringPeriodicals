<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Spring Periodicals App</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="<spring:url value="/resources/css/home.css"/>" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="<c:url value="/resources/js/logout.js"/>"></script>

</head>
<body>

<jsp:include page="../views/fragments/header.jsp"></jsp:include>

<div class="container">
    <div class="row">
        <h1>Register</h1>
    </div>
    <c:url value="/register" var="loginVar"/>
    <form id="appointment-form" action="${loginVar}" method="POST">
        <div class="form-group">
            <label for="make">Username</label>
            <input name="username" class="form-control" />
        </div>
        <div class="form-group">
            <label for="model">Password</label>
            <input type="password" name="password" class="form-control" />
        </div>
        <sec:csrfInput/>

        <button type="submit" id="btn-save" class="btn btn-primary">Login</button>
    </form>
</div>
</body>
</html>