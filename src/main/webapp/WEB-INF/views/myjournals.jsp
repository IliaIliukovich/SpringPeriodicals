<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Project Manager</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="<spring:url value="/resources/css/home.css"/>" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="<c:url value="/resources/js/logout.js"/>"></script>

</head>
<body>

<jsp:include page="../views/fragments/header.jsp"></jsp:include>
<spring:url value="/myjournals/delete" var="delUrl"/>

<div class="container">

    <c:choose>
        <c:when test="${(myChoiceJournals[0] != null) or mySubscriptionJournals[0] != null}">

            <c:if test="${myChoiceJournals[0] != null}">
                <h2>Added journals</h2>
                <table class="table table-hover">
                    <tbody>
                    <tr>
                        <th>Journal name</th><th>Description</th><th>Price</th><th>Action</th>
                    </tr>
                    <c:forEach items="${myChoiceJournals}" var="journal">
                        <tr>
                            <td>${journal.name}</td>
                            <td>${journal.description}</td>
                            <td>${journal.price}</td>
                            <td>
                                <form:form action="${delUrl}" method="POST">
                                    <input type="hidden" value="${journal.relationalTableId}" name="choiceId">
                                    <input type="submit" value="delete"/>
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div>
                    <tr>
                        <td>Total sum to pay:</td>
                        <td>${sumToPay}</td>
                        <td>
                            <spring:url value="/myjournals/pay" var="formUrl"/>
                            <form:form action="${formUrl}" method="POST">
                                <input type="hidden" value="${sumToPay}" name="sum">
                                <input type="submit" value="Make payment"/>
                            </form:form>
                        </td>
                    </tr>
                </div>
            </c:if>

            <c:if test="${mySubscriptionJournals[0] != null}">
                <h2>My subscriptions</h2>
                <table class="table table-hover">
                    <tbody>
                    <tr>
                        <th>Journal name</th><th>Description</th><th>Price</th>
                    </tr>
                    <c:forEach items="${mySubscriptionJournals}" var="journal">
                        <tr>
                            <td>${journal.name}</td>
                            <td>${journal.description}</td>
                            <td>${journal.price}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </c:when>

        <c:otherwise>
            You don't have any subscriptions yet
        </c:otherwise>

    </c:choose>

</div>
</body>
</html>