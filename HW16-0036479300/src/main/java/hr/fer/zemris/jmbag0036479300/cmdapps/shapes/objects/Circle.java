package hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects;

import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;

import java.awt.*;

/**
 * Circle object implementation from the super AbstractCircle
 */
public class Circle extends AbstractCircle {

    /**
     * Foregorund color value
     */
    private Color foreground = null;

    /**
     * Default class constructor
     *
     * @param center JPoint center of the object
     * @param radius int radius used
     */
    public Circle(JPoint center, int radius) {
        setCenter(center);
        setRadius(radius);
    }


    /**
     * String object model
     *
     * @return String object representation
     */
    @Override
    public String toString() {

        String circle = super.toString();
        circle = circle + String.format("%d %d %d",
                getPrimaryColor().getRed(),
                getPrimaryColor().getGreen(),
                getPrimaryColor().getBlue());

        return circle;
    }

    @Override
    public GeometricalObject process(String[] params) {
        if (params.length != 7) {
            return null;
        }

        int centerX, centerY, radius, r, g, b;
        try {
            centerX = Integer.parseInt(params[1]);
            centerY = Integer.parseInt(params[2]);
            radius = Integer.parseInt(params[3]);
            r = Integer.parseInt(params[4]);
            g = Integer.parseInt(params[5]);
            b = Integer.parseInt(params[6]);
        } catch (Exception e) {
            return null;
        }

        Circle circle = new Circle(new JPoint(centerX, centerY), radius);
        try {
            Color c = new Color(r, g, b);
            circle.setForegroundColor(c);
        } catch (Exception e) {
            return null;
        }

        return circle;
    }

    /**
     * Foreground color setter
     * @param color Color object of the circle
     */
    public void setForegroundColor(Color color) {

        foreground = color;
    }

    @Override
    public void drawObject(Graphics2D g2d, Color foreground, Color background) {

        if (this.foreground == null) {
            this.foreground = foreground;
        }

        g2d.setColor(getPrimaryColor());
        g2d.drawOval(getCenter().getx() - getRadius(),
                getCenter().gety() - getRadius(),
                getRadius() * 2, getRadius() * 2);
    }

    @Override
    public Color getPrimaryColor() {

        return foreground;
    }


    @Override
    public GeometricalObject copy() {

        Circle newCirle = new Circle(getCenter(), getRadius());
        newCirle.setForegroundColor(foreground);
        return newCirle;
    }

}
