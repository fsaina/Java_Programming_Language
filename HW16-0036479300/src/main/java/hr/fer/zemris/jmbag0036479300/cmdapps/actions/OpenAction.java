package hr.fer.zemris.jmbag0036479300.cmdapps.actions;

import hr.fer.zemris.jmbag0036479300.cmdapps.JVDraw;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.Circle;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.FilledCircle;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.Line;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Open action object implementation
 */
public class OpenAction extends AbstractAction {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 6313601223411329996L;

	/**
     * JVDraw frame object reference
     */
    private JVDraw frame;

    /**
     * DrawingModel object reference
     */
    private DrawingModel drawingModel;

    /**
     * Default class implementation
     *
     * @param frame        JVDraw draw object
     * @param drawingModel DrawingModel draw object
     */
    public OpenAction(JVDraw frame, DrawingModel drawingModel) {
        this.frame = frame;
        this.drawingModel = drawingModel;

        putValue(Action.NAME, "Open...");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(Action.SHORT_DESCRIPTION, "Opens new graphical file - extension .jvd");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");

        if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        Path file = fileChooser.getSelectedFile().toPath();
        String extension = getFileExtension(file);
        if (!extension.equals("jvd")) {
            JOptionPane.showMessageDialog(frame,
                    "Chosen file must have .jvd extension", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Cannot read file", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<GeometricalObject> objects = openGeometricalObject(fileBytes);
        if (objects == null) {
            return;
        }

        List<GeometricalObject> dmObjects = new ArrayList<GeometricalObject>();

        for (int i = 0; i < drawingModel.getSize(); i++) {
            dmObjects.add(drawingModel.getObject(i));
        }

        for (GeometricalObject obj : dmObjects) {
            drawingModel.remove(obj);
        }

        for (GeometricalObject object : objects) {
            drawingModel.add(object);
        }

        frame.setTitle(file.toString());
        frame.setEditedCanvasFlag(false);
    }

    /**
     * Method opens the geometrical object from fileBytes
     *
     * @param fileBytes byte field returned
     * @return List<GeometricalObject> list of geometrical objects obtained from file
     */
    private List<GeometricalObject> openGeometricalObject(byte[] fileBytes) {

        String str = new String(fileBytes, StandardCharsets.UTF_8);
        String ls = System.lineSeparator();
        String[] lines = str.split(ls);

        List<GeometricalObject> objects = new ArrayList<GeometricalObject>();

        for (String line : lines) {
            String[] params = line.split(" ");
            GeometricalObject object = null;
            if (params[0].toLowerCase().equals("line")) {
                object = new Line(null, null);
                object = object.process(params);
            } else if (params[0].toLowerCase().equals("circle")) {
                object = new Circle(null, 0);
                object = object.process(params);
            } else if (params[0].toLowerCase().equals("fcircle")) {
                object = new FilledCircle(null, 0);
                object = object.process(params);
            } else {
                JOptionPane
                        .showMessageDialog(
                                frame,
                                "Invalid file content, requested file won't be pictured",
                                "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (object == null) {
                JOptionPane
                        .showMessageDialog(
                                frame,
                                "Invalid file content, requested file won't be pictured",
                                "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            objects.add(object);
        }

        return objects;
    }

    /**
     * Method returns the file extension of the filename
     *
     * @param file Path to the file representation
     * @return String file extension
     */
    private static String getFileExtension(Path file) {

        String path = file.toString();
        String[] split = path.split("\\.");

        if (split.length != 2) {
            return "";
        } else {
            return split[1];
        }
    }
}
