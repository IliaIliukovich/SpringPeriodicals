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

</head>
<body>

<jsp:include page="../views/fragments/header.jsp"></jsp:include>
<spring:url value="/journals/add" var="formUrl"/>

<div class="container">

    <h2>Journals</h2>
    <table class="table table-hover">
        <tbody>
        <tr>
            <th>Journal name</th><th>Description</th><th>Price</th><th>Add to my list</th>
        </tr>
        <c:forEach items="${journals}" var="journal">
            <tr>
                <td>${journal.name}</td>
                <td>${journal.description}</td>
                <td>${journal.price}</td>
                <td>
                    <c:choose>
                        <c:when test="${journal.subscription == 'unsubscribed'}">
                            <form:form action="${formUrl}" method="POST">
                                <input type="hidden" value="${journal.id_journal}" name="currentId">
                                <input type="submit" value="add"/>
                            </form:form>
                        </c:when>
                        <c:when test="${journal.subscription == 'chosen'}">
                            added
                        </c:when>
                        <c:when test="${journal.subscription == 'subscribed'}">
                            subscribed
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>