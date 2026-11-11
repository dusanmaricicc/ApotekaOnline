package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import database.DBQueriesLek;
import model.Lek;

// prikaz lekova samo one apoteke u kojoj je ulogovani korisnik menadzer
public class MojiLekoviServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer apotekaId = (Integer) sesija.getAttribute("apotekaId");

		if (uloga == null || !uloga.equals("menadzer") || apotekaId == null) {
			request.setAttribute("greska", "Ova stranica je dostupna samo menadzeru apoteke.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		ArrayList<Lek> mojiLekovi = DBQueriesLek.getLekoviZaApoteku(apotekaId);
		request.setAttribute("mojiLekovi", mojiLekovi);

		request.getRequestDispatcher("mojiLekovi.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
