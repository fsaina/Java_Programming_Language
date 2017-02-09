package hr.fer.zemris.web.glasanje;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet class implementation used for obtaining the data from the local file
 * store and generating a XLS file required to the output stream. If any error
 * occurred while reading the definition files in the RecordController class, an
 * error message is shown to the user describing the error
 *
 * @author Filip Saina
 */
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 156229150389113517L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");
		ServletOutputStream outputStream = resp.getOutputStream();

		RecordController controller;

		try {
			controller = new RecordController(getServletContext());
		} catch (Exception e) {
			req.setAttribute("errorMessage", "Error with: " + e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		HSSFWorkbook hwb = setXlsFileData(controller);
		hwb.write(outputStream);

	}

	/**
	 * Sets the data to be shown in the XLS file. Method rads from the
	 * RecordController the List of bands and assigns a table cell value for
	 * every row.
	 *
	 * @param controller
	 *            RecordController controller object used from manipulation over
	 *            RecordModel objects
	 * @return HSSFWorkbook class abstraction over XLS files
	 */
	private HSSFWorkbook setXlsFileData(RecordController controller) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Voting results");

		HSSFRow rowZero = sheet.createRow(0);
		HSSFCell cellName = rowZero.createCell(0);
		HSSFCell cellVotes = rowZero.createCell(1);

		cellName.setCellValue("Band name:");
		cellVotes.setCellValue("Number of votes:");

		List<RecordModel> bands = controller.getBands();
		bands.sort(RecordController.byVotesComparator);

		int i = 1;
		for (RecordModel band : bands) {

			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(band.getName());
			row.createCell(1).setCellValue(band.getVotes());
			i++;
		}

		// resize
		HSSFRow row = hwb.getSheetAt(0).getRow(0);
		for (int colNum = 0; colNum < row.getLastCellNum(); colNum++)
			hwb.getSheetAt(0).autoSizeColumn(colNum);

		return hwb;
	}
}
