package hr.fer.zemris.jmbag0036479300.cmdapps.actions;

import hr.fer.zemris.jmbag0036479300.cmdapps.JVDraw;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.DrawingModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The type Save as action.
 */
public class SaveAsAction extends AbstractAction {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 153621627202278873L;

	/**
     * DrawingModel reference
     */
    private DrawingModel drawingModel;

    /**
     * JVDraw object reference
     */
    private JVDraw rootFrame;

    /**
     * Instantiates a new Save as action.
     *
     * @param rootFrame    the rootFrame
     * @param drawingModel the drawing model
     */
    public SaveAsAction(JVDraw rootFrame, DrawingModel drawingModel) {
        this.rootFrame = rootFrame;
        this.drawingModel = drawingModel;

        putValue(Action.NAME, "Save as...");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
        putValue(Action.SHORT_DESCRIPTION, "Save opened file as...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Util.saveAs(rootFrame, drawingModel);
    }

}
