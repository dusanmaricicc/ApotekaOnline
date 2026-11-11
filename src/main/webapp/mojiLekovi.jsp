<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Lek"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Moji lekovi</title>
</head>
<body>

<jsp:include page="header.jsp" />

<%
ArrayList<Lek> mojiLekovi = (ArrayList<Lek>) request.getAttribute("mojiLekovi");
%>

<div class="container mt-4">
	<h2>Lekovi moje apoteke</h2>
	<p>Ovde mozete pregledati, dodavati, izmeniti i obrisati lekove u apoteci u kojoj ste menadzer.</p>

	<table class="table table-bordered table-striped bg-white">
		<tr>
			<th>Naziv</th>
			<th>Opis</th>
			<th>Cena</th>
			<th>Kolicina</th>
			<th>Popust (%)</th>
			<th>Akcije</th>
		</tr>

		<%
		for (int i = 0; i < mojiLekovi.size(); i++) {
			Lek l = mojiLekovi.get(i);
		%>
		<tr>
			<form action="IzmeniLekServlet" method="post">
				<input type="hidden" name="id" value="<%= l.getId() %>">
				<td><input type="text" name="naziv" value="<%= l.getNaziv() %>" class="form-control form-control-sm"></td>
				<td><input type="text" name="opis" value="<%= l.getOpis() %>" class="form-control form-control-sm"></td>
				<td><input type="number" step="0.01" name="cena" value="<%= l.getCena() %>" class="form-control form-control-sm" style="width: 90px;"></td>
				<td><input type="number" name="kolicina" value="<%= l.getKolicina() %>" class="form-control form-control-sm" style="width: 70px;"></td>
				<td><input type="number" name="popust" value="<%= l.getPopust() %>" class="form-control form-control-sm" style="width: 70px;"></td>
				<td>
					<button type="submit" class="btn btn-sm btn-primary">Sacuvaj</button>
					<a href="ObrisiLekServlet?id=<%= l.getId() %>" class="btn btn-sm btn-danger"
						onclick="return confirm('Obrisati lek?');">Obrisi</a>
				</td>
			</form>
		</tr>
		<% } %>
	</table>

	<h4 class="mt-4">Dodaj novi lek</h4>
	<form action="DodajLekServlet" method="post" class="row g-2" style="max-width: 900px;">
		<div class="col-md-3">
			<input type="text" name="naziv" placeholder="Naziv" class="form-control" required>
		</div>
		<div class="col-md-3">
			<input type="text" name="opis" placeholder="Opis" class="form-control">
		</div>
		<div class="col-md-2">
			<input type="number" step="0.01" name="cena" placeholder="Cena" class="form-control" required>
		</div>
		<div class="col-md-2">
			<input type="number" name="kolicina" placeholder="Kolicina" class="form-control" required>
		</div>
		<div class="col-md-1">
			<input type="number" name="popust" placeholder="Popust %" value="0" class="form-control">
		</div>
		<div class="col-md-1">
			<button type="submit" class="btn btn-success">Dodaj</button>
		</div>
	</form>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
