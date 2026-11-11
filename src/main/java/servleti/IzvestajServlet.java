package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import database.DBQueriesLek;
import database.DBQueriesNarudzbina;
import model.Lek;
import model.Narudzbina;

// izvestaj o poslovanju apoteke - vidljivo samo menadzeru te apoteke
public class IzvestajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer apotekaId = (Integer) sesija.getAttribute("apotekaId");

		if (uloga == null || !uloga.equals("menadzer") || apotekaId == null) {
			request.setAttribute("greska", "Izvestaj o poslovanju je dostupan samo menadzeru apoteke.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		ArrayList<Lek> lekovi = DBQueriesLek.getLekoviZaApoteku(apotekaId);
		ArrayList<Narudzbina> narudzbine = DBQueriesNarudzbina.getNarudzbineZaApoteku(apotekaId);
		double ukupanPrihod = DBQueriesNarudzbina.getUkupanPrihodZaApoteku(apotekaId);

		int ukupnaZaliha = 0;
		double vrednostZalihe = 0;
		for (int i = 0; i < lekovi.size(); i++) {
			Lek l = lekovi.get(i);
			ukupnaZaliha += l.getKolicina();
			vrednostZalihe += l.getKolicina() * l.cenaSaPopustom();
		}

		request.setAttribute("brojVrstaLekova", lekovi.size());
		request.setAttribute("ukupnaZaliha", ukupnaZaliha);
		request.setAttribute("vrednostZalihe", vrednostZalihe);
		request.setAttribute("brojNarudzbina", narudzbine.size());
		request.setAttribute("ukupanPrihod", ukupanPrihod);
		request.setAttribute("narudzbine", narudzbine);

		request.getRequestDispatcher("izvestaj.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
