package hr.fer.zemris.web.glasanje;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet class implementation used for obtaining the data from the local file store and sorting it according
 * to the id-s (lower is better. If any error occurres while reading the definition files in the
 * RecordController class, an error message is shown to the user describing the error
 *
 * @author Filip Saina
 */
public class GlasanjeServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1122237177764718530L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RecordController controller;

        try {
            controller = new RecordController(getServletContext());
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error with: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        List<RecordModel> bands = controller.getBands();
        bands.sort(RecordController.byIdComparator);

        req.setAttribute("bandNames", bands);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
