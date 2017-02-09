package hr.fer.zemris.java.graphics.shapes;


/**
 * Circle object implementation of the GeometricShape object
 */
public class Circle extends GeometericShape {

    /**
     * Radius of the circle element
     */
    private int radius;

    /**
     * Def constructor of the Circle object
     *
     * @param x      center X point
     * @param y      center Y point
     * @param radius radius of the circle
     */
    public Circle(int x, int y, int radius) {
        if (radius < 1)
            throw new IllegalArgumentException("Radius must be greater than 0");

        setPointX(x);
        setPointY(y);
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsPoint(int x, int y) {

        if(x < getPointX() - radius || x > getPointX() + radius)
            return false;

        int yOfX = (int) (Math.sqrt( radius*radius - Math.pow( getPointX() - x ,2)));

        if(y >= getPointY() - yOfX && y < getPointY() + yOfX)
            return true;

        return false;
    }

    /**
     * getter for the radius value
     *
     * @return int number representing the radius of the circled
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Setter method for the radius value
     *
     * @param radius int value greater than 0
     * @return Circle object
     */
    public Circle setRadius(int radius) {
        if (radius < 1)
            throw new IllegalArgumentException("Radius must be greater than 0");

        this.radius = radius;
        return this;
    }
}
