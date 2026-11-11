package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import database.DBQueriesLek;
import model.Lek;

// javni katalog lekova - vide ga svi posetioci sajta (ulogovani i neulogovani)
public class LekoviServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Lek> sviLekovi = DBQueriesLek.getSviLekovi();
		request.setAttribute("sviLekovi", sviLekovi);

		request.getRequestDispatcher("katalog.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
