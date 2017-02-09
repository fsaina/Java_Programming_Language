package hr.fer.zemris.web.color;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet serving as a color picker setting the "pickedBgCol" attribute
 *
 * @author Filip Saina
 */
public class ColorServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5169945434849380525L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("pickedBgCol", "#" + req.getParameter("color"));
        req.getRequestDispatcher("colors.jsp").forward(req, resp);
    }
}
