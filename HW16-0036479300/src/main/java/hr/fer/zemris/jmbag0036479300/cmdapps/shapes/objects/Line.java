package hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects;


import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.JPoint;

import java.awt.*;

/**
 * Line object class representation on the raster
 */
public class Line implements GeometricalObject {

    /**
     * Initial point of the line
     */
    private JPoint endJPoint;

    /**
     * End point of the line
     */
    private JPoint startJPoint;

    /**
     * Foreground color of the line
     */
    private Color foregroundColor;

    /**
     * Default class constructor
     *
     * @param startJPoint JPoint start position
     * @param endJPoint   JPoint end position
     */
    public Line(JPoint startJPoint, JPoint endJPoint) {
        this.startJPoint = startJPoint;
        this.endJPoint = endJPoint;
    }


    /**
     * Gets start j point.
     *
     * @return the start j point
     */
    public JPoint getStartJPoint() {
        return startJPoint;
    }


    /**
     * Sets start j point.
     *
     * @param startJPoint the start j point
     * @return the start j point
     */
    public Line setStartJPoint(JPoint startJPoint) {
        this.startJPoint = startJPoint;
        return this;
    }

    /**
     * Gets end j point.
     *
     * @return the end j point
     */
    public JPoint getEndJPoint() {
        return endJPoint;
    }

    /**
     * Sets end j point.
     *
     * @param endJPoint end start j point
     * @return the end j point
     */
    public Line setEndJPoint(JPoint endJPoint) {
        this.endJPoint = endJPoint;
        return this;
    }

    @Override
    public void drawObject(Graphics2D g2d, Color foreground, Color background) {

        if (this.foregroundColor == null) {
            this.foregroundColor = foreground;
        }
        g2d.setColor(getPrimaryColor());
        g2d.drawLine((int) startJPoint.getX(),
                (int) startJPoint.gety(),
                (int) endJPoint.getx(),
                (int) endJPoint.gety());
    }

    @Override
    public void pointUpdate(JPoint p) {
        endJPoint = p;
    }

    @Override
    public String toString() {

        String line = String.format("Line %d %d %d %d %d %d %d",
                getEndJPoint().getx(),
                getEndJPoint().gety(),
                getStartJPoint().getx(),
                getStartJPoint().gety(),
                getPrimaryColor().getRed(),
                getPrimaryColor().getGreen(),
                getPrimaryColor().getBlue());

        return line;
    }

    @Override
    public GeometricalObject process(String[] params) {
        if (params.length != 8) {
            return null;
        }

        int x1, y1, x2, y2, r, g, b;
        try {
            x1 = Integer.parseInt(params[1]);
            y1 = Integer.parseInt(params[2]);
            x2 = Integer.parseInt(params[3]);
            y2 = Integer.parseInt(params[4]);
            r = Integer.parseInt(params[5]);
            g = Integer.parseInt(params[6]);
            b = Integer.parseInt(params[7]);
        } catch (Exception e) {
            return null;
        }

        Line line = new Line(new JPoint(x1, y1), new JPoint(x2, y2));
        try {
            Color c = new Color(r, g, b);
            line.setForegroundColor(c);
        } catch (Exception e) {
            return null;
        }

        return line;
    }

    /**
     * Method sets the initial start point p
     *
     * @param p start point p
     */
    public void setStart(JPoint p) {

        startJPoint = p;
    }

    /**
     * End point p setter
     *
     * @param p JPoint end
     */
    public void setEnd(JPoint p) {
        endJPoint = p;
    }

    /**
     * Foreground color setter
     *
     * @param c Color c of the foreground
     */
    public void setForegroundColor(Color c) {

        foregroundColor = c;
    }

    @Override
    public Color getPrimaryColor() {
        return foregroundColor;
    }

    @Override
    public GeometricalObject copy() {

        Line newLine = new Line(startJPoint, endJPoint);
        newLine.setForegroundColor(foregroundColor);
        return newLine;
    }
}
