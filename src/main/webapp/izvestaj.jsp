<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Narudzbina"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Izvestaj o poslovanju</title>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container mt-4">
	<h2>Izvestaj o poslovanju apoteke</h2>

	<div class="row mt-3">
		<div class="col-md-3">
			<div class="card p-3 text-center">
				<h6>Broj vrsta lekova</h6>
				<h3><%= request.getAttribute("brojVrstaLekova") %></h3>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card p-3 text-center">
				<h6>Ukupna zaliha (komada)</h6>
				<h3><%= request.getAttribute("ukupnaZaliha") %></h3>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card p-3 text-center">
				<h6>Vrednost zalihe</h6>
				<h3><%= String.format("%.2f", (double) request.getAttribute("vrednostZalihe")) %> din</h3>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card p-3 text-center">
				<h6>Ukupan prihod</h6>
				<h3><%= String.format("%.2f", (double) request.getAttribute("ukupanPrihod")) %> din</h3>
			</div>
		</div>
	</div>

	<h4 class="mt-4">Sve narudzbine (<%= request.getAttribute("brojNarudzbina") %>)</h4>

	<%
	ArrayList<Narudzbina> narudzbine = (ArrayList<Narudzbina>) request.getAttribute("narudzbine");
	%>

	<% if (narudzbine.isEmpty()) { %>
	<div class="alert alert-info">Za sada nema nijedne narudzbine u ovoj apoteci.</div>
	<% } else { %>
	<table class="table table-bordered table-striped bg-white">
		<tr>
			<th>Datum</th>
			<th>Korisnik</th>
			<th>Lek</th>
			<th>Kolicina</th>
			<th>Cena po komadu</th>
			<th>Iskorisceni poeni</th>
			<th>Ukupno</th>
		</tr>
		<%
		for (int i = 0; i < narudzbine.size(); i++) {
			Narudzbina n = narudzbine.get(i);
		%>
		<tr>
			<td><%= n.getDatum() %></td>
			<td><%= n.getKorisnikImePrezime() %></td>
			<td><%= n.getLekNaziv() %></td>
			<td><%= n.getKolicina() %></td>
			<td><%= String.format("%.2f", n.getCenaPoKomadu()) %> din</td>
			<td><%= n.getIskorisceniPoeni() %></td>
			<td><b><%= String.format("%.2f", n.getUkupnaCena()) %> din</b></td>
		</tr>
		<% } %>
	</table>
	<% } %>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
