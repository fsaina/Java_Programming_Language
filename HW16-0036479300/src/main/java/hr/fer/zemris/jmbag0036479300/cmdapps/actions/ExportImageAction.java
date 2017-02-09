package hr.fer.zemris.jmbag0036479300.cmdapps.actions;

import hr.fer.zemris.jmbag0036479300.cmdapps.JVDraw;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Export the given object as a image implementation action
 */
public class ExportImageAction extends AbstractAction {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 4478454771932742838L;

	/**
     * Root drawing model frame
     */
    private JVDraw rootFrame;

    /**
     * DrawingModel reference
     */
    private DrawingModel drawingmodel;

    /**
     * Instantiates a new Export image action.
     *
     * @param rootFrame    the root frame
     * @param drawingmodel the drawingmodel
     */
    public ExportImageAction(JVDraw rootFrame, DrawingModel drawingmodel) {
        this.rootFrame = rootFrame;
        this.drawingmodel = drawingmodel;

        putValue(Action.NAME, "Export");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(Action.SHORT_DESCRIPTION, "Exports as picture");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export");

        FileFilter jpgFilter = new TypeFilter(".jpg", "JPG picture");
        FileFilter pngFilter = new TypeFilter(".png", "PNG picture");
        FileFilter gifFilter = new TypeFilter(".gif", "GIF picture");

        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(gifFilter);

        if (fileChooser.showSaveDialog(rootFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        Path file = fileChooser.getSelectedFile().toPath();
        FileFilter extensionFilter = fileChooser.getFileFilter();
        if (!extensionFilter.accept(file.toFile())) {
            String temp = file.toString() + extensionFilter.toString();
            file = new File(temp).toPath();
        }
        if (Files.exists(file)) {
            int result = JOptionPane.showConfirmDialog(rootFrame, "Chosen file ("
                            + file + ") already exists. Do you want to overwrite it?",
                    "Warning", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        List<GeometricalObject> geomObjects = new ArrayList<GeometricalObject>();
        // make copies of shapes in drawing model
        for (int i = 0; i < drawingmodel.getSize(); i++) {
            GeometricalObject obj = drawingmodel.getObject(i);
            geomObjects.add(obj.copy());
        }

        Dimension d = generateDimObjects(geomObjects);

        BufferedImage image = new BufferedImage(d.width, d.height,
                BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, d.width, d.height);
        for (GeometricalObject obj : geomObjects) {
            obj.drawObject(g, null, null);
        }
        g.dispose();
        String ext = Util.getFileExtension(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, ext, bos);
            FileOutputStream fos = new FileOutputStream(file.toFile());
            fos.write(bos.toByteArray());
            fos.close();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(rootFrame, "Cannot export image",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(rootFrame, "Export success",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Generates the dimension and objects
     *
     * @param geomObjects List to generate dimensions
     * @return Dimension of the given objects
     */
    private Dimension generateDimObjects(List<GeometricalObject> geomObjects) {

        int maxX = 0;
        int maxY = 0;
        int minX = rootFrame.getWidth();
        int minY = rootFrame.getHeight();


        for (int i = 0; i < drawingmodel.getSize(); i++) {

            JPoint max = new JPoint(0, 0);
            JPoint min = new JPoint(0, 0);
            if (drawingmodel.getObject(i) instanceof Line) {
                Line object = (Line) drawingmodel.getObject(i);
                lineFind(object, min, max);

            } else if (drawingmodel.getObject(i) instanceof Circle) {
                Circle object = (Circle) drawingmodel.getObject(i);
                circleFind(object, min, max);
            } else if (drawingmodel.getObject(i) instanceof FilledCircle) {
                FilledCircle object = (FilledCircle) drawingmodel.getObject(i);
                circleFind(object, min, max);
            }

            maxX = Math.max(maxX, max.x);
            minX = Math.min(minX, min.x);
            maxY = Math.max(maxY, max.y);
            minY = Math.min(minY, min.y);
        }

        createNewObject(geomObjects, minX, minY);

        return new Dimension(maxX - minX, maxY - minY);
    }

    /**
     * Method generates a new object from given parameters
     *
     * @param geomObjects list of geometrical objects
     * @param minX        minimal x value
     * @param minY        minimal y value
     */
    private void createNewObject(List<GeometricalObject> geomObjects, int minX, int minY) {

        for (GeometricalObject obj : geomObjects)
            if (obj instanceof Line) {
                int x1 = (int) (((Line) obj).getStartJPoint().getx() - minX);
                int x2 = (int) (((Line) obj).getStartJPoint().gety() - minX);
                int y1 = (int) (((Line) obj).getEndJPoint().getx() - minY);
                int y2 = (int) (((Line) obj).getStartJPoint().gety() - minY);
                ((Line) obj).setStart(new JPoint(x1, y1));
                ((Line) obj).setEnd(new JPoint(x2, y2));
            } else if (obj instanceof Circle) {
                int centerX = ((Circle) obj).getCenter().getx() - minX;
                int centerY = ((Circle) obj).getCenter().gety() - minY;
                ((Circle) obj).setCenter(new JPoint(centerX, centerY));
            } else if (obj instanceof FilledCircle) {
                int centerX = ((FilledCircle) obj).getCenter().getx() - minX;
                int centerY = ((FilledCircle) obj).getCenter().gety() - minY;
                ((FilledCircle) obj).setCenter(new JPoint(centerX, centerY));
            }
    }

    /**
     * Find a given circle
     *
     * @param object AbstractCircle circle object to find
     * @param min    min position
     * @param max    max position
     */
    private void circleFind(AbstractCircle object, JPoint min, JPoint max) {

        int minX = object.getCenter().getx() - object.getRadius();
        int minY = object.getCenter().gety() - object.getRadius();

        int maxX = object.getCenter().getx() + object.getRadius();
        int maxY = object.getCenter().gety() + object.getRadius();

        min.setLocation(minX, minY);
        max.setLocation(maxX, maxY);
    }

    /**
     * Find a given line
     *
     * @param object Line object to find
     * @param min    min position
     * @param max    min position
     */
    private void lineFind(Line object, JPoint min, JPoint max) {

        int minX = Math.min(object.getStartJPoint().getx(), object.getEndJPoint().getx());
        int minY = Math.min(object.getStartJPoint().gety(), object.getStartJPoint().gety());

        int maxX = Math.max(object.getStartJPoint().getx(), object.getStartJPoint().getx());
        int maxY = Math.max(object.getStartJPoint().gety(), object.getStartJPoint().gety());

        min.setLocation(minX, minY);
        max.setLocation(maxX, maxY);
    }
}
