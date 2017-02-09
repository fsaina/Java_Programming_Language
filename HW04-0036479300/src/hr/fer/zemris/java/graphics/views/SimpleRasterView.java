package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * RasterView interface implementation that provides basic Viewer functionality. outputs on std.out
 */
public class SimpleRasterView implements RasterView {

    private final static char DEFAULT_ON_CHAR = '*';
    private final static char DEFAULT_OFF_CHAR = '.';

    /**
     * Symbol representing turned on element
     */
    private char turnOnSymbol;

    /**
     * Symobl represnting turned off element
     */
    private char turnOffSymbol;

    /**
     * Def constructor assumes '*' and '.' as representing elements
     */
    public SimpleRasterView() {
        this(DEFAULT_ON_CHAR, DEFAULT_OFF_CHAR);
    }

    /**
     * Constructor method
     *
     * @param turnOnSymbol  view turnedOn element representation
     * @param turnOffSymbol view turnedOff element representation
     */
    public SimpleRasterView(char turnOnSymbol, char turnOffSymbol) {
        if (turnOnSymbol == turnOffSymbol)
            throw new IllegalArgumentException("Both states cant be represented with the same character");
        this.turnOnSymbol = turnOnSymbol;
        this.turnOffSymbol = turnOffSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object produce(BWRaster raster) {
        StringRasterView stringRasterView = new StringRasterView(turnOnSymbol, turnOffSymbol);

        System.out.println(stringRasterView.produce(raster));

        return null;
    }
}
