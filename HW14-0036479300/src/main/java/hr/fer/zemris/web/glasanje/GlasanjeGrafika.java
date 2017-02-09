package hr.fer.zemris.web.glasanje;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Servlet class for creating the image preview of a Swing library visualization tool. If any error occurres
 * while reading the definition files in the RecordController class, an error message is shown to the user
 * describing the error
 *
 * @author Filip Saina.
 */
public class GlasanjeGrafika extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4791863788538580406L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("image/png");
        OutputStream out = resp.getOutputStream();

        RecordController controller;
        try {
            controller = new RecordController(getServletContext());
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error with: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        BufferedImage bi = createImageStream(controller);

        ImageIO.write(bi, "png", out);

    }

    /**
     * Method creates a new image stream and sets the frame around the swing graphics element. No title to the
     * image is provided
     *
     * @param controller RecordController object used for obtaining a list of bands
     * @return BufferedImage object containing the information used for drawing the image
     */
    private BufferedImage createImageStream(RecordController controller) {
        PieDataset dataset = createDataset(controller.getBands());
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, "");
        // we put the chart into a panel
        ChartPanel c = new ChartPanel(chart);

        JFrame frame = new JFrame();
        frame.setBackground(Color.WHITE);
        frame.setUndecorated(true);
        frame.getContentPane().add(c);
        frame.pack();
        BufferedImage bi = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bi.createGraphics();
        c.print(graphics);
        return bi;
    }

    /**
     * Method fills in the dataset used for graophing the bands with their values. If a band has zero votes it
     * is not drawn
     *
     * @param bands List of bands currently active
     * @return PieDataset used for drawing the graph
     */
    private PieDataset createDataset(List<RecordModel> bands) {

        DefaultPieDataset dataset = new DefaultPieDataset();

        for (RecordModel band : bands) {

            if (band.getVotes() == 0)
                continue;
            dataset.setValue(band.getName(), band.getVotes());
        }

        return dataset;
    }

    /**
     * Creates a chart using the provided dataset and the title
     *
     * @param dataset PieDataset used for drawing the graphics
     * @param title   String containin the title text
     * @return JFreeChart object representation of the graph
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
                title,          // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
