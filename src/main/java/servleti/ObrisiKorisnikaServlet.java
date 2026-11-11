package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesKorisnik;

public class ObrisiKorisnikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");

		if (uloga == null || !uloga.equals("administrator")) {
			request.setAttribute("greska", "Samo administrator moze da obrise korisnika.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String idParam = request.getParameter("id");
		if (idParam == null || idParam.trim().isEmpty()) {
			request.setAttribute("greska", "Nedostaje id korisnika koji se brise.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		DBQueriesKorisnik.obrisiKorisnika(Integer.parseInt(idParam));
		response.sendRedirect("KorisniciServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
