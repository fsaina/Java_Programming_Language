package hr.fer.zemris.java.graphics.shapes;


import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Object implementation of the GeometricShape interface
 */
public class Square extends GeometericShape {

    /**
     * Length between two neighbouring vertices
     */
    private int size;

    /**
     * Default constructor for the object Square
     *
     * @param x    value of the reference point
     * @param y    value of the reference point
     * @param size lenght between neighbouring vertices
     */
    public Square(int x, int y, int size) {
        if (size < 1)
            throw new IllegalArgumentException("Size of a rectangle must be grater than 0");

        setPointX(x);
        setPointY(y);
        this.size = size;
    }

    /**
     * Method override of the draw method for a better performance implementation
     *
     * @param raster reference to the raster to draw on
     */
    @Override
    public void draw(BWRaster raster) {
        for (int y = getPointY(), size = getSize(); y < getPointY() + size; y++) {
            for (int x = getPointX(); x < getPointX() + size; x++) {
                raster.turnOn(x, y);
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsPoint(int x, int y) {
        if (x >= getPointX() + size || x < getPointX())
            return false;
        else if (y >= getPointY() + size || y < getPointY())
            return false;
        return true;
    }

    /**
     * Getter for the size value
     *
     * @return int values of the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter for the size element
     *
     * @param size int value grater than 0
     * @return Square object
     */
    public Square setSize(int size) {
        if (size < 1)
            throw new IllegalArgumentException("Size must be greater than 0");
        this.size = size;
        return this;
    }
}
