package hr.fer.zemris.jmbag0036479300.cmdapps.components;

import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.DrawingModelListener;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;

/**
 * Drawing model interface implementation
 */
public interface DrawingModel {

    /**
     * Method returns the number of geometrical objects drawn -registered
     *
     * @return int size
     */
    public int getSize();

    /**
     * Method returns the object at a given index
     *
     * @param index index of the object to be returned
     * @return GeometricalObject object
     */
    public GeometricalObject getObject(int index);

    /**
     * Method adds a GeometricalObject to the internal list of objects and notifies the listeners
     *
     * @param object
     */
    public void add(GeometricalObject object);

    /**
     * Method registers a new drawingmodellistener to the object
     *
     * @param l DrawingModelListener to be registered
     */
    public void addDrawingModelListener(DrawingModelListener l);


    /**
     * Method removes the given DrawingModelListener object from the Drawing notifiy list
     *
     * @param l DrawingModelListener object to be removed from the list
     */
    public void removeDrawingModelListener(DrawingModelListener l);


    /**
     * Method notifies all register llistener about the object-s change in parametres
     *
     * @param object
     */
    public void objectChanged(GeometricalObject object);

    /**
     * Method removes the GeometricalObject provided from the internal list of objects and notifies the
     * listeners
     *
     * @param object
     */
    public void remove(GeometricalObject object);
}
