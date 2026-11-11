package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesApoteka;
import model.Apoteka;

public class DodajApotekuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");

		if (uloga == null || !uloga.equals("administrator")) {
			request.setAttribute("greska", "Samo administrator moze da dodaje apoteke.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String naziv = request.getParameter("naziv");
		String adresa = request.getParameter("adresa");
		String grad = request.getParameter("grad");

		if (naziv == null || naziv.trim().isEmpty() || adresa == null || adresa.trim().isEmpty() || grad == null
				|| grad.trim().isEmpty()) {
			request.setAttribute("greska", "Naziv, adresa i grad apoteke su obavezni.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		DBQueriesApoteka.dodajApoteku(new Apoteka(naziv, adresa, grad));
		response.sendRedirect("ApotekeServlet");
	}
}
