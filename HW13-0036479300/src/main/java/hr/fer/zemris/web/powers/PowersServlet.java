package hr.fer.zemris.web.powers;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class implementation for calculating all of the powers to a given value. The servlet server a XLS
 * file object to the returning stream and the file format specifies for all pages to provide power values for
 * a certain power (from 1 to 5). If an error occurs a message is send to the user
 *
 * @author Filip Saina
 */
public class PowersServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4794837821870008045L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/vnd.ms-excel");
        Integer a, b, n;

        a = validateIntegerParameter(req, resp, "a", -100, 100);
        b = validateIntegerParameter(req, resp, "b", -100, 100);
        n = validateIntegerParameter(req, resp, "n", 1, 5);

        if (a == null || b == null || n == null)
            return;

        if (a > b) {
            Integer c = new Integer(a);
            a = b;
            b = c;
        }

        ServletOutputStream outputStream = resp.getOutputStream();

        HSSFWorkbook hwb = createXLSData(a, b, n);

        hwb.write(outputStream);
    }

    /**
     * Used for initializing the data of the XLS file.
     *
     * @param a start value
     * @param b end value
     * @param n power value -- number of pages
     * @return HSSFWorkbook object containing the data required
     */
    private HSSFWorkbook createXLSData(Integer a, Integer b, Integer n) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        int difference = b - a;


        for (int i = 1; i <= n; i++) {
            HSSFSheet sheet = hwb.createSheet("Power to the " + i + "-th");

            HSSFRow rowZero = sheet.createRow(0);
            rowZero.createCell(0).setCellValue("numbers:");
            rowZero.createCell(1).setCellValue("power to " + i);

            for (int m = 0; m < difference; m++) {

                HSSFRow row = sheet.createRow(m + 1);
                row.createCell(0).setCellValue(a + m);
                row.createCell(1).setCellValue(Math.pow(a + m, i));
            }
        }

        //resize
        HSSFRow row = hwb.getSheetAt(0).getRow(0);
        for (int i = 0; i < n; i++) {
            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++)
                hwb.getSheetAt(i).autoSizeColumn(colNum);
        }

        return hwb;
    }

    /**
     * Method used for integer value validation of the used provided parameter's
     *
     * @param req       HttpServletRequest request object
     * @param resp      HttpServletResponse response object
     * @param parameter parameter String to be used for evaluation
     * @param start     valid number start
     * @param end       valid number end
     * @return Integer value of the String
     *
     * @throws ServletException when the requestDispatcher encounters an dispatching error
     * @throws IOException      when the requestDispatcher encounters an read/write error
     */
    private Integer validateIntegerParameter(HttpServletRequest req, HttpServletResponse resp, String parameter,
                                             int start, int end) throws ServletException, IOException {
        Integer number = null;
        try {
            number = Integer.parseInt(req.getParameter(parameter));
        } catch (NumberFormatException | NullPointerException nul) {
            req.setAttribute("errorMessage", "Invalid or missing parameter -" + parameter);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return null;
        }

        if (number < start || number > end) {
            req.setAttribute("errorMessage", "parameter - " + parameter +
                    " is not within bounds from " + start + " to " + end);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return null;
        }

        return number;
    }
}
