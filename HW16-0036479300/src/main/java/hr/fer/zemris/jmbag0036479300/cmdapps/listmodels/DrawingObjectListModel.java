package hr.fer.zemris.jmbag0036479300.cmdapps.listmodels;

import hr.fer.zemris.jmbag0036479300.cmdapps.JVDraw;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;
import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.DrawingModelListener;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model used for mapping the graphical logic behind the list view containing all elements
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -7281394753003507950L;

	/**
     * JVDraw object reference
     */
    private JVDraw jvDraw;

    /**
     * DrawingModel object reference
     */
    private DrawingModel drawingModel;

    /**
     * Instantiates a new Drawing object list model.
     *
     * @param jvDraw       the jv draw
     * @param drawingModel the drawing model
     */
    public DrawingObjectListModel(JVDraw jvDraw, DrawingModel drawingModel) {

        this.drawingModel = drawingModel;
        this.jvDraw = jvDraw;
        drawingModel.addDrawingModelListener(this);
    }

    @Override
    public int getSize() {

        return drawingModel.getSize();
    }

    @Override
    public GeometricalObject getElementAt(int arg0) {

        return drawingModel.getObject(arg0);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {

        jvDraw.setEditedCanvasFlag(true);
        ListDataListener[] listeners = this.getListDataListeners();

        for (ListDataListener listener : listeners) {

            listener.intervalAdded(new ListDataEvent(this,
                    ListDataEvent.INTERVAL_ADDED, index0, index1));
        }
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {

        jvDraw.setEditedCanvasFlag(true);
        ListDataListener[] listeners = this.getListDataListeners();

        for (ListDataListener listener : listeners) {

            listener.intervalRemoved(new ListDataEvent(this,
                    ListDataEvent.INTERVAL_REMOVED, index0, index1));
        }
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {

        jvDraw.setEditedCanvasFlag(true);
        ListDataListener[] listeners = this.getListDataListeners();

        for (ListDataListener listener : listeners) {

            listener.contentsChanged(new ListDataEvent(this,
                    ListDataEvent.CONTENTS_CHANGED, index0, index1));
        }
    }

}
