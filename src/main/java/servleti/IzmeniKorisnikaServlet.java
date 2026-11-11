package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesKorisnik;
import model.Korisnik;

public class IzmeniKorisnikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");

		if (uloga == null || !uloga.equals("administrator")) {
			request.setAttribute("greska", "Samo administrator moze da izmeni korisnika.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String idParam = request.getParameter("id");
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String novaLozinka = request.getParameter("password");
		String novaUloga = request.getParameter("novaUloga");
		String apotekaIdParam = request.getParameter("apotekaId");

		String poruka = "";
		boolean ispravno = true;

		if (idParam == null || idParam.trim().isEmpty()) {
			poruka += "Nedostaje id korisnika. ";
			ispravno = false;
		}
		if (ime == null || ime.trim().isEmpty() || prezime == null || prezime.trim().isEmpty()) {
			poruka += "Ime i prezime su obavezni. ";
			ispravno = false;
		}
		if (username == null || username.trim().isEmpty()) {
			poruka += "Korisnicko ime je obavezno. ";
			ispravno = false;
		}

		if (ispravno) {
			Korisnik k = new Korisnik();
			k.setId(Integer.parseInt(idParam));
			k.setIme(ime);
			k.setPrezime(prezime);
			k.setEmail(email);
			k.setUsername(username);
			k.setUloga(novaUloga);
			if (novaUloga != null && novaUloga.equals("menadzer") && apotekaIdParam != null
					&& !apotekaIdParam.trim().isEmpty()) {
				k.setApotekaId(Integer.parseInt(apotekaIdParam));
			} else {
				k.setApotekaId(null);
			}

			boolean menjaSeLozinka = novaLozinka != null && !novaLozinka.trim().isEmpty();
			if (menjaSeLozinka) {
				k.setPassword(novaLozinka);
			}

			DBQueriesKorisnik.izmeniKorisnika(k, menjaSeLozinka);
			response.sendRedirect("KorisniciServlet");
		} else {
			request.setAttribute("greska", poruka);
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
		}
	}
}
