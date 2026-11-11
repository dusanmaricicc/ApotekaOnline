package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import database.DBQueriesApoteka;
import database.DBQueriesKorisnik;
import model.Apoteka;
import model.Korisnik;

// prikaz spiska svih korisnika - dostupno samo administratoru
public class KorisniciServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");

		if (uloga == null || !uloga.equals("administrator")) {
			request.setAttribute("greska", "Ova stranica je dostupna samo administratoru.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		ArrayList<Korisnik> sviKorisnici = DBQueriesKorisnik.getSviKorisnici();
		ArrayList<Apoteka> sveApoteke = DBQueriesApoteka.getSveApoteke();

		request.setAttribute("sviKorisnici", sviKorisnici);
		request.setAttribute("sveApoteke", sveApoteke);

		request.getRequestDispatcher("korisnici.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
