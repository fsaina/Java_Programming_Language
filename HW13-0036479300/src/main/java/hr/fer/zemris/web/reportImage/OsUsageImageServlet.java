package hr.fer.zemris.web.reportImage;

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

/**
 * Servlet class implementation for serving a png image representation of the Swing graph library apache poi
 * (http://mvnrepository.com/artifact/org.apache.poi/poi). Servlet returns a raw png image so no other
 * information is provided.
 */
public class OsUsageImageServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 203485759343177399L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("image/png");
        OutputStream out = resp.getOutputStream();

        BufferedImage bi = createImageStreamFromSwing();

        ImageIO.write(bi, "png", out);
    }

    /**
     * Method creates a new image stream and sets the frame around the swing graphics element. No title to the
     * image is provided
     *
     * @return BufferedImage object containing the information used for drawing the image
     */
    private BufferedImage createImageStreamFromSwing() {
        PieDataset dataset = createDataset();
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, "OS usage image data:");
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
     * Method fills in the dataset used. Current implementation uses the default data obtained from
     * http://www.roseindia.net/answers/viewqa/Java-Beginners/14946-how-to-create-an-excel-file-using-java.html
     *
     * @return PieDataset used for drawing the graph
     */
    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;

    }


    /**
     * Creates a chart using the provided dataset and the title
     *
     * @param dataset PieDataset used for drawing the graphics
     * @param title   String containing the title text
     * @return JFreeChart object representation of the graph
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
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
