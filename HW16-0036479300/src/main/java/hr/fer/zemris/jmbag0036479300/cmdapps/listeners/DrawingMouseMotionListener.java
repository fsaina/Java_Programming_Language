package hr.fer.zemris.jmbag0036479300.cmdapps.listeners;


import hr.fer.zemris.jmbag0036479300.cmdapps.components.JDrawingCanvas;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * The type Drawing mouse motion jDrawingCanvas.
 */
public class DrawingMouseMotionListener implements MouseMotionListener {

    /**
     * Geometrical object reference
     */
    private GeometricalObject geometricalObject;

    /**
     * Drawing canvas reference on which the geom.object whill be drawn
     */
    private JDrawingCanvas jDrawingCanvas;

    /**
     * Instantiates a new Drawing mouse motion jDrawingCanvas.
     *
     * @param geometricalObject the geometrical object
     * @param jDrawingCanvas    the jDrawingCanvas
     */
    public DrawingMouseMotionListener(GeometricalObject geometricalObject,
                                      JDrawingCanvas jDrawingCanvas) {

        this.geometricalObject = geometricalObject;
        this.jDrawingCanvas = jDrawingCanvas;
    }

    /**
     * Gets geometrical object.
     *
     * @return the geometrical object
     */
    public GeometricalObject getGeometricalObject() {
        return geometricalObject;
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        //IGNORE
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {

        JPoint p = new JPoint(arg0.getPoint());
        geometricalObject.pointUpdate(p);
        jDrawingCanvas.repaint();
    }
}
