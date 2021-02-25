<%@page import="java.util.List"%>
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
		List<Kysymykset> questionList = QuestionDao.getAllQuestions();
	%>
	<div id="container">
		<div class="row">
			<div class="col-lg-6">
				<h1>All the questions</h1>
			</div>
			<div class="col-lg-4">
				<a href="addQuestion.jsp" class="btn btn-warning btn-lg"><b>Add
						more question</b></a>
			</div>
			<div class="col-lg-2">
				<a href="welcome.jsp" class="btn btn-warning btn-lg"><b>Back</b></a>
			</div>
		</div>
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th><b>Kysymys_id</b></th>
					<th><b>Kysymys</b></th>
					<th><b>Action</b></th>
					<th><b>Action</b>
					<th>
				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < questionList.size(); i++) {
				%>
				<tr>
					<td><%=questionList.get(i).getKysymysId()%></td>
					<td><%=questionList.get(i).getKysymys()%></td>
					<td><a
						href="edit-process.jsp?kysymys_id=<%=questionList.get(i).getKysymysId()%>"
						class="btn btn-warning">Edit</a></td>
					<td><a
						href="questionservlet?kysymys_id=<%=questionList.get(i).getKysymysId()%>"
						class="btn btn-warning">Delete</a></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>