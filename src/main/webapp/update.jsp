<%@page import="dao.AnswerDao"%>
<%@page import="dao.QuestionDao"%>
<%@page import="persist.Kysymykset"%>
<%@page import="javax.persistence.Query"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="persist.Vastaukset"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
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
	<%
		Integer candidateId = (Integer) session.getAttribute("candidateId");
		Integer questionId = Integer.parseInt(request.getParameter("questionId"));
		Kysymykset question = QuestionDao.getOneQuestion(questionId);
		List<Vastaukset> answerList = AnswerDao.getAllAnswerOfOneCandidate(candidateId);
	%>

	<div id="container">
		<img id="headerimg" src="Logo.png" width="500" height="144" alt="" />

		<div class="kysymys">
			Question
			<%=questionId%>:
			<%=question.getKysymys()%>
			<br>

		</div>
		<form
			action="answerservlet?candidateId=<%=candidateId%>&questionId=<%=questionId%>"
			method="POST" id="vastausformi">
			<label>Your answer: </label><input type="text" name="answer"
				style="width: 10%;" class="text-center"
				placeholder="<%=answerList.get(questionId - 1).getVastaus()%>"
				readonly="readonly" /> <label>New answer: </label> <label>1</label><input
				type="radio" name="newAns" value="1" /> <label>2</label><input
				type="radio" name="newAns" value="2" /> <label>3</label><input
				type="radio" name="newAns" value="3" checked="checked" /> <label>4</label><input
				type="radio" name="newAns" value="4" /> <label>5</label><input
				type="radio" name="newAns" value="5" /> <br> <br> <input
				type="submit" id="submitnappi" value="Update" />
		</form>
		<div class="kysymys">
			<small>1=Täysin eri mieltä 2=Osittain eri mieltä 3=En osaa
				sanoa, 4=Osittain samaa mieltä 5=Täysin samaa mieltä</small>
		</div>
	</div>





</body>
</html>