package servleti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

// Servlet koji prikazuje poruku o gresci - isti princip kao na vezbama
// (npr. kada podaci u formi nisu validni)
public class GreskaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String greska = (String) request.getAttribute("greska");
		if (greska == null) {
			greska = "Doslo je do greske.";
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append("<html><head><meta charset=\"UTF-8\">"
				+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">"
				+ "</head><body><div class=\"container mt-5\">");
		response.getWriter().append("<div class=\"alert alert-danger\">" + greska + "</div>");
		response.getWriter().append("<a class=\"btn btn-secondary\" href=\"index.jsp\">Nazad na pocetnu</a>");
		response.getWriter().append("</div></body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
