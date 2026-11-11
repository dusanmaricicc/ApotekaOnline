<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Lek"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Potvrda kupovine</title>
</head>
<body>

<jsp:include page="header.jsp" />

<%
Lek lek = (Lek) request.getAttribute("lek");
int kolicina = (Integer) request.getAttribute("kolicina");
double konacnaCena = (Double) request.getAttribute("konacnaCena");
int iskorisceniPoeni = (Integer) request.getAttribute("iskorisceniPoeni");
int dobijeniPoeni = (Integer) request.getAttribute("dobijeniPoeni");
%>

<div class="container mt-4">
	<div class="alert alert-success">
		<h4>Kupovina je uspesno zavrsena!</h4>
	</div>

	<table class="table table-bordered bg-white" style="max-width: 600px;">
		<tr><th>Lek</th><td><%= lek.getNaziv() %></td></tr>
		<tr><th>Apoteka</th><td><%= lek.getApotekaNaziv() %> (<%= lek.getApotekaGrad() %>)</td></tr>
		<tr><th>Kolicina</th><td><%= kolicina %></td></tr>
		<tr><th>Iskorisceni poeni</th><td><%= iskorisceniPoeni %></td></tr>
		<tr><th>Ukupno za placanje</th><td><b><%= String.format("%.2f", konacnaCena) %> din</b></td></tr>
		<tr><th>Novi dobijeni poeni</th><td><%= dobijeniPoeni %></td></tr>
	</table>

	<a href="LekoviServlet" class="btn btn-secondary">Nazad na lekove</a>
	<a href="MojeNarudzbineServlet" class="btn btn-primary">Moje narudzbine</a>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
