<%@page import="java.awt.print.Book"%>
<%@page import="persist.Kysymykset"%>
<%@page import="dao.QuestionDao"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE html>
<html>
<head>
<title>Diginide vaalikone</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

</head>
<body>


	<%
		int id = Integer.parseInt(request.getParameter("kysymys_id"));
		Kysymykset question = QuestionDao.getOneQuestion(id);
	%>


	<div class="container" id="container">
		<img id="headerimg" src="Logo.png" width="720" />
		<div class="kysymys">
			<h1>Update question</h1>
		</div>
		<br>

		<form action="questionservlet" method="POST">
			<input type="hidden" name="id" value="<%=id%>">
			<h3><label for="inputUser">Question:</label></h3> <input type="text"
				name="kysymys" value="<%=question.getKysymys()%>"
				class="form-control"> <br> <input type="Submit"
				value="Update" class="btn btn-warning">
		</form>
	</div>


</body>
</html>