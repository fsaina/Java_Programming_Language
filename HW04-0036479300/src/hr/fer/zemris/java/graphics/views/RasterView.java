package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Interface for the raster object to implement
 */
public interface RasterView {

    /**
     * Method takes the data contained in the raster element
     * and makes a graphical representaiton of the raster on the
     * standard output or other output stream
     * @param raster data container
     * @return Object representing the graphics
     */
    public Object produce(BWRaster raster);

}
