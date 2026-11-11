package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import database.DBQueriesApoteka;
import model.Apoteka;

// prikaz spiska apoteka - vidljiv svima (posetioci vide adrese, administrator ima i CRUD forme)
public class ApotekeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Apoteka> sveApoteke = DBQueriesApoteka.getSveApoteke();
		request.setAttribute("sveApoteke", sveApoteke);
		request.getRequestDispatcher("apoteke.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
