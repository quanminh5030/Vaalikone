<%@page import="java.util.List"%>
<%@page import="persist.Ehdokkaat"%>

<%@page import="dao.CandidateDao"%>
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
		//List all the Candidate from CandidateDao package
		List<Ehdokkaat> candidateList = CandidateDao.getAllCandidates();
	%>

	<div class="row" style="margin:15px 0">
		<div class="col-lg-9">
			<h1>Candidate information</h1>
		</div>
		

		<div class="col-lg-2" id="addCan">
			<a href="add.jsp" class="btn btn-warning btn-lg"><b>Add more
					Candidate</b></a>
		</div>
		<div class="col-lg-1" id="">
			<a href="welcome.jsp" class="btn btn-warning btn-lg"><b>Back</b></a>
		</div>
		<br> 

	</div>

	<table class="table">

		<thead class="thead-dark">
			<tr>
				<th><b>Ehdokas_id</b></th>
				<th><b>Sukunimi</b></th>
				<th><b>Etunimi</b></th>
				<th><b>Puolue</b></th>
				<th><b>Kotipaikkankunta</b></th>
				<th><b>Ika</b></th>
				<th><b>Miksi eduskuntaan?</b></th>
				<th><b>Mita asioita haluat edistaa?</b></th>
				<th><b>Ammatti</b></th>
				<th><b>Action</b></th>
				<th><b>Action</b>
				<th>
			</tr>
		</thead>
		<tbody>
			<%
				for (int i = 0; i < candidateList.size(); i++) {
			%>
			<tr>
				<td><%=candidateList.get(i).getEhdokasId()%></td>
				<td><%=candidateList.get(i).getSukunimi()%></td>
				<td><%=candidateList.get(i).getEtunimi()%></td>
				<td><%=candidateList.get(i).getPuolue()%></td>
				<td><%=candidateList.get(i).getKotipaikkakunta()%></td>
				<td><%=candidateList.get(i).getIka()%></td>
				<td><%=candidateList.get(i).getMiksiEduskuntaan()%></td>
				<td><%=candidateList.get(i).getMitaAsioitaHaluatEdistaa()%></td>
				<td><%=candidateList.get(i).getAmmatti()%></td>
				<td><a
					href="updateCan.jsp?ehdokas_id=<%=candidateList.get(i).getEhdokasId()%>"
					class="btn btn-warning">Update</a></td>
				<td><a
					href="UpdateCandidate?ehdokas_id=<%=candidateList.get(i).getEhdokasId()%>"
					class="btn btn-warning">Delete</a></td>
			</tr>
			<%
				}
			%>

		</tbody>
	</table>

</body>
</html>