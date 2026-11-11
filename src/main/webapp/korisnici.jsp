<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Korisnik"%>
<%@ page import="model.Apoteka"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Korisnici</title>
</head>
<body>

<jsp:include page="header.jsp" />

<%
ArrayList<Korisnik> sviKorisnici = (ArrayList<Korisnik>) request.getAttribute("sviKorisnici");
ArrayList<Apoteka> sveApoteke = (ArrayList<Apoteka>) request.getAttribute("sveApoteke");
%>

<div class="container mt-4">
	<h2>Upravljanje korisnicima</h2>

	<table class="table table-bordered table-striped bg-white">
		<tr>
			<th>Ime i prezime</th>
			<th>Email</th>
			<th>Korisnicko ime</th>
			<th>Uloga</th>
			<th>Apoteka (za menadzera)</th>
			<th>Poeni (za klijenta)</th>
			<th>Nova lozinka</th>
			<th>Akcije</th>
		</tr>

		<%
		for (int i = 0; i < sviKorisnici.size(); i++) {
			Korisnik k = sviKorisnici.get(i);
		%>
		<tr>
			<form action="IzmeniKorisnikaServlet" method="post">
				<input type="hidden" name="id" value="<%= k.getId() %>">

				<td>
					<input type="text" name="ime" value="<%= k.getIme() %>" class="form-control form-control-sm mb-1">
					<input type="text" name="prezime" value="<%= k.getPrezime() %>" class="form-control form-control-sm">
				</td>
				<td><input type="text" name="email" value="<%= k.getEmail() %>" class="form-control form-control-sm"></td>
				<td><input type="text" name="username" value="<%= k.getUsername() %>" class="form-control form-control-sm"></td>
				<td>
					<select name="novaUloga" class="form-select form-select-sm">
						<option value="administrator" <%= "administrator".equals(k.getUloga()) ? "selected" : "" %>>administrator</option>
						<option value="menadzer" <%= "menadzer".equals(k.getUloga()) ? "selected" : "" %>>menadzer</option>
						<option value="klijent" <%= "klijent".equals(k.getUloga()) ? "selected" : "" %>>klijent</option>
					</select>
				</td>
				<td>
					<select name="apotekaId" class="form-select form-select-sm">
						<option value="">-</option>
						<%
						for (int j = 0; j < sveApoteke.size(); j++) {
							Apoteka a = sveApoteke.get(j);
							boolean izabrana = k.getApotekaId() != null && k.getApotekaId() == a.getId();
						%>
						<option value="<%= a.getId() %>" <%= izabrana ? "selected" : "" %>><%= a.getNaziv() %></option>
						<% } %>
					</select>
				</td>
				<td><%= k.getPoeni() %></td>
				<td><input type="password" name="password" placeholder="(bez izmene)" class="form-control form-control-sm"></td>
				<td>
					<button type="submit" class="btn btn-sm btn-primary">Sacuvaj</button>
					<a href="ObrisiKorisnikaServlet?id=<%= k.getId() %>" class="btn btn-sm btn-danger"
						onclick="return confirm('Obrisati korisnika?');">Obrisi</a>
				</td>
			</form>
		</tr>
		<% } %>
	</table>

	<h4 class="mt-4">Dodaj novog korisnika</h4>
	<form action="DodajKorisnikaServlet" method="post" class="row g-2" style="max-width: 900px;">
		<div class="col-md-2">
			<input type="text" name="ime" placeholder="Ime" class="form-control" required>
		</div>
		<div class="col-md-2">
			<input type="text" name="prezime" placeholder="Prezime" class="form-control" required>
		</div>
		<div class="col-md-2">
			<input type="email" name="email" placeholder="Email" class="form-control">
		</div>
		<div class="col-md-2">
			<input type="text" name="username" placeholder="Korisnicko ime" class="form-control" required>
		</div>
		<div class="col-md-2">
			<input type="password" name="password" placeholder="Lozinka" class="form-control" required>
		</div>
		<div class="col-md-1">
			<select name="novaUloga" class="form-select">
				<option value="administrator">administrator</option>
				<option value="menadzer">menadzer</option>
				<option value="klijent" selected>klijent</option>
			</select>
		</div>
		<div class="col-md-1">
			<select name="apotekaId" class="form-select">
				<option value="">-</option>
				<%
				for (int j = 0; j < sveApoteke.size(); j++) {
					Apoteka a = sveApoteke.get(j);
				%>
				<option value="<%= a.getId() %>"><%= a.getNaziv() %></option>
				<% } %>
			</select>
		</div>
		<div class="col-12">
			<button type="submit" class="btn btn-success mt-2">Dodaj korisnika</button>
		</div>
	</form>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
