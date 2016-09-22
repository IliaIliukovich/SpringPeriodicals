<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
						<li><a href="<spring:url value="/addjournal"/>">Add journal</a></li>
					</ul>
				</li>
    			<li><a href="<spring:url value="/myjournals"/>">My journals</a></li>

    			<%--<li><a href="<spring:url value="/journals"/>">Journals</a></li>--%>

    		</ul>
    		
		</div>
</nav>