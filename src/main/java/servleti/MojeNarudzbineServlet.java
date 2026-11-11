package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import database.DBQueriesNarudzbina;
import model.Narudzbina;

// istorija kupovina ulogovanog klijenta
public class MojeNarudzbineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer korisnikId = (Integer) sesija.getAttribute("korisnikId");

		if (uloga == null || !uloga.equals("klijent") || korisnikId == null) {
			request.setAttribute("greska", "Istorija narudzbina je dostupna samo ulogovanim klijentima.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		ArrayList<Narudzbina> mojeNarudzbine = DBQueriesNarudzbina.getNarudzbineZaKorisnika(korisnikId);
		request.setAttribute("mojeNarudzbine", mojeNarudzbine);

		request.getRequestDispatcher("mojeNarudzbine.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
