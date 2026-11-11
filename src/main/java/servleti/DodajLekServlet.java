package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesLek;
import model.Lek;

public class DodajLekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer apotekaId = (Integer) sesija.getAttribute("apotekaId");

		if (uloga == null || !uloga.equals("menadzer") || apotekaId == null) {
			request.setAttribute("greska", "Samo menadzer apoteke moze da dodaje lekove.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String naziv = request.getParameter("naziv");
		String opis = request.getParameter("opis");
		String cenaParam = request.getParameter("cena");
		String kolicinaParam = request.getParameter("kolicina");
		String popustParam = request.getParameter("popust");

		String poruka = "";
		boolean ispravno = true;

		if (naziv == null || naziv.trim().isEmpty()) {
			poruka += "Naziv leka je obavezan. ";
			ispravno = false;
		}

		double cena = 0;
		try {
			cena = Double.parseDouble(cenaParam);
			if (cena <= 0) {
				poruka += "Cena mora biti veca od nule. ";
				ispravno = false;
			}
		} catch (Exception e) {
			poruka += "Cena nije unesena ispravno. ";
			ispravno = false;
		}

		int kolicina = 0;
		try {
			kolicina = Integer.parseInt(kolicinaParam);
			if (kolicina < 0) {
				poruka += "Kolicina ne moze biti negativna. ";
				ispravno = false;
			}
		} catch (Exception e) {
			poruka += "Kolicina nije unesena ispravno. ";
			ispravno = false;
		}

		int popust = 0;
		try {
			popust = (popustParam == null || popustParam.trim().isEmpty()) ? 0 : Integer.parseInt(popustParam);
			if (popust < 0 || popust > 100) {
				poruka += "Popust mora biti izmedju 0 i 100. ";
				ispravno = false;
			}
		} catch (Exception e) {
			poruka += "Popust nije unesen ispravno. ";
			ispravno = false;
		}

		if (ispravno) {
			Lek l = new Lek(naziv, opis, cena, kolicina, popust, apotekaId);
			DBQueriesLek.dodajLek(l);
			response.sendRedirect("MojiLekoviServlet");
		} else {
			request.setAttribute("greska", poruka);
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
		}
	}
}
