<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Apoteka Online</title>
</head>
<body>

<jsp:include page="header.jsp" />

<section class="hero">
	<div class="container">
		<h1>Dobrodosli u Apoteku Online</h1>
		<p class="lead mt-3">
			Pregledajte ponudu lekova iz nasih apoteka, uporedite cene i popuste,
			i naruc­ite izabrane proizvode online.
		</p>
		<a href="LekoviServlet" class="btn btn-light btn-lg mt-3">Pogledaj ponudu lekova</a>
	</div>
</section>

<div class="container mt-4">
	<div class="row">
		<div class="col-md-4">
			<div class="card p-3">
				<h5>Pregled lekova</h5>
				<p>Svi posetioci mogu da pregledaju dostupne lekove, njihove cene i uslove dostave.</p>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card p-3">
				<h5>Registracija i poeni</h5>
				<p>Registrovani korisnici prilikom svake kupovine sakupljaju poene koje mogu iskoristiti
				za popust pri sledecoj kupovini.</p>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card p-3">
				<h5>Nase apoteke</h5>
				<p>Pogledajte spisak nasih apoteka, njihove adrese i gradove u kojima se nalaze.</p>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
