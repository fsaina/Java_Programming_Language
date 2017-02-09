package hr.fer.zemris.jmbag0036479300.cmdapps.components;

import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.DrawingModelListener;
import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.DrawingMouseMotionListener;
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
 * DrawingCanvas component that models the canvas area implementation are behaviour
 *
 * @author filipsaina
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 6670871258078976161L;

	/**
     * Reference to the internal model used for manipulating drawn data
     */
    private DrawingModel drawingModel;

    /**
     * Foreground color provider class
     */
    private IColorProvider foreground;

    /**
     * Background provider class
     */
    private IColorProvider background;

    /**
     * DrawingListener for the mouse events on the drawing canvas
     */
    private DrawingMouseMotionListener drawingMouseMotionListener;

    /**
     * Current object in creation in between clicks
     */
    private GeometricalObject geometricalObject;

    /**
     * Instantiates a new J drawing canvas.
     *
     * @param drawingModel the drawing model
     * @param foreground   the foreground
     * @param background   the background
     * @param buttonGroup  the button group
     */
    public JDrawingCanvas(DrawingModel drawingModel, IColorProvider foreground,
                          IColorProvider background, ButtonGroup buttonGroup) {
        this.drawingModel = drawingModel;
        this.foreground = foreground;
        this.background = background;

        drawingModel.addDrawingModelListener(this);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                Object source = e.getSource();

                if (source instanceof JDrawingCanvas) {

                    if (drawingMouseMotionListener != null) {
                        removeMouseMotionListener(drawingMouseMotionListener);
                        drawingMouseMotionListener = null;
                        drawingModel.add(geometricalObject);
                        geometricalObject = null;
                        return;
                    }

                    JPoint p = new JPoint(e.getPoint());
                    ButtonModel button = buttonGroup.getSelection();
                    if (button == null) {
                        return;
                    }
                    String object = button.getActionCommand();

                    if (object.equals("Line")) {
                        geometricalObject = new Line(p, p);

                        drawingMouseMotionListener = new DrawingMouseMotionListener(geometricalObject,
                                JDrawingCanvas.this);
                        addMouseMotionListener(drawingMouseMotionListener);
                    } else if (object.equals("Circle")) {

                        geometricalObject = new Circle(p, 0);

                        drawingMouseMotionListener = new DrawingMouseMotionListener(geometricalObject,
                                JDrawingCanvas.this);
                        addMouseMotionListener(drawingMouseMotionListener);
                    } else if (object.equals("FilledCircle")) {


                        geometricalObject = new FilledCircle(p, 0);
                        drawingMouseMotionListener = new DrawingMouseMotionListener(geometricalObject,
                                JDrawingCanvas.this);
                        addMouseMotionListener(drawingMouseMotionListener);
                    }
                }
            }
        });
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        repaint();
    }


    /**
     * Method used for drawing the canvas element
     *
     * @param g2d        Graphics2D for drawing
     * @param shape      Shape to be drawn
     * @param foreground Color in which to do the drawing
     */
    protected void draw(Graphics2D g2d, Shape shape, Color foreground) {
        g2d.setColor(foreground);
        g2d.fill(shape);
        g2d.setColor(Color.white);
        g2d.draw(shape);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        draw(g2d, new Rectangle((int) getSize().getWidth(), (int) getSize().getHeight()),
                Color.white);


        for (int i = 0; i < drawingModel.getSize(); i++) {

            if (drawingModel.getObject(i) != null) {
                drawingModel.getObject(i).drawObject(g2d,
                        foreground.getCurrentColor(),
                        background.getCurrentColor());
            }
        }
        if (geometricalObject != null) {
            geometricalObject.drawObject(g2d, foreground.getCurrentColor(),
                    background.getCurrentColor());
        }

        g2d.dispose();
    }
}
