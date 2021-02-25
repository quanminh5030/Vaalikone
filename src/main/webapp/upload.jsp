<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Upload a file</title>
</head>
<body>
	
	<%
		int candidateId = Integer.parseInt(request.getParameter("candidateId"));
	%>

	<h1>Select file to upload</h1>


	<form action="UploadServlet?candidateId=<%=candidateId %>" method="post"
		enctype="multipart/form-data">
		<input type="hidden" value="<%=candidateId %>">
		Select a file : <input type="file" name="file" accept=".png" /><br>
		<input type="submit" value="Upload" />
	</form>
</body>
</html>