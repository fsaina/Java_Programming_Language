package hr.fer.zemris.java.graphics.raster;

/**
 * Interface representing the Black-White raster
 */
public interface BWRaster {

    /**
     * Return the width of the raster frame
     *
     * @return width of raster
     */
    public int getWidth();

    /**
     * Return the height of the raster frame
     *
     * @return height of raster
     */
    public int getHeight();

    /**
     * Clear the raster view, all od the elements shown
     */
    public void clear();

    /**
     * Turn a specific point on raster. Method depends on the flipMode flag. If its true, flip the existing
     * value, turn it on otherwise.
     *
     * @param x horizontal position of the element
     * @param y vertical position of the element
     */
    public void turnOn(int x, int y);

    /**
     * Turn off a specific point on raster.
     *
     * @param x horizonal position of the element
     * @param y vertical position of the lement
     */
    public void turnOff(int x, int y);

    /**
     * Enables the flipping mode. In this mode, when the turnOn method is called, flip the exisitng value.
     */
    public void enableFlipMode();

    /**
     * Disables the flipMode
     */
    public void disableFlipMode();

    /**
     * Method for checking the element at a given position
     *
     * @param x horizontal value
     * @param y vertical value
     * @return true if on, false otherwise
     */
    public boolean isTurnedOn(int x, int y);

}
