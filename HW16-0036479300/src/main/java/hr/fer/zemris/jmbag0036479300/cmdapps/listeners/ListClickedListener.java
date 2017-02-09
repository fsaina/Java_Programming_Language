package hr.fer.zemris.jmbag0036479300.cmdapps.listeners;

import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.JColorArea;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.Circle;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.FilledCircle;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type List clicked listener.
 */
public class ListClickedListener extends MouseAdapter {

    /**
     * List model elements currently in
     */
    private JList<GeometricalObject> geometricalObjectJList;

    /**
     * Drawing model abstraction with given elements
     */
    private DrawingModel drawingModel;

    /**
     * Instantiates a new List clicked listener.
     *
     * @param drawingModel           the drawing model
     * @param geometricalObjectJList the geometricalObjectJList
     */
    public ListClickedListener(DrawingModel drawingModel,
                               JList<GeometricalObject> geometricalObjectJList) {

        this.geometricalObjectJList = geometricalObjectJList;
        this.drawingModel = drawingModel;
    }

    /**
     * Method acts like a mutex after the user double clicks on a certain element in the list
     *
     * @param index int index of the element clicked
     */
    private void provideUser(int index) {

        GeometricalObject object = geometricalObjectJList.getModel().getElementAt(index);

        if (object instanceof Line) {
            showOptionPaneLine((Line) object);
        } else if (object instanceof Circle) {
            showOptionPaneCircle((Circle) object);
        } else if (object instanceof FilledCircle) {
            showOptionPaneFCircle((FilledCircle) object);
        } else {
            return;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        Object object = e.getSource();

        if (!(object instanceof JList<?>)) {
            return;
        }
        if (e.getClickCount() != 2) {
            return;
        }

        int index = geometricalObjectJList.locationToIndex(e.getPoint());

        provideUser(index);
    }

    /**
     * Method used for showing the user a settings pane to select values for the double cliked FilledCircle
     * object on the drawing canvas
     *
     * @param object FilledCircle object to be updated
     */
    private void showOptionPaneFCircle(FilledCircle object) {

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Center X: "));
        JTextField centerX = new JTextField();
        centerX.setText(Integer.toString(object.getCenter().getx()));
        panel.add(centerX);
        panel.add(new JLabel("Center Y: "));
        JTextField centerY = new JTextField();
        centerY.setText(Integer.toString(object.getCenter().gety()));
        panel.add(centerY);
        panel.add(new JLabel("Radius: "));
        JTextField radius = new JTextField();
        radius.setText(Integer.toString(object.getRadius()));
        panel.add(radius);

        panel.add(new JLabel("Choose foreground color: "));
        JColorArea foregroundColor = new JColorArea(
                object.getPrimaryColor(), false);
        panel.add(foregroundColor);
        panel.add(new JLabel("Choose background color: "));
        JColorArea backgroundColor = new JColorArea(
                object.getBackgroundColor(), false);
        panel.add(backgroundColor);

        int result = JOptionPane.showConfirmDialog(geometricalObjectJList, panel,
                "Edit filled circle", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        int x, y, r;
        try {
            x = Integer.parseInt(centerX.getText());
            y = Integer.parseInt(centerY.getText());
            r = Integer.parseInt(radius.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(geometricalObjectJList, "Invalid parameters", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Color bColor = backgroundColor.getCurrentColor();
        Color fColor = foregroundColor.getCurrentColor();

        object.setBackgroundColor(bColor);
        object.setForegroundColor(fColor);
        object.setCenter(new JPoint(x, y));
        object.setRadius(r);

        drawingModel.objectChanged(object);
    }

    /**
     * Method used for showing the user a settings pane to select values for the double cliked Line object on
     * the drawing canvas
     *
     * @param line Line object to be updated
     */
    private void showOptionPaneLine(Line line) {

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Start X: "));
        JTextField startX = new JTextField();
        startX.setText(Integer.toString((int) line.getStartJPoint().getX()));
        panel.add(startX);

        panel.add(new JLabel("Start Y: "));
        JTextField startY = new JTextField();
        startY.setText(Integer.toString((int) line.getStartJPoint().getY()));
        panel.add(startY);

        panel.add(new JLabel("End X: "));
        JTextField endX = new JTextField();
        endX.setText(Integer.toString((int) line.getEndJPoint().getX()));
        panel.add(endX);

        panel.add(new JLabel("End Y: "));
        JTextField endY = new JTextField();
        endY.setText(Integer.toString((int) line.getEndJPoint().getY()));
        panel.add(endY);

        panel.add(new JLabel("Choose line color: "));
        JColorArea lineColor = new JColorArea(line.getPrimaryColor(), false);
        panel.add(lineColor);

        int result = JOptionPane.showConfirmDialog(geometricalObjectJList, panel, "Edit line", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        int x1, x2, y1, y2;
        try {
            x1 = Integer.parseInt(startX.getText());
            y1 = Integer.parseInt(startY.getText());
            x2 = Integer.parseInt(endX.getText());
            y2 = Integer.parseInt(endY.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(geometricalObjectJList, "Invalid parameters", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Color color = lineColor.getCurrentColor();

        line.setForegroundColor(color);
        line.setStart(new JPoint(x1, y1));
        line.setEnd(new JPoint(x2, y2));

        drawingModel.objectChanged(line);
    }

    /**
     * Method used for showing the user a settings pane to select values for the double clicked Circle object
     * on the drawing canvas
     *
     * @param object Circle object to be updated
     */
    private void showOptionPaneCircle(Circle object) {

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Center X: "));
        JTextField centerX = new JTextField();
        centerX.setText(Integer.toString(object.getCenter().getx()));
        panel.add(centerX);
        panel.add(new JLabel("Center Y: "));
        JTextField centerY = new JTextField();
        centerY.setText(Integer.toString(object.getCenter().getx()));
        panel.add(centerY);
        panel.add(new JLabel("Radius: "));
        JTextField radius = new JTextField();
        radius.setText(Integer.toString(object.getRadius()));
        panel.add(radius);

        panel.add(new JLabel("Choose circle color: "));
        JColorArea circleColor = new JColorArea(object.getPrimaryColor(),
                false);
        panel.add(circleColor);

        int result = JOptionPane.showConfirmDialog(geometricalObjectJList, panel, "Edit circle",
                JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        int x = 0, y = 0, r = 0;
        try {
            x = Integer.parseInt(centerX.getText());
            y = Integer.parseInt(centerY.getText());
            r = Integer.parseInt(radius.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(geometricalObjectJList, "Invalid parameters", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Color color = circleColor.getCurrentColor();

        object.setForegroundColor(color);
        object.setCenter(new JPoint(x, y));
        object.setRadius(r);

        drawingModel.objectChanged(object);
    }
}
