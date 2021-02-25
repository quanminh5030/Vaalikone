<%@page import="rest.UploadService"%>
<%@page import="dao.AnswerDao"%>
<%@page import="dao.CandidateDao"%>
<%@page import="dao.QuestionDao"%>
<%@page import="persist.Ehdokkaat"%>
<%@page import="persist.Kysymykset"%>
<%@page import="java.util.List"%>
<%@page import="persist.Vastaukset"%>
<%@page import="java.util.ArrayList"%>
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
		Integer candidateId = (Integer) session.getAttribute("candidateId");
		Ehdokkaat candidate = CandidateDao.getOneCandidate(candidateId);
		List<Kysymykset> questionList = QuestionDao.getAllQuestions();
		List<Vastaukset> answerList = AnswerDao.getAllAnswerOfOneCandidate(candidateId);
	%>

	<div class="container">
		<div class="row">
			<div class="col-lg-10">
				<h1>Hello candidate!</h1>
			</div>
			<div class="col-lg-2">
				<a href="candidate.html" class="btn btn-warning btn-lg"
					style="margin-top: 15px">Log out</a>
			</div>
		</div>



		<div class="row" style="margin-top: 25px">
			<div class="col-lg-9">
				<h3>Your Information:</h3>
				<ul>
					<li><b>Name:</b> <%=candidate.getEtunimi() + " " + candidate.getSukunimi()%></li>
					<li><b>Party:</b> <%=candidate.getPuolue()%></li>
					<li><b>Registered official municipality:</b> <%=candidate.getKotipaikkakunta()%></li>
					<li><b>Age:</b> <%=candidate.getIka()%></li>
				</ul>
			</div>

			<div class="col-lg-3">
				<%
					if (candidate.getPicture() == null) {
				%><i>You don't upload any photo yet!</i><br> <a
					href="upload.jsp?candidateId=<%=candidateId%>">Upload your
					picture</a><br>
				<%
					} else {
				%>
				<img src="<%=candidate.getPicture()%>" alt="picture"
					class="img-fluid"><br>
					<a href="upload.jsp?candidateId=<%=candidateId%>">Change Your Picture</a><br>
				<%
					}
				%>
			</div>
			<h3>Your Answers:</h3>
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Questions_ID</th>
						<th scope="col">Question</th>
						<th scope="col">Your Answer</th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<%
						for (int i = 0; i < questionList.size(); i++) {
					%>
					<tr>
						<td><%=questionList.get(i).getKysymysId()%></td>
						<td><%=questionList.get(i).getKysymys()%></td>
						<td><%=answerList.get(i).getVastaus()%></td>
						<td><a
							href="update.jsp?questionId=<%=questionList.get(i).getKysymysId()%>"
							class="btn btn-warning">Edit</a></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>