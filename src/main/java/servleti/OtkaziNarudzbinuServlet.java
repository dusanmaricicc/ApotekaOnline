package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesKorisnik;
import database.DBQueriesLek;
import database.DBQueriesNarudzbina;
import model.Narudzbina;

// klijent otkazuje sopstvenu narudzbinu - vraca se kolicina leka na stanje
// i ponistavaju se poeni koji su dodeljeni/iskorisceni prilikom te kupovine
public class OtkaziNarudzbinuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer korisnikId = (Integer) sesija.getAttribute("korisnikId");

		if (uloga == null || !uloga.equals("klijent") || korisnikId == null) {
			request.setAttribute("greska", "Samo ulogovani klijent moze da otkaze narudzbinu.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String idParam = request.getParameter("id");
		if (idParam == null || idParam.trim().isEmpty()) {
			request.setAttribute("greska", "Nedostaje id narudzbine koja se otkazuje.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		int narudzbinaId = Integer.parseInt(idParam);
		Narudzbina n = DBQueriesNarudzbina.getNarudzbinaPoId(narudzbinaId);

		if (n == null) {
			request.setAttribute("greska", "Trazena narudzbina ne postoji.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		// klijent sme da otkaze samo svoju narudzbinu
		if (n.getKorisnikId() != korisnikId) {
			request.setAttribute("greska", "Ne mozete otkazati tudju narudzbinu.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		// vracamo kupljenu kolicinu leka na stanje
		DBQueriesLek.povecajKolicinu(n.getLekId(), n.getKolicina());

		// ponistavamo poene: oduzimamo dobijene poene, vracamo iskoriscene poene
		int trenutniPoeni = (Integer) sesija.getAttribute("poeni");
		int noviBrojPoena = trenutniPoeni - n.getDobijeniPoeni() + n.getIskorisceniPoeni();
		if (noviBrojPoena < 0) {
			noviBrojPoena = 0;
		}
		DBQueriesKorisnik.azurirajPoene(korisnikId, noviBrojPoena);
		sesija.setAttribute("poeni", noviBrojPoena);

		// brisemo samu narudzbinu
		DBQueriesNarudzbina.obrisiNarudzbinu(narudzbinaId);

		response.sendRedirect("MojeNarudzbineServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
