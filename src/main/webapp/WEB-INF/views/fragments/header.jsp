<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default">
		<div class="container-fluid">
		
			<div class="navbar-header">
      			<a class="navbar-brand" href="#">Periodicals</a>
    		</div>
    		
    		<ul class="nav navbar-nav">
    		
    			<li><a href="<spring:url value="/"/>">Welcome</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
					   data-toggle="dropdown" role="button"
					   aria-expanded="false">Journals <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<spring:url value="/journals"/>">All journals</a></li>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="<spring:url value="/addjournal"/>">Add journal</a></li>
						</sec:authorize>
					</ul>
				</li>

				<sec:authorize access="hasRole('ROLE_USER')">
					<li><a href="<spring:url value="/myjournals"/>">My journals</a></li>
				</sec:authorize>

				<li>
					<sec:authorize access="authenticated" var="authenticated"/>
					<c:choose>
						<c:when test="${authenticated}">
							<li>
								<p class="navbar-text">
									Welcome,
									<sec:authentication property="name"/>
									!
									<a id="logout" href="#">Logout</a>
								</p>
								<form id="logout-form" action="<c:url value="/logout"/>" method="post">
									<sec:csrfInput/>
								</form>
							</li>
						</c:when>
						<c:otherwise>
							<li><a href="<spring:url value="/login/"/>">Sign In</a></li>
						</c:otherwise>
					</c:choose>
    			</li>
				<li><a href="<spring:url value="/register/"/>">Register</a></li>

    		</ul>
    		
		</div>
</nav>