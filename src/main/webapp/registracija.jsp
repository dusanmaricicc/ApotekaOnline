<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
<title>Registracija</title>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container d-flex justify-content-center align-items-center" style="min-height: 70vh;">
	<div class="card shadow p-4" style="width: 450px;">

		<h2 class="text-center mb-4">Registracija</h2>

		<form action="RegistracijaServlet" method="post" onsubmit="return proveriFormu()">

			<div class="mb-3">
				<label class="form-label">Ime</label>
				<input type="text" id="ime" name="ime" class="form-control" required onblur="proveriIme()">
				<div class="form-text">Mora poceti velikim slovom (npr. Ana).</div>
				<div id="imeGreska" class="text-danger small" style="display:none;">
					Ime mora poceti velikim slovom da biste mogli da nastavite dalje!
				</div>
			</div>

			<div class="mb-3">
				<label class="form-label">Prezime</label>
				<input type="text" id="prezime" name="prezime" class="form-control" required
					onfocus="proveriImePreUlaskaUPrezime()" onblur="proveriPrezime()">
				<div class="form-text">Mora poceti velikim slovom (npr. Anic).</div>
				<div id="prezimeGreska" class="text-danger small" style="display:none;">
					Prezime mora poceti velikim slovom da biste mogli da nastavite dalje!
				</div>
			</div>

			<div class="mb-3">
				<label class="form-label">Email</label>
				<input type="email" id="email" name="email" class="form-control" required
					onfocus="proveriPrezimePreUlaskaUEmail()">
			</div>

			<div class="mb-3">
				<label class="form-label">Korisnicko ime</label>
				<input type="text" name="username" class="form-control" required>
			</div>

			<div class="mb-3">
				<label class="form-label">Lozinka</label>
				<input type="password" id="password" name="password" class="form-control" required
					oninput="proveriPodudaranjeLozinki()">
			</div>

			<div class="mb-3">
				<label class="form-label">Potvrda lozinke</label>
				<input type="password" id="potvrdaLozinke" name="potvrdaLozinke" class="form-control" required
					oninput="proveriPodudaranjeLozinki()">
				<div id="lozinkaGreska" class="text-danger small" style="display:none;">
					Lozinke se ne poklapaju!
				</div>
			</div>

			<div class="d-grid">
				<button type="submit" class="btn btn-primary">Registruj se</button>
			</div>
		</form>
	</div>
</div>

<script>
	// regex koji proverava da prvo slovo bude veliko (ukljucujuci i nasa slova Č, Ć, Ž, Š, Đ)
	var regexVelikoSlovo = /^[A-ZČĆŽŠĐ]/;

	function proveriIme() {
		var imeInput = document.getElementById("ime");
		var greska = document.getElementById("imeGreska");

		if (!regexVelikoSlovo.test(imeInput.value)) {
			greska.style.display = "block";
			return false;
		} else {
			greska.style.display = "none";
			return true;
		}
	}

	function proveriPrezime() {
		var prezimeInput = document.getElementById("prezime");
		var greska = document.getElementById("prezimeGreska");

		if (!regexVelikoSlovo.test(prezimeInput.value)) {
			greska.style.display = "block";
			return false;
		} else {
			greska.style.display = "none";
			return true;
		}
	}

	// poziva se kada korisnik pokusa da udje u polje Prezime (klikom ili tabom)
	function proveriImePreUlaskaUPrezime() {
		if (!proveriIme()) {
			// ime nije ispravno uneto - vracamo fokus nazad na polje Ime
			document.getElementById("ime").focus();
		}
	}

	// poziva se kada korisnik pokusa da udje u polje Email (klikom ili tabom)
	function proveriPrezimePreUlaskaUEmail() {
		if (!proveriPrezime()) {
			// prezime nije ispravno uneto - vracamo fokus nazad na polje Prezime
			document.getElementById("prezime").focus();
		}
	}

	// proverava da li se lozinka i potvrda lozinke poklapaju - poziva se pri
	// svakom kucanju u oba polja, da bi se poruka prikazala/sakrila odmah
	function proveriPodudaranjeLozinki() {
		var password = document.getElementById("password").value;
		var potvrda = document.getElementById("potvrdaLozinke").value;
		var greska = document.getElementById("lozinkaGreska");

		if (potvrda.length > 0 && password !== potvrda) {
			greska.style.display = "block";
			return false;
		} else {
			greska.style.display = "none";
			return true;
		}
	}

	// poziva se pri slanju cele forme - ako nesto nije ispravno, sprecava
	// slanje na server i samo prikazuje odgovarajucu poruku ispod polja
	function proveriFormu() {
		var imeIspravno = proveriIme();
		var prezimeIspravno = proveriPrezime();
		var lozinkeIspravne = proveriPodudaranjeLozinki();

		return imeIspravno && prezimeIspravno && lozinkeIspravne;
	}
</script>

<jsp:include page="footer.jsp" />
</body>
</html>
