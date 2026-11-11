package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesLek;

public class ObrisiLekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer apotekaId = (Integer) sesija.getAttribute("apotekaId");

		if (uloga == null || !uloga.equals("menadzer") || apotekaId == null) {
			request.setAttribute("greska", "Samo menadzer apoteke moze da obrise lek.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String idParam = request.getParameter("id");
		if (idParam == null || idParam.trim().isEmpty()) {
			request.setAttribute("greska", "Nedostaje id leka koji se brise.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		// menadzer moze da obrise samo lek iz svoje apoteke (proverava se u DAO sloju)
		DBQueriesLek.obrisiLek(Integer.parseInt(idParam), apotekaId);
		response.sendRedirect("MojiLekoviServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
