package hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects;

import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;

import java.awt.*;

/**
 * Geometrical object interface representation
 */
public interface GeometricalObject {
    /**
     * Method used for specifying the drawing parameters for any object
     *
     * @param g2d        Graphics2D drawing mechanism
     * @param foreground Foreground color
     * @param background Background color
     */
    void drawObject(Graphics2D g2d, Color foreground, Color background);

    /**
     * Point update class for the update of point parameter. Interpretation depends on the object
     * implementation
     *
     * @param p JPoint object new
     */
    void pointUpdate(JPoint p);

    /**
     * Primary color getter --every object needs to have a primary color
     *
     * @return Color object
     */
    Color getPrimaryColor();

    /**
     * Copy class for the given object
     *
     * @return GeometricalObject duplicate
     */
    GeometricalObject copy();


    /**
     * Method used for reading the file parameters and giving it to a object(used for open/save)
     * @param params File read parameters
     * @return GeometricalObject created
     */
    GeometricalObject process(String[] params);
}
