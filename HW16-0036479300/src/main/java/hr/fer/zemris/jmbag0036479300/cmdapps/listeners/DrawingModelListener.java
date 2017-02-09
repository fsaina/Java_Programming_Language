package hr.fer.zemris.jmbag0036479300.cmdapps.listeners;


import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;

/**
 * The interface Drawing model listener.
 */
public interface DrawingModelListener {

    /**
     * Objects added.
     *
     * @param source the source
     * @param index0 the index 0
     * @param index1 the index 1
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Objects removed.
     *
     * @param source the source
     * @param index0 the index 0
     * @param index1 the index 1
     */
    public void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * Objects changed.
     *
     * @param source the source
     * @param index0 the index 0
     * @param index1 the index 1
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);
}
