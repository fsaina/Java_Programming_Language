package hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects;


import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;

/**
 * Circle class abstraction containing only the center point and the radius
 */
public abstract class AbstractCircle implements GeometricalObject {

    /**
     * Center point
     */
    private JPoint center;

    /**
     * Radius of the circle
     */
    private int radius;

    /**
     * Update the circle to a new given point object class
     *
     * @param point JPoint new "end" point of the class
     */
    @Override
    public void pointUpdate(JPoint point) {

        radius = (int) Math.sqrt(
                (point.x - center.getx()) * (point.x - center.getx())
                        + (point.y - center.gety()) * (point.y - center.gety())
        );
    }

    /**
     * To string object cast
     *
     * @return String representing the object
     */
    @Override
    public String toString() {

        String circle = String.format("Circle %d %d %d ",
                getCenter().getx(),
                getCenter().gety(),
                getRadius());

        return circle;
    }

    /**
     * Center point getter
     *
     * @return JPoint of the center
     */
    public JPoint getCenter() {
        return center;
    }

    /**
     * Center point setter
     *
     * @param center JPoint object
     * @return this object
     */
    public AbstractCircle setCenter(JPoint center) {
        this.center = center;
        return this;
    }

    /**
     * Radius setter object
     *
     * @param r radius of the object
     */
    public void setRadius(int r) {
        radius = r;
    }

    /**
     * Radius object getter
     *
     * @return radius size
     */
    public int getRadius() {
        return radius;
    }

}
