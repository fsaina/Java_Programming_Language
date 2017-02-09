package hr.fer.zemris.jmbag0036479300.cmdapps.shapes;

import java.awt.*;

/**
 * Point object adapter
 */
public class JPoint extends java.awt.Point {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 7172209601536114780L;

	/**
     * Default class constructor
     *
     * @param p Point to be constructed
     */
    public JPoint(Point p) {
        super(p);
    }

    /**
     * Jpoint object constructor from x and y value
     *
     * @param x x position on the raster
     * @param y y position on the raster
     */
    public JPoint(int x, int y) {
        super(x, y);
    }

    /**
     * Gets x
     *
     * @return the
     */
    public int getx() {
        return (int) super.getX();
    }

    /**
     * Gets y
     *
     * @return the
     */
    public int gety() {
        return (int) getY();
    }

}
