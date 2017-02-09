package hr.fer.zemris.jmbag0036479300.cmdapps.components;

import java.awt.*;

/**
 * Color provider interface modeling the behaviour for the Color provider object that will listen for changes
 */
public interface IColorProvider {

    /**
     * Method returns the current active color the user selected
     *
     * @return Color object representing the color user choose
     */
    public Color getCurrentColor();

    /**
     * Method returns a boolean true value if the current IColorProvider class is user for background color
     *
     * @return true if it is, false if it is for the foreground color
     */
    public boolean isBackground();
}