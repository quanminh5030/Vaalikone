<%@page import="java.util.List"%>
<%@page import="dao.QuestionDao"%>
<%@page import="persist.Kysymykset"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		int candidateId = Integer.parseInt(request.getParameter("candidateId"));
		List<Kysymykset> questionList = QuestionDao.getAllQuestions();
	%>

	
	<%
		for (int i = 0; i < questionList.size(); i++) {
	%>
	<form action="answerservlet?candidateId=<%=candidateId %>" method="post">
		<h3>
			Question
			<%=i + 1%>:
		</h3>
		<p><%=questionList.get(i).getKysymys()%></p>
		<label>1</label><input type="radio" name="vastaus" value="1" /> 
		<label>2</label><input type="radio" name="vastaus" value="2" /> 
		<label>3</label><input type="radio" name="vastaus" value="3"/> 
		<label>4</label><input type="radio" name="vastaus" value="4" /> 
		<label>5</label><input type="radio" name="vastaus" value="5" /> 
		<input type="hidden" name="q" value="<%=questionList.get(i).getKysymysId()%>">
	    
	<%
		}
	%>
	<input type="submit" value="Save">
	</form>



</body>
</html>