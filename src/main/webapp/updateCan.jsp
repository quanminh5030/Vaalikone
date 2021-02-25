
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="dao.CandidateDao"%>
<%@page import="persist.Ehdokkaat"%>
<%@page import="java.util.List"%>
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
		Integer id = Integer.parseInt(request.getParameter("ehdokas_id"));
		Ehdokkaat CanInform = CandidateDao.getOneCandidate(id);
	%>


	<div class="container" id="container">
		<img id="headerimg" src="Logo.png" width="720" />
		<div class="kysymys">
			<h1>Update data</h1>
		</div>
		<br>

		<form method="POST" action="UpdateCandidate">
			<input type="hidden" name="id" value="<%=id%>"><br>
			<div class="row">
				<div class="col">
					<label for="inputUser">Sukunimi:</label><input type="text"
						name="sukunimi" value="<%=CanInform.getSukunimi()%>"
						class="form-control">
				</div>
				<div class="col">
					<label for="inputUser">Etunimi:</label> <input type="text"
						name="etunimi" value="<%=CanInform.getEtunimi()%>"
						class="form-control"><br>
				</div>
			</div>
			<label for="inputUser">Puolue:</label><input type="text"
				name="puolue" value="<%=CanInform.getPuolue()%>"
				class="form-control"> <br> <label for="inputUser">Kotipaikkakunta:</label>
			<input type="text" name="koti"
				value="<%=CanInform.getKotipaikkakunta()%>" class="form-control">
			<br> <label for="inputUser">Ika:</label><input type="text"
				name="ika" value="<%=CanInform.getIka()%>" class="form-control">
			<br> <label for="inputUser">Miksi eduskuntaan:</label> <input
				type="text" name="miksi"
				value="<%=CanInform.getMiksiEduskuntaan()%>" class="form-control">
			<br> <label for="inputUser">Mita asioita haluat edistaa:</label><input
				type="text" name="mita"
				value="<%=CanInform.getMitaAsioitaHaluatEdistaa()%>"
				class="form-control"> <br> <label for="inputUser">Ammatti:</label><input
				type="text" name="ammatti" value="<%=CanInform.getAmmatti()%>"
				class="form-control"> <br> <input type="Submit"
				value="Submit" class="btn btn-warning">

		</form>




	</div>
</body>
</html>