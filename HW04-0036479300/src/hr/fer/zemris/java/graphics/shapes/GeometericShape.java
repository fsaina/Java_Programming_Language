package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;


/**
 * Class abstraction of the GeometricShape class
 */
public abstract class GeometericShape {

    /**
     * reference X point of the object
     */
    private int pointX;

    /**
     * reference Y point of the object
     */
    private int pointY;

    /**
     * Return the X value of the object reference point
     * @return X value
     */
    public int getPointX() {
        return pointX;
    }

    /**
     * Setter method for the X point
     * @param pointX int value for X reference
     */
    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    /**
     * Return the Y value of the object reference point
     * @return Y value
     */
    public int getPointY() {
        return pointY;
    }

    /**
     * Setter method for the Y value
     * @param pointY int value for the Y refernce
     */
    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    /**
     * Def draw method for drawing on the raster.
     * When a better implementation is not providede this one is used.
     * @param raster reference to the raster to draw on
     */
    public void draw(BWRaster raster) {
        for (int y = 0, height = raster.getHeight(); y < height; y++) {
            for (int x = 0, width = raster.getWidth(); x < width; x++) {
                if (containsPoint(x, y))
                    raster.turnOn(x, y);
            }
        }
    }

    /**
     * Mehtod abstraction for the point ownership
     * @param x horizontal value
     * @param y verival value
     * @return true if is contained, false otherwise
     */
    public abstract boolean containsPoint(int x, int y);
}
