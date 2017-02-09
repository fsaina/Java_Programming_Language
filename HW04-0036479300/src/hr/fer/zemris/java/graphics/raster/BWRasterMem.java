package hr.fer.zemris.java.graphics.raster;

/**
 * Implementation of the BWRaster interface. Internal memory of elements is stored in a boolean array.
 */
public class BWRasterMem implements BWRaster {

    /**
     * Variable storing the rasterWidth
     */
    private int rasterWidth;

    /**
     * Variable storing the rasterHeight
     */
    private int rasterHeight;

    /**
     * Internal implementation storing the raster state
     */
    private boolean[][] rasterMemory;

    /**
     * Flag showing the mode of the raster element
     */
    private boolean flipMode;

    /**
     * Def. constructor for creating the BWRasterMemory elements
     *
     * @param rasterWidth  width of the raster
     * @param rasterHeight height of the raster
     */
    public BWRasterMem(int rasterWidth, int rasterHeight) {
        if (rasterWidth < 1 || rasterHeight < 1)
            throw new IllegalArgumentException("Given numbers can't be smaller than 1");

        this.rasterWidth = rasterWidth;
        this.rasterHeight = rasterHeight;
        this.rasterMemory = new boolean[rasterHeight][rasterWidth];
        this.flipMode = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return rasterWidth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return rasterHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (int i = 0; i < rasterHeight; i++) {
            for (int j = 0; j < rasterWidth; j++) {
                rasterMemory[i][j] = false;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void turnOn(int x, int y) {
        if (x < 0 || x >= rasterWidth)
            throw new IllegalArgumentException("Trying to turn on a pixel not inside the width of raster");
        if (y < 0 || y >= rasterHeight)
            throw new IllegalArgumentException("Trying to turn on a pixel not inside the height of raster");

        if (!flipMode)
            rasterMemory[y][x] = true;
        else
            rasterMemory[y][x] = !rasterMemory[y][x];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void turnOff(int x, int y) {
        if (x < 0 || x > rasterWidth)
            throw new IllegalArgumentException("Trying to turn off a pixel not inside the width of raster");
        if (y < 0 || y > rasterHeight)
            throw new IllegalArgumentException("Trying to turn off a pixel not inside the height of raster");

        rasterMemory[y][x] = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableFlipMode() {
        this.flipMode = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableFlipMode() {
        this.flipMode = false;
    }

    /**
     * Implementation of the flipMode getter for the later use in the
     * Demo class as it is required to flip current states.
     * @return flipmode
     */
    public boolean isFlipModeEnabled(){
        return flipMode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTurnedOn(int x, int y) {
        if (rasterMemory[y][x])
            return true;
        return false;
    }
}
