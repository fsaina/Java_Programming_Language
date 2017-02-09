package hr.fer.zemris.web.trigonometry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet class implementation of the providing trigonometry calculation table. It takes three input integer
 * parameters and creates a given table of sin() and cos() values to a list.
 */
public class TrigonometricServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -1158558792853649930L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Double> sinValues = new ArrayList<>();
        List<Double> cosValues = new ArrayList<>();
        Integer a, b;

        final String DEF_A_VALUE = "0";
        final String DEF_B_VALUE = "360";

        a = testParameter(req, resp, DEF_A_VALUE, "a");
        b = testParameter(req, resp, DEF_B_VALUE, "b");

        if (a == null || b == null)
            return;

        if (a > b) {
            Integer c = new Integer(b);
            b = a;
            a = c;
        }

        if (b > a + 720) {
            b = a + 720;
        }

        Integer difference = b - a + 1;

        for (int i = 0; i < difference; i++) {
            sinValues.add(Math.sin(Math.toRadians(a + i)));
            cosValues.add(Math.cos(Math.toRadians(a + i)));
        }

        req.setAttribute("sinValues", sinValues);
        req.setAttribute("cosValues", cosValues);
        req.setAttribute("startValue", a);
        req.setAttribute("endValue", b);

        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }

    /**
     * Used for testing input parameters
     *
     * @param req          HttpServletRequest request class
     * @param resp         HttpServletResponse response class
     * @param defaultValue String providing the default value in case the parameter is not assigned
     * @param parameter    Parameters String name
     * @return Integer object valid
     *
     * @throws IOException      when sending a response message
     * @throws ServletException when sending the response message servlet
     */
    private Integer testParameter(HttpServletRequest req, HttpServletResponse resp, String defaultValue, String parameter) throws IOException, ServletException {
        Integer a;
        String aString = req.getParameter(parameter);

        if (aString == null) {
            aString = defaultValue;
        }

        try {
            a = Integer.parseInt(aString);
        } catch (NumberFormatException | NullPointerException num) {
            String message = "Non valid parameter > " + parameter + " < passed. Please provide a valid integer value.";

            req.setAttribute("errorMessage", message);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return null;
        }
        return a;
    }

}
