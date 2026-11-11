<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Narudzbina"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Moje narudzbine</title>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container mt-4">
	<h2>Moje narudzbine</h2>
	<p>Trenutno stanje poena: <b><%= session.getAttribute("poeni") %></b></p>

	<%
	ArrayList<Narudzbina> mojeNarudzbine = (ArrayList<Narudzbina>) request.getAttribute("mojeNarudzbine");
	%>

	<% if (mojeNarudzbine.isEmpty()) { %>
	<div class="alert alert-info">Jos uvek nemate nijednu narudzbinu.</div>
	<% } else { %>
	<table class="table table-bordered table-striped bg-white">
		<tr>
			<th>Datum</th>
			<th>Lek</th>
			<th>Apoteka</th>
			<th>Kolicina</th>
			<th>Cena po komadu</th>
			<th>Iskorisceni poeni</th>
			<th>Dobijeni poeni</th>
			<th>Ukupno placeno</th>
			<th>Akcija</th>
		</tr>
		<%
		for (int i = 0; i < mojeNarudzbine.size(); i++) {
			Narudzbina n = mojeNarudzbine.get(i);
		%>
		<tr>
			<td><%= n.getDatum() %></td>
			<td><%= n.getLekNaziv() %></td>
			<td><%= n.getApotekaNaziv() %></td>
			<td><%= n.getKolicina() %></td>
			<td><%= String.format("%.2f", n.getCenaPoKomadu()) %> din</td>
			<td><%= n.getIskorisceniPoeni() %></td>
			<td><%= n.getDobijeniPoeni() %></td>
			<td><b><%= String.format("%.2f", n.getUkupnaCena()) %> din</b></td>
			<td>
				<a href="OtkaziNarudzbinuServlet?id=<%= n.getId() %>" class="btn btn-sm btn-danger"
					onclick="return confirm('Da li ste sigurni da zelite da otkazete ovu narudzbinu? Kolicina leka ce biti vracena na stanje, a poeni ponisteni.');">
					Otkazi
				</a>
			</td>
		</tr>
		<% } %>
	</table>
	<% } %>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
