package hr.fer.zemris.jmbag0036479300.cmdapps.actions;

import hr.fer.zemris.jmbag0036479300.cmdapps.JVDraw;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class implementation of the close action in the application
 */
public class CloseAplicationAction extends AbstractAction {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 2355596922698396941L;

	/**
     * DrawingModel reference
     */
    private DrawingModel drawingModel;

    /**
     * Root frame reference
     */
    private JVDraw rootFrame;

    /**
     * Instantiates a new Close application action.
     *
     * @param rootFrame    the root frame
     * @param drawingModel the drawing model
     */
    public CloseAplicationAction(JVDraw rootFrame, DrawingModel drawingModel) {
        this.rootFrame = rootFrame;
        this.drawingModel = drawingModel;

        putValue(Action.NAME, "Exit");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
        putValue(Action.SHORT_DESCRIPTION, "Close application");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Util.exit(rootFrame, drawingModel);
    }
}
