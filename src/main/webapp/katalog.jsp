<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Lek"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Lekovi</title>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container mt-4">
	<h2>Ponuda lekova</h2>
	<p>Ovde mozete pogledati sve lekove dostupne u nasim apotekama, njihove cene, popuste i kolicine na
	stanju. Dostava se vrsi na adresu koju unesete prilikom kupovine, a placanje se vrsi pouzecem.</p>

	<%
	boolean ulogovan = session.getAttribute("logged") != null && (boolean) session.getAttribute("logged");
	String uloga = (String) session.getAttribute("uloga");
	boolean jeKlijent = ulogovan && "klijent".equals(uloga);

	ArrayList<Lek> sviLekovi = (ArrayList<Lek>) request.getAttribute("sviLekovi");
	%>

	<% if (!jeKlijent) { %>
	<div class="alert alert-info">
		Da biste kupili lek, potrebno je da se <a href="login.jsp">prijavite</a> kao klijent
		ili da se <a href="registracija.jsp">registrujete</a>.
	</div>
	<% } %>

	<table class="table table-bordered table-striped bg-white">
		<tr>
			<th>Naziv</th>
			<th>Opis</th>
			<th>Apoteka</th>
			<th>Cena</th>
			<th>Popust</th>
			<th>Cena sa popustom</th>
			<th>Na stanju</th>
			<% if (jeKlijent) { %>
			<th>Kupovina</th>
			<% } %>
		</tr>

		<%
		for (int i = 0; i < sviLekovi.size(); i++) {
			Lek l = sviLekovi.get(i);
		%>
		<tr>
			<td><%= l.getNaziv() %></td>
			<td><%= l.getOpis() %></td>
			<td><%= l.getApotekaNaziv() %> (<%= l.getApotekaGrad() %>)</td>
			<td><%= l.getCena() %> din</td>
			<td><%= l.getPopust() %>%</td>
			<td><b><%= String.format("%.2f", l.cenaSaPopustom()) %> din</b></td>
			<td><%= l.getKolicina() %></td>

			<% if (jeKlijent) { %>
			<td>
				<% if (l.getKolicina() > 0) { %>
				<form action="KupiLekServlet" method="post" class="d-flex flex-column gap-1">
					<input type="hidden" name="lekId" value="<%= l.getId() %>">
					<div class="d-flex gap-1">
						<input type="number" name="kolicina" min="1" max="<%= l.getKolicina() %>" value="1"
							class="form-control form-control-sm" style="width: 65px;" title="Kolicina">
						<input type="number" name="iskoristiPoena" min="0" value="0"
							class="form-control form-control-sm" style="width: 65px;" title="Poeni za popust">
					</div>
					<button type="submit" class="btn btn-sm btn-success">Kupi</button>
				</form>
				<% } else { %>
				<span class="text-danger">Nema na stanju</span>
				<% } %>
			</td>
			<% } %>
		</tr>
		<% } %>
	</table>

	<% if (jeKlijent) { %>
	<p class="text-muted">
		Napomena: u drugo polje pored kolicine mozete uneti broj poena koje zelite da iskoristite za
		popust na tekucu kupovinu (1 poen = 1 dinar popusta). Trenutno imate
		<b><%= session.getAttribute("poeni") %></b> poena. Za svakih <%= 100 %> dinara placenih pri kupovini
		dobijate 1 novi poen.
	</p>
	<% } %>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
