<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Apoteka"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Apoteke</title>
</head>
<body>

<jsp:include page="header.jsp" />

<%
boolean jeAdmin = session.getAttribute("uloga") != null && "administrator".equals(session.getAttribute("uloga"));
ArrayList<Apoteka> sveApoteke = (ArrayList<Apoteka>) request.getAttribute("sveApoteke");
%>

<div class="container mt-4">
	<h2>Nase apoteke</h2>

	<table class="table table-bordered table-striped bg-white">
		<tr>
			<th>Naziv</th>
			<th>Adresa</th>
			<th>Grad</th>
			<% if (jeAdmin) { %>
			<th>Akcije</th>
			<% } %>
		</tr>
		<%
		for (int i = 0; i < sveApoteke.size(); i++) {
			Apoteka a = sveApoteke.get(i);
		%>
		<tr>
			<% if (jeAdmin) { %>
			<form action="IzmeniApotekuServlet" method="post">
				<input type="hidden" name="id" value="<%= a.getId() %>">
				<td><input type="text" name="naziv" value="<%= a.getNaziv() %>" class="form-control form-control-sm"></td>
				<td><input type="text" name="adresa" value="<%= a.getAdresa() %>" class="form-control form-control-sm"></td>
				<td><input type="text" name="grad" value="<%= a.getGrad() %>" class="form-control form-control-sm"></td>
				<td>
					<button type="submit" class="btn btn-sm btn-primary">Sacuvaj</button>
					<a href="ObrisiApotekuServlet?id=<%= a.getId() %>" class="btn btn-sm btn-danger"
						onclick="return confirm('Obrisati apoteku?');">Obrisi</a>
				</td>
			</form>
			<% } else { %>
			<td><%= a.getNaziv() %></td>
			<td><%= a.getAdresa() %></td>
			<td><%= a.getGrad() %></td>
			<% } %>
		</tr>
		<% } %>
	</table>

	<% if (jeAdmin) { %>
	<h4 class="mt-4">Dodaj novu apoteku</h4>
	<form action="DodajApotekuServlet" method="post" class="row g-2" style="max-width: 700px;">
		<div class="col-md-4">
			<input type="text" name="naziv" placeholder="Naziv" class="form-control" required>
		</div>
		<div class="col-md-4">
			<input type="text" name="adresa" placeholder="Adresa" class="form-control" required>
		</div>
		<div class="col-md-3">
			<input type="text" name="grad" placeholder="Grad" class="form-control" required>
		</div>
		<div class="col-md-1">
			<button type="submit" class="btn btn-success">Dodaj</button>
		</div>
	</form>
	<% } %>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
