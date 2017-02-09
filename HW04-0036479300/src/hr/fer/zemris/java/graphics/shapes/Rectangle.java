package hr.fer.zemris.java.graphics.shapes;


import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Object implementation for the eatangle representation
 */
public class Rectangle extends GeometericShape {

    /**
     * Width of the rectangle object
     */
    private int width;

    /**
     * Height of the rectangle object
     */
    private int height;

    /**
     * Def constructor
     *
     * @param x      initial reference point, X value
     * @param y      initial referecne point, Y vaule
     * @param width  width of the rectangle
     * @param height height of the rectangel
     */
    public Rectangle(int x, int y, int width, int height) {
        if (width < 1 || height < 1)
            throw new IllegalArgumentException("Width and height arguments must be greater than 0");

        setPointX(x);
        setPointY(y);
        this.width = width;
        this.height = height;
    }

    /**
     * Method implementation of the containing point
     *
     * @param x horizontal value
     * @param y vertical value
     * @return true if contains, false otherwise
     */
    @Override
    public boolean containsPoint(int x, int y) {

        if (x >= getPointX() + width || x < getPointX())
            return false;
        else if (y >= getPointY() + height || y < getPointY())
            return false;

        return true;
    }

    /**
     * Method override of the draw method for a better performance implementation
     *
     * @param raster reference to the raster to draw on
     */
    @Override
    public void draw(BWRaster raster) {
        for (int y = getPointY(), height = getHeight(); y < getPointY() + height; y++) {
            for (int x = getPointX(), width = getWidth(); x < getPointX() + width; x++) {
                raster.turnOn(x, y);
            }
        }
    }

    /**
     * Getter width method
     *
     * @return the values representing the wifth of the element
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter width value
     *
     * @param width int number grater than 0
     * @return Rectangle object
     */
    public Rectangle setWidth(int width) {
        if (width < 1)
            throw new IllegalArgumentException("Width must be greater than 0");

        this.width = width;
        return this;
    }

    /**
     * Getter for heigh values
     *
     * @return int number representing the height of the Rectangle
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter value for the height value
     *
     * @param height int number grater than 0
     * @return Rectangle object
     */
    public Rectangle setHeight(int height) {
        if (height < 1)
            throw new IllegalArgumentException("Height must be greater than 0");

        this.height = height;
        return this;
    }
}
