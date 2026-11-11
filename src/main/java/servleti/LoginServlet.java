package servleti;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesKorisnik;
import model.Korisnik;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Korisnik uneseno = new Korisnik(username, password);

		Korisnik ulogovan = DBQueriesKorisnik.proveriKorisnika(uneseno);

		if (ulogovan != null) {
			HttpSession sesija = request.getSession();
			sesija.setAttribute("logged", true);
			sesija.setAttribute("korisnikId", ulogovan.getId());
			sesija.setAttribute("username", ulogovan.getUsername());
			sesija.setAttribute("imePrezime", ulogovan.getIme() + " " + ulogovan.getPrezime());
			sesija.setAttribute("uloga", ulogovan.getUloga());
			sesija.setAttribute("apotekaId", ulogovan.getApotekaId());
			sesija.setAttribute("poeni", ulogovan.getPoeni());

			response.sendRedirect("index.jsp");
		} else {
			request.setAttribute("poruka", "Korisnicko ime ili lozinka nisu tacni.");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}
}
