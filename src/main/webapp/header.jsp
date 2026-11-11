<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar">
	<div class="container d-flex justify-content-between align-items-center flex-wrap">

		<a class="navbar-brand fw-bold" href="index.jsp">Apoteka Online</a>

		<ul class="navbar-nav d-flex flex-row flex-wrap gap-3 mb-0">

			<li class="nav-item"><a href="index.jsp">Pocetna</a></li>
			<li class="nav-item"><a href="LekoviServlet">Lekovi</a></li>
			<li class="nav-item"><a href="ApotekeServlet">Apoteke</a></li>

			<%
			boolean ulogovan = false;
			if (session.getAttribute("logged") != null) {
				ulogovan = (boolean) session.getAttribute("logged");
			}

			if (!ulogovan) {
			%>
			<li class="nav-item"><a href="login.jsp">Prijava</a></li>
			<li class="nav-item"><a href="registracija.jsp">Registracija</a></li>
			<%
			} else {
				String uloga = (String) session.getAttribute("uloga");
			%>

			<% if ("administrator".equals(uloga)) { %>
			<li class="nav-item"><a href="KorisniciServlet">Korisnici</a></li>
			<% } %>

			<% if ("menadzer".equals(uloga)) { %>
			<li class="nav-item"><a href="MojiLekoviServlet">Moji lekovi</a></li>
			<li class="nav-item"><a href="IzvestajServlet">Izvestaj</a></li>
			<% } %>

			<% if ("klijent".equals(uloga)) { %>
			<li class="nav-item"><a href="MojeNarudzbineServlet">Moje narudzbine</a></li>
			<li class="nav-item">Poeni: <%= session.getAttribute("poeni") %></li>
			<% } %>

			<li class="nav-item">Zdravo, <%= session.getAttribute("imePrezime") %>!</li>
			<li class="nav-item"><a href="LogoutServlet">Odjava</a></li>

			<% } %>
		</ul>
	</div>
</nav>
