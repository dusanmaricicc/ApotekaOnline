package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import database.DBQueriesKorisnik;
import model.Korisnik;

// Servlet za registraciju novog klijenta sa sajta
public class RegistracijaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String potvrdaLozinke = request.getParameter("potvrdaLozinke");

		String poruka = "";
		boolean sviPodaciUneti = true;

		if (ime == null || ime.trim().isEmpty()) {
			poruka += "Niste uneli ime. ";
			sviPodaciUneti = false;
		} else if (!Character.isUpperCase(ime.charAt(0))) {
			poruka += "Ime mora poceti velikim slovom. ";
			sviPodaciUneti = false;
		}
		if (prezime == null || prezime.trim().isEmpty()) {
			poruka += "Niste uneli prezime. ";
			sviPodaciUneti = false;
		} else if (!Character.isUpperCase(prezime.charAt(0))) {
			poruka += "Prezime mora poceti velikim slovom. ";
			sviPodaciUneti = false;
		}
		if (email == null || !email.contains("@")) {
			poruka += "Email adresa nije ispravna. ";
			sviPodaciUneti = false;
		}
		if (username == null || username.trim().isEmpty()) {
			poruka += "Niste uneli korisnicko ime. ";
			sviPodaciUneti = false;
		}
		if (password == null || password.length() < 4) {
			poruka += "Lozinka mora imati bar 4 karaktera. ";
			sviPodaciUneti = false;
		} else if (potvrdaLozinke == null || !password.equals(potvrdaLozinke)) {
			poruka += "Lozinka i potvrda lozinke se ne poklapaju. ";
			sviPodaciUneti = false;
		}

		if (sviPodaciUneti && DBQueriesKorisnik.postojiUsername(username)) {
			poruka += "Korisnicko ime je vec zauzeto. ";
			sviPodaciUneti = false;
		}

		if (sviPodaciUneti) {
			Korisnik k = new Korisnik(ime, prezime, email, username, password);
			DBQueriesKorisnik.registrujKlijenta(k);
			request.setAttribute("poruka", "Uspesno ste se registrovali. Sada mozete da se ulogujete.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			request.setAttribute("greska", poruka);
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("registracija.jsp");
	}
}
