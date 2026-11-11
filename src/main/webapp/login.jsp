<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Prijava</title>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container d-flex justify-content-center align-items-center" style="min-height: 70vh;">
	<div class="card shadow p-4" style="width: 400px;">

		<h2 class="text-center mb-4">Prijava</h2>

		<form action="LoginServlet" method="post">

			<div class="mb-3">
				<label class="form-label">Korisnicko ime</label>
				<input type="text" name="username" class="form-control" required>
			</div>

			<div class="mb-3">
				<label class="form-label">Lozinka</label>
				<input type="password" name="password" class="form-control" required>
			</div>

			<div class="d-grid">
				<button type="submit" class="btn btn-primary">Prijavi se</button>
			</div>
		</form>

		<p class="text-center mt-3">
			Nemate nalog? <a href="registracija.jsp">Registrujte se</a>
		</p>

		<%
		if (request.getAttribute("poruka") != null) {
			String poruka = (String) request.getAttribute("poruka");
		%>
		<div class="alert alert-info mt-3 text-center"><%= poruka %></div>
		<%
		}
		%>
	</div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
