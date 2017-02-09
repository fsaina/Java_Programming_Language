package hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects;

import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;

import java.awt.*;

/**
 * Filled Circle class implementation
 */
public class FilledCircle extends Circle {

    /**
     * Background color
     */
    private Color backgroundColor = null;

    /**
     * Default class constructor
     *
     * @param center JPoint center of the object
     * @param radius int radius used
     */
    public FilledCircle(JPoint center, int radius) {
        super(center, radius);
    }

    @Override
    public void drawObject(Graphics2D g2d, Color foreground, Color background) {

        if (getPrimaryColor() == null) {
            setForegroundColor(foreground);
        }
        if (getBackgroundColor() == null) {
            setBackgroundColor(background);
        }

        g2d.setColor(getPrimaryColor());
        g2d.drawOval(getCenter().getx() - getRadius(),
                getCenter().gety() - getRadius(),
                getRadius() * 2, getRadius() * 2);
        g2d.setColor(getBackgroundColor());
        g2d.fillOval(getCenter().getx() - getRadius(),
                getCenter().gety() - getRadius(),
                getRadius() * 2, getRadius() * 2);

    }

    @Override
    public String toString() {

        String cirecleText = super.toString();
        cirecleText = cirecleText + String.format(" %d %d %d",
                getBackgroundColor().getRed(),
                getBackgroundColor().getGreen(),
                getBackgroundColor().getBlue());
        cirecleText = cirecleText.replace("Circle", "FCircle");

        return cirecleText;
    }

    @Override
    public GeometricalObject process(String[] params) {
        if (params.length != 10) {
            return null;
        }

        int centerX, centerY, radius, r1, g1, b1, r2, g2, b2;
        try {
            centerX = Integer.parseInt(params[1]);
            centerY = Integer.parseInt(params[2]);
            radius = Integer.parseInt(params[3]);
            r1 = Integer.parseInt(params[4]);
            g1 = Integer.parseInt(params[5]);
            b1 = Integer.parseInt(params[6]);
            r2 = Integer.parseInt(params[7]);
            g2 = Integer.parseInt(params[8]);
            b2 = Integer.parseInt(params[9]);
        } catch (Exception e) {
            return null;
        }

        FilledCircle fcircle = new FilledCircle(new JPoint(centerX, centerY), radius);
        try {
            Color fc = new Color(r1, g1, b1);
            Color bc = new Color(r2, g2, b2);
            fcircle.setForegroundColor(fc);
            fcircle.setBackgroundColor(bc);
        } catch (Exception e) {
            return null;
        }

        return fcircle;
    }

    /**
     * Background color setter
     *
     * @param c Color of the new background
     */
    public void setBackgroundColor(Color c) {
        backgroundColor = c;
    }

    /**
     * Background color getter
     *
     * @return Background color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }


    @Override
    public GeometricalObject copy() {
        FilledCircle newCirle = new FilledCircle(getCenter(), getRadius());
        newCirle.setBackgroundColor(getBackgroundColor());
        newCirle.setForegroundColor(getPrimaryColor());
        return newCirle;
    }
}
