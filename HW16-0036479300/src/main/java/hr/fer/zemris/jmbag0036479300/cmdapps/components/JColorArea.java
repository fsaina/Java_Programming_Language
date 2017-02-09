package hr.fer.zemris.jmbag0036479300.cmdapps.components;

import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.ColorChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class JColorArea extends JComponent implements IColorProvider {

    private static final long serialVersionUID = 1L;
    private Color selectedColor;
    private boolean background;
    private List<ColorChangeListener> listeners;

    public JColorArea(Color selectedColor, boolean background) {
        this.selectedColor = selectedColor;
        listeners = new ArrayList<ColorChangeListener>();
        this.background = background;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                Object source = e.getSource();

                if (source instanceof JColorArea) {

                    Color newColor = JColorChooser.showDialog(JColorArea.this,
                            "Choose color", selectedColor);
                    if (newColor == null) {
                        return;
                    }
                    if (!newColor.equals(selectedColor)) {
                        changeColor(newColor);
                    }
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(15, 15);
    }

    @Override
    public Color getCurrentColor() {

        return selectedColor;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        draw(g2d, new Rectangle((int) getPreferredSize().getWidth(), (int) getPreferredSize().getHeight()),
                selectedColor);

        g2d.dispose();
    }

    protected void draw(Graphics2D g2d, Shape shape, Color foreground) {
        g2d.setColor(foreground);
        g2d.fill(shape);
        g2d.setColor(Color.BLACK);
        g2d.draw(shape);
    }

    @Override
    public boolean isBackground() {
        return background;
    }

    public void addColorChangeListener(ColorChangeListener l) {
        listeners.add(l);
    }

    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }

    public void changeColor(Color newColor) {

        for (ColorChangeListener listener : listeners) {
            listener.newColorSelected(this, selectedColor, newColor);
        }
        selectedColor = newColor;
        repaint();
    }
}
