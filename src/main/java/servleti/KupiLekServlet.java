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
import model.Lek;
import model.Narudzbina;

// klijent kupuje odredjenu kolicinu jednog leka; moze i da iskoristi deo
// svojih poena za popust, a za svaku kupovinu dobija nove poene
// (1 poen = 1 dinar popusta, a svakih potrosenih 100 dinara donosi 1 poen)
public class KupiLekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int DINARA_PO_POENU = 100; // koliko dinara je potrebno potrositi za 1 poen

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer korisnikId = (Integer) sesija.getAttribute("korisnikId");

		if (uloga == null || !uloga.equals("klijent") || korisnikId == null) {
			request.setAttribute("greska", "Da biste kupili lek morate biti ulogovani kao klijent.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String lekIdParam = request.getParameter("lekId");
		String kolicinaParam = request.getParameter("kolicina");
		String iskoristiPoenaParam = request.getParameter("iskoristiPoena");

		String poruka = "";
		boolean ispravno = true;

		int lekId = 0;
		int kolicina = 0;
		try {
			lekId = Integer.parseInt(lekIdParam);
			kolicina = Integer.parseInt(kolicinaParam);
			if (kolicina <= 0) {
				poruka += "Kolicina mora biti veca od nule. ";
				ispravno = false;
			}
		} catch (Exception e) {
			poruka += "Kolicina nije ispravno uneta. ";
			ispravno = false;
		}

		Lek lek = ispravno ? DBQueriesLek.getLekPoId(lekId) : null;
		if (ispravno && lek == null) {
			poruka += "Trazeni lek ne postoji. ";
			ispravno = false;
		}
		if (ispravno && lek.getKolicina() < kolicina) {
			poruka += "Nema dovoljno leka na stanju (na raspolaganju je " + lek.getKolicina() + "). ";
			ispravno = false;
		}

		if (!ispravno) {
			request.setAttribute("greska", poruka);
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		double cenaPoKomadu = lek.cenaSaPopustom();
		double ukupnaCenaPrePoena = cenaPoKomadu * kolicina;

		int trenutniPoeni = (Integer) sesija.getAttribute("poeni");

		int iskorisceniPoeni = 0;
		if (iskoristiPoenaParam != null && !iskoristiPoenaParam.trim().isEmpty()) {
			try {
				iskorisceniPoeni = Integer.parseInt(iskoristiPoenaParam);
			} catch (Exception e) {
				iskorisceniPoeni = 0;
			}
		}
		// klijent ne moze da iskoristi vise poena nego sto ih ima, niti vise
		// nego sto iznosi cena narudzbine (1 poen = 1 dinar popusta)
		if (iskorisceniPoeni < 0) {
			iskorisceniPoeni = 0;
		}
		if (iskorisceniPoeni > trenutniPoeni) {
			iskorisceniPoeni = trenutniPoeni;
		}
		if (iskorisceniPoeni > ukupnaCenaPrePoena) {
			iskorisceniPoeni = (int) ukupnaCenaPrePoena;
		}

		double konacnaCena = ukupnaCenaPrePoena - iskorisceniPoeni;
		int dobijeniPoeni = (int) (konacnaCena / DINARA_PO_POENU);

		int noviBrojPoena = trenutniPoeni - iskorisceniPoeni + dobijeniPoeni;

		Narudzbina n = new Narudzbina(korisnikId, lek.getApotekaId(), lekId, kolicina, cenaPoKomadu, konacnaCena,
				iskorisceniPoeni, dobijeniPoeni);

		DBQueriesNarudzbina.kreirajNarudzbinu(n);
		DBQueriesLek.umanjiKolicinu(lekId, kolicina);
		DBQueriesKorisnik.azurirajPoene(korisnikId, noviBrojPoena);

		// azuriramo poene i u sesiji da bi se odmah videla nova vrednost
		sesija.setAttribute("poeni", noviBrojPoena);

		request.setAttribute("lek", lek);
		request.setAttribute("kolicina", kolicina);
		request.setAttribute("konacnaCena", konacnaCena);
		request.setAttribute("iskorisceniPoeni", iskorisceniPoeni);
		request.setAttribute("dobijeniPoeni", dobijeniPoeni);

		request.getRequestDispatcher("potvrdaKupovine.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("LekoviServlet");
	}
}
