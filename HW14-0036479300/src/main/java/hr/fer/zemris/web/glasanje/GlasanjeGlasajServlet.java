package hr.fer.zemris.web.glasanje;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class used for voting for the selected id. The internal model, trough the RecordController class,
 * is incremented and written to the provided output.
 */
public class GlasanjeGlasajServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3702331764027913820L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RecordController controller;
        try {
            controller = new RecordController(getServletContext());

            String idString = req.getParameter("id");

            if (idString == null)
                throw new IllegalArgumentException("You did not specify the id number to be voted");
            Integer.parseInt(idString);

            controller.incrementVoteForId(idString);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error with: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }


}
