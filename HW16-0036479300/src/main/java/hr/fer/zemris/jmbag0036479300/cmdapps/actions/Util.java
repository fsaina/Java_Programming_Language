package hr.fer.zemris.jmbag0036479300.cmdapps.actions;

import hr.fer.zemris.jmbag0036479300.cmdapps.JVDraw;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Util class for the Action implementation. Contains logic that can be used cross application
 */
public class Util {

    /**
     * Method handles the generation of files from drawing models
     *
     * @param drawingModel the drawing model
     * @return File String representation from the drawing model
     */
    private static String generateFile(DrawingModel drawingModel) {

        StringBuilder sb = new StringBuilder();
        String ls = System.lineSeparator();

        for (int i = 0; i < drawingModel.getSize(); i++) {
            GeometricalObject object = drawingModel.getObject(i);
            String s = object.toString();
            if (i < drawingModel.getSize() - 1) {
                sb.append(s).append(ls);
            } else {
                sb.append(s);
            }
        }

        return sb.toString();
    }

    /**
     * Method hadles the exit event
     *
     * @param frame        the frame
     * @param drawingModel the drawing model
     */
    public static void exit(JVDraw frame, DrawingModel drawingModel) {

        if (frame.getEditedCanvasFlag()) {

            int result = JOptionPane.showConfirmDialog(frame,
                    "Would you like to save the current image?", "Save",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                saveAs(frame, drawingModel);
            }
        }

        int result = JOptionPane.showConfirmDialog(frame,
                "Close the application ?", "Close", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }


    /**
     * Method handles the save as event
     *
     * @param frame        the frame
     * @param drawingModel the drawing model
     */
    public static void saveAs(JVDraw frame, DrawingModel drawingModel) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as");

        if (fileChooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
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
        if (Files.exists(file)) {
            int result = JOptionPane.showConfirmDialog(frame, "Chosen file ("
                            + file + ") already exists. Do you want to overwrite it?",
                    "Warning", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        String text = generateFile(drawingModel);
        try {
            Files.write(file, text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error while writing file to filesystem ("
                            + file + "):" + e.getMessage(), "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        frame.setEditedCanvasFlag(false);
        frame.setTitle(file.toString());
    }

    /**
     * Gets file extension.
     *
     * @param file the file
     * @return the file extension
     */
    public static String getFileExtension(Path file) {

        String path = file.toString();
        String[] split = path.split("\\.");

        if (split.length != 2) {
            return "";
        } else {
            return split[1];
        }
    }
}
