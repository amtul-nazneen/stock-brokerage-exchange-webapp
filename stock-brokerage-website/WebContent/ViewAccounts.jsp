<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>View Bank Accounts</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<script>
	$(function() {
		$("#nav-placeholder").load("Nav.jsp");
	});
</script>
<body class="body_bg">
<%
		HttpSession nsession = request.getSession(false);
		if (nsession != null) {
			String logged = (String) session.getAttribute("logged");
			if (logged == null) {
				String redirectURL = "https://localhost:8443/stock-brokerage-website/Login.jsp";
				response.sendRedirect(redirectURL);
			}
		} else {
			String redirectURL = "https://localhost:8443/stock-brokerage-website/Login.jsp";
			response.sendRedirect(redirectURL);
		}
	%>
	<div id="nav-placeholder"></div>
	<br>
	<form>
		<div class="container">
			<h2>Your Bank Accounts</h2>
			<!--div class="text-right mb-3">
				<button type="submit" class="btn btn-primary">Sell Stocks</button>
			</div-->
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Bank Account Number</th>
						<th>Bank Routing Number</th>
						<th>Balance</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${accounts}" var="account">
						<tr>
							
							<td><c:out value="${account.accno}" /></td>
							<td><c:out value="${account.routingno}"/></td>
							<td><c:out value="${account.balance}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>

</body>
</html>
