package hr.fer.zemris.jmbag0036479300.cmdapps.components;

import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.ColorChangeListener;

import javax.swing.*;
import java.awt.*;

/**
 * Footer text extension that on every ColorProvider change - changes the values set by the user.
 *
 * @author filipsaina
 */
public class JFooterText extends JLabel implements ColorChangeListener {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -5507923105890069254L;

	/**
     * Current foreground selected color
     */
    private Color foregroundColor;

    /**
     * Current background selected color
     */
    private Color backgroundColor;

    /**
     * Default class constructor
     *
     * @param foreground Initial Foreground color
     * @param background Initial Background color
     */
    public JFooterText(JColorArea foreground, JColorArea background) {
        foregroundColor = foreground.getCurrentColor();
        backgroundColor = background.getCurrentColor();

        foreground.addColorChangeListener(this);
        background.addColorChangeListener(this);

        footerText();
    }

    /**
     * Text to be written on the bottom element label on each IColorProvider update
     */
    private void footerText() {

        String footerText = String.format("Foreground color: (%d, %d, %d), " +
                        "background color: (%d, %d, %d).",
                foregroundColor.getRed(),
                foregroundColor.getGreen(),
                foregroundColor.getBlue(),
                backgroundColor.getRed(),
                backgroundColor.getGreen(),
                backgroundColor.getBlue());

        setText(footerText);
    }


    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {

        if (source.isBackground()) {
            backgroundColor = newColor;
        } else {
            foregroundColor = newColor;
        }
        footerText();
    }
}
