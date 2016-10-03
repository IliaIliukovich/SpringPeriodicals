<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Project Manager</title>

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
        <h1>Add new a journal</h1>
    </div>

    <spring:url value="/addjournal" var="formUrl"/>

    <form:form action="${formUrl}" method="POST" modelAttribute="journal">

        <div class="row">

            <div class="form-group">
                <label for="journal-name">Name</label>
                <form:input path="name" cssClass="form-control" id="journal-name"/>
                <form:errors path="name"/>
            </div>

            <div class="form-group">
                <label for="journal-description">Description</label>
                <form:textarea id="journal-description" path="description" cssClass="form-control" rows="3"/>
            </div>

            <div class="form-group">
                <label for="journal-price">Price</label>
                <form:input id="journal-price" cssClass="form-control" path="price" />
                <form:errors path="price"/>
            </div>

            <button type="submit" class="btn btn-default">Submit</button>

        </div>

    </form:form>

</div>
</body>
</html>