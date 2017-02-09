package hr.fer.zemris.java.graphics.shapes;


/**
 * GeomtericShapce implementation fot the Ellipse object
 */
public class Ellipse extends GeometericShape {

    /**
     * Horizontal radius value from the reference point
     */
    private int horizontalRadius;

    /**
     * Vertical radius value from the reference point
     */
    private int verticalRadius;

    /**
     * Default object constructor
     *
     * @param x    referene point X value
     * @param y    reference point Y value
     * @param radX horizontal radius of the ellipse
     * @param radY vertical radius of the ellipse
     */
    public Ellipse(int x, int y, int radX, int radY) {
        if (radX < 1 || radY < 1)
            throw new IllegalArgumentException("Horizonal and vertical radius must be greater than 0");

        setPointX(x);
        setPointY(y);
        this.horizontalRadius = radX;
        this.verticalRadius = radY;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsPoint(int x, int y) {
        if(x < getPointX() - horizontalRadius || x >= getPointX() + horizontalRadius)
            return false;

        double factor = Math.pow( verticalRadius/(double)horizontalRadius ,2);

        int yOfX = (int) ((Math.sqrt( Math.pow(verticalRadius, 2) -
                 factor * Math.pow(x - getPointX(), 2))));

        if(y >= getPointY() - yOfX && y < getPointY() + yOfX)
            return true;

        return false;
    }

    /**
     * Getter for the horizontalRadius
     *
     * @return int representing the radius
     */
    public int getHorizontalRadius() {
        return horizontalRadius;
    }

    /**
     * Setter fo the horizontal radius
     *
     * @param horizontalRadius int value grater than 0
     * @return Ellipse object
     */
    public Ellipse setHorizontalRadius(int horizontalRadius) {
        if (horizontalRadius < 1)
            throw new IllegalArgumentException("Horizontal radius must be greater than 0");
        this.horizontalRadius = horizontalRadius;
        return this;
    }

    /**
     * Getter fot the vertical radius
     *
     * @return int representing the vertical radius
     */
    public int getVerticalRadius() {
        return verticalRadius;
    }

    /**
     * Setter for the vertical radius elemetn
     *
     * @param verticalRadius int number representation of the radius
     * @return Ellipse object
     */
    public Ellipse setVerticalRadius(int verticalRadius) {
        if (verticalRadius < 1)
            throw new IllegalArgumentException("Vertical radius must be greater than 0");
        this.verticalRadius = verticalRadius;
        return this;
    }
}
