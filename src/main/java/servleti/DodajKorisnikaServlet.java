package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesKorisnik;
import model.Korisnik;

// administrator dodaje novog korisnika (administratora, menadzera ili klijenta)
public class DodajKorisnikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");

		if (uloga == null || !uloga.equals("administrator")) {
			request.setAttribute("greska", "Samo administrator moze da dodaje korisnike.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String novaUloga = request.getParameter("novaUloga");
		String apotekaIdParam = request.getParameter("apotekaId");

		String poruka = "";
		boolean ispravno = true;

		if (ime == null || ime.trim().isEmpty()) {
			poruka += "Nedostaje ime. ";
			ispravno = false;
		}
		if (prezime == null || prezime.trim().isEmpty()) {
			poruka += "Nedostaje prezime. ";
			ispravno = false;
		}
		if (username == null || username.trim().isEmpty()) {
			poruka += "Nedostaje korisnicko ime. ";
			ispravno = false;
		}
		if (password == null || password.length() < 4) {
			poruka += "Lozinka mora imati bar 4 karaktera. ";
			ispravno = false;
		}
		if (novaUloga == null
				|| !(novaUloga.equals("administrator") || novaUloga.equals("menadzer") || novaUloga.equals("klijent"))) {
			poruka += "Nepoznata uloga. ";
			ispravno = false;
		}
		if (ispravno && username != null && DBQueriesKorisnik.postojiUsername(username)) {
			poruka += "Korisnicko ime je vec zauzeto. ";
			ispravno = false;
		}
		if (ispravno && novaUloga.equals("menadzer") && (apotekaIdParam == null || apotekaIdParam.trim().isEmpty())) {
			poruka += "Za menadzera morate izabrati apoteku. ";
			ispravno = false;
		}

		if (ispravno) {
			Korisnik k = new Korisnik();
			k.setIme(ime);
			k.setPrezime(prezime);
			k.setEmail(email);
			k.setUsername(username);
			k.setPassword(password);
			k.setUloga(novaUloga);
			if (novaUloga.equals("menadzer") && apotekaIdParam != null && !apotekaIdParam.trim().isEmpty()) {
				k.setApotekaId(Integer.parseInt(apotekaIdParam));
			} else {
				k.setApotekaId(null);
			}
			DBQueriesKorisnik.dodajKorisnika(k);
			response.sendRedirect("KorisniciServlet");
		} else {
			request.setAttribute("greska", poruka);
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
		}
	}
}
