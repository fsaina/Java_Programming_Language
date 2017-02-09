package hr.fer.zemris.jmbag0036479300.cmdapps.listeners;


import hr.fer.zemris.jmbag0036479300.cmdapps.components.IColorProvider;

import java.awt.*;

/**
 * The interface Color change listener.
 */
public interface ColorChangeListener {

    /**
     * New color selected event notifier. Listener object must implement this method with actions to do on
     * each nofitication of color change
     *
     * @param source   the source
     * @param oldColor the old color
     * @param newColor the new color
     */
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}