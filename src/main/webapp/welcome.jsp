<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Diginide vaalikone</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>


	<div id="container">
		<img id="headerimg" src="Logo.png" width="720" />
		<div class="kysymys">
			<h1>Welcome to the admin page</h1>
		</div>
		<br> <br>


		<div class="row">
			<div class="col-lg-2"></div>

			<div class="col-lg-3">
				<form action="rtest.jsp">
					<input type="submit" value="All the candidates"
						class="btn btn-primary btn-lg">
				</form>
			</div>
			<div class="col-lg-3">
				<form action="edit.jsp">
					<input type="submit" value="All the questions"
						class="btn btn-primary btn-lg">
				</form>
			</div>
			
			<div class="col-lg-3">
				<a href="index.html" class="btn btn-warning btn-lg">Log out</a>
			</div>
		</div>
	</div>



</body>
</html>