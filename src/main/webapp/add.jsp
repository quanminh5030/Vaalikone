<%@page import="dao.CandidateDao"%>
<%@page import="persist.Ehdokkaat"%>
<%@page import="java.util.List"%>
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
		List<Ehdokkaat> candidateList = CandidateDao.getAllCandidates();
		int lastCandidateId = candidateList.get(candidateList.size() - 1).getEhdokasId();
	%>
	<div id="container">

		<img id="headerimg" src="Logo.png" width="720" />
		<div class="kysymys">
			<h1>Add Candidate</h1>
		</div>
		<br>

		<form action="./CandidateClient" method="POST"
			enctype="muiltipart/form-data">
			<div class="row">


				<div class="col">
					<input type="hidden" name="lastcandidateid"
						value="<%=lastCandidateId + 1%>"> <label for="inputUser">Sukunimi:
					</label> <input type="text" name="suku" required class="form-control">
				</div>
				<div class="col">
					<label for="inputUser">Etunimi:</label> <input type="text"
						name="etu" required class="form-control"> <br>
				</div>
			</div>
			<label for="inputUser">Puolue:</label> <input type="text" name="puo"
				required class="form-control"> <br> <label
				for="inputUser"> Kotipaikkakunta: </label> <input type="text"
				name="koti" required class="form-control"> <br> <label
				for="inputUser">Ika:</label><input type="number" name="ika" min='1'
				max='200' required class="form-control"> <br> <label
				for="inputUser">Miksi eduskuntaan:</label> <input type="text"
				name="miksi" required class="form-control"> <br> <label
				for="inputUser">Mita asioita haluat edistaa:</label> <input
				type="text" name="mita" required class="form-control"> <br>
			<label for="inputUser">Ammatti:</label> <input type="text" name="ama"
				required class="form-control"> <br> <input
				type="submit" value="Add" class="btn btn-warning btn-lg">
		</form>
	</div>
</body>
</html>