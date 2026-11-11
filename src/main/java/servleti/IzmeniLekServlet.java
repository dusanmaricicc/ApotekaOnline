package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import database.DBQueriesLek;
import model.Lek;

public class IzmeniLekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesija = request.getSession();
		String uloga = (String) sesija.getAttribute("uloga");
		Integer apotekaId = (Integer) sesija.getAttribute("apotekaId");

		if (uloga == null || !uloga.equals("menadzer") || apotekaId == null) {
			request.setAttribute("greska", "Samo menadzer apoteke moze da izmeni lek.");
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
			return;
		}

		String idParam = request.getParameter("id");
		String naziv = request.getParameter("naziv");
		String opis = request.getParameter("opis");
		String cenaParam = request.getParameter("cena");
		String kolicinaParam = request.getParameter("kolicina");
		String popustParam = request.getParameter("popust");

		String poruka = "";
		boolean ispravno = true;

		if (idParam == null || naziv == null || naziv.trim().isEmpty()) {
			poruka += "Nedostaju osnovni podaci o leku. ";
			ispravno = false;
		}

		double cena = 0;
		int kolicina = 0;
		int popust = 0;
		try {
			cena = Double.parseDouble(cenaParam);
			kolicina = Integer.parseInt(kolicinaParam);
			popust = (popustParam == null || popustParam.trim().isEmpty()) ? 0 : Integer.parseInt(popustParam);
			if (cena <= 0 || kolicina < 0 || popust < 0 || popust > 100) {
				poruka += "Cena, kolicina ili popust nisu ispravno uneti. ";
				ispravno = false;
			}
		} catch (Exception e) {
			poruka += "Cena, kolicina i popust moraju biti brojevi. ";
			ispravno = false;
		}

		if (ispravno) {
			Lek l = new Lek();
			l.setId(Integer.parseInt(idParam));
			l.setNaziv(naziv);
			l.setOpis(opis);
			l.setCena(cena);
			l.setKolicina(kolicina);
			l.setPopust(popust);
			l.setApotekaId(apotekaId); // menadzer moze da menja samo lekove svoje apoteke

			DBQueriesLek.izmeniLek(l);
			response.sendRedirect("MojiLekoviServlet");
		} else {
			request.setAttribute("greska", poruka);
			request.getRequestDispatcher("GreskaServlet").forward(request, response);
		}
	}
}
