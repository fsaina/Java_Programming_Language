package hr.fer.zemris.jmbag0036479300.cmdapps.components;

import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.DrawingModelListener;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementation of the DrawingModel interface. This class abstracts the number of crated elements on
 * the screen and nofifies required classes about the change
 */
public class DrawingModelImplementation implements DrawingModel {

    /**
     * List of listener
     */
    private List<DrawingModelListener> listeners;

    /**
     * List of GeometricalObject-s currently in use
     */
    private List<GeometricalObject> geometricalObjects;

    public DrawingModelImplementation() {
        listeners = new ArrayList<>();
        geometricalObjects = new ArrayList<>();
    }

    @Override
    public int getSize() {

        return geometricalObjects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {

        if (index < 0 || index >= getSize()) {
            return null;
        }

        return geometricalObjects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {

        geometricalObjects.add(object);

        for (DrawingModelListener listener : listeners) {
            listener.objectsAdded(this, geometricalObjects.size() - 1,
                    geometricalObjects.size() - 1);
        }
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {

        listeners.add(l);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {

        listeners.remove(l);
    }

    @Override
    public void objectChanged(GeometricalObject object) {

        int index = geometricalObjects.indexOf(object);

        for (DrawingModelListener listener : listeners) {
            listener.objectsChanged(this, index, index);
        }
    }

    @Override
    public void remove(GeometricalObject object) {

        int index = geometricalObjects.indexOf(object);
        geometricalObjects.remove(object);

        for (DrawingModelListener listener : listeners) {
            listener.objectsRemoved(this, index, index);
        }
    }
}
