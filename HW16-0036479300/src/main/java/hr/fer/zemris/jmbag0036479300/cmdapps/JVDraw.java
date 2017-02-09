package hr.fer.zemris.jmbag0036479300.cmdapps;

import hr.fer.zemris.jmbag0036479300.cmdapps.actions.*;
import hr.fer.zemris.jmbag0036479300.cmdapps.components.*;
import hr.fer.zemris.jmbag0036479300.cmdapps.listeners.ListClickedListener;
import hr.fer.zemris.jmbag0036479300.cmdapps.listmodels.DrawingObjectListModel;
import hr.fer.zemris.jmbag0036479300.cmdapps.shapes.objects.GeometricalObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * JVDraw Graphical application used for vector drawing of various graphical elements and objects. Current
 * implementation supports Line, Circle, FilledCircle objects. Also image export and state open and save are
 * also supported.
 *
 * @author filipsaina
 */
public class JVDraw extends JFrame {

    /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -6230687437644047930L;
	/**
     * The constant toolbar text.
     */
    public static final String TOOLBAR = "Toolbar";
    /**
     * The constant list graphical width.
     */
    public static final int LISTWIDTH = 250;
    /**
     * The constant line text.
     */
    public static final String LINE = "Line";
    /**
     * The constant circle text.
     */
    public static final String CIRCLE = "Circle";
    /**
     * The constant filled circle text.
     */
    public static final String FILLED_CIRCLE = "FilledCircle";
    /**
     * The constant file text.
     */
    public static final String FILE = "File";

    /**
     * The constant APPLICATION_TITLE.
     */
    private static final String APPLICATION_TITLE = "JVDraw";

    /**
     * The constant DEFAULT_FOREGROUND of new objects.
     */
    private static final Color DEFAULT_FOREGROUND = Color.red;

    /**
     * The constant DEFAULT_BACKGROUND of new objects.
     */
    private static final Color DEFAULT_BACKGROUND = Color.BLUE;

    /**
     * flag if the canvas was edited
     */
    private boolean editedCanvasFlag = false;

    /**
     * Drawing canvas object reference
     */
    @SuppressWarnings("unused")
	private JDrawingCanvas canvas;

    /**
     * Foreground Color area reference
     */
    private JColorArea foregroundColor;

    /**
     * Background Color area reference
     */
    private JColorArea backgroundColor;

    /**
     * Drawing model --acts like a canvas controller class with internal representations
     */
    private DrawingModel drawingModel;

    /**
     * Button group reference
     */
    private ButtonGroup buttonGroup;

    /**
     * Instantiates a new Jv draw.
     */
    public JVDraw() {

        setSize(900, 700);
        setLocation(300, 300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle(APPLICATION_TITLE);

        initGUI();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> {
            new JVDraw().setVisible(true);
        });
    }

    /**
     * Method initializes the gui components
     */
    private void initGUI() {

        buttonGroup = setToolbar();
        setFooter();
        drawingModel = setCanvas(buttonGroup);
        setList(drawingModel);
        createMenus(drawingModel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Util.exit(JVDraw.this, drawingModel);
            }
        });
    }

    /**
     * Method initializes the list graphical object
     *
     * @param drawingModel DrawingModel instance
     */
    private void setList(DrawingModel drawingModel) {
        JList<GeometricalObject> list = new JList<GeometricalObject>(
                new DrawingObjectListModel(this, drawingModel));
        JScrollPane listScroll = new JScrollPane(list);

        Dimension d = list.getPreferredSize();
        d.width = LISTWIDTH;

        listScroll.setPreferredSize(d);
        list.addMouseListener(new ListClickedListener(drawingModel, list));
        list.setFocusable(true);

        getContentPane().add(listScroll, BorderLayout.EAST);
    }

    /**
     * Canvas graphical object instantiate
     *
     * @param buttonGroup ButtonGroup instance
     * @return DrawingModel for later use
     */
    private DrawingModel setCanvas(ButtonGroup buttonGroup) {
        DrawingModel drawingModel = new DrawingModelImplementation();
        JDrawingCanvas canvas = new JDrawingCanvas(drawingModel, foregroundColor,
                backgroundColor, buttonGroup);

        getContentPane().add(canvas, BorderLayout.CENTER);
        return drawingModel;
    }

    /**
     * Footer graphical object instantiation
     */
    private void setFooter() {
        JFooterText writeColors = new JFooterText(foregroundColor, backgroundColor);
        getContentPane().add(writeColors, BorderLayout.SOUTH);
    }

    /**
     * Toolbar graphical object instantiation
     *
     * @return ButtonGroup that is made of LINE, CIRCLE, FILLEDCIRCLE elements
     */
    private ButtonGroup setToolbar() {
        JToolBar toolbar = new JToolBar(TOOLBAR);

        JPanel panel = new JPanel(new FlowLayout());
        foregroundColor = new JColorArea(DEFAULT_FOREGROUND, false);
        panel.add(foregroundColor);

        backgroundColor = new JColorArea(DEFAULT_BACKGROUND, true);
        panel.add(backgroundColor);

        ButtonGroup buttonGroup = new ButtonGroup();
        JToggleButton lineButton = new JToggleButton(LINE);
        lineButton.setActionCommand(LINE);
        JToggleButton circleButton = new JToggleButton(CIRCLE);
        circleButton.setActionCommand(CIRCLE);
        JToggleButton filledCircleButton = new JToggleButton(FILLED_CIRCLE);
        filledCircleButton.setActionCommand(FILLED_CIRCLE);
        buttonGroup.add(lineButton);
        buttonGroup.add(circleButton);
        buttonGroup.add(filledCircleButton);

        JPanel shapePanel = new JPanel(new GridLayout(1, 0));
        shapePanel.add(lineButton);
        shapePanel.add(circleButton);
        shapePanel.add(filledCircleButton);

        toolbar.add(panel);
        toolbar.add(shapePanel);
        getContentPane().add(toolbar, BorderLayout.NORTH);
        return buttonGroup;
    }

    /**
     * Menu graphical instantiation
     *
     * @param drawingModel DrawingModel instance of canvas representation
     */
    private void createMenus(DrawingModel drawingModel) {

        OpenAction openAction = new OpenAction(this, drawingModel);
        SaveAsAction saveAsAction = new SaveAsAction(this, drawingModel);
        ExportImageAction exportImageAction = new ExportImageAction(this, drawingModel);
        CloseAplicationAction closeAplicationAction = new CloseAplicationAction(this, drawingModel);

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu(FILE);
        menu.add(fileMenu);
        JMenuItem openMenu = new JMenuItem(openAction);
        fileMenu.add(openMenu);
        JMenuItem saveAsMenu = new JMenuItem(saveAsAction);
        fileMenu.add(saveAsMenu);
        JMenuItem exportMenu = new JMenuItem(exportImageAction);
        fileMenu.add(exportMenu);
        JMenuItem exitMenu = new JMenuItem(closeAplicationAction);
        fileMenu.add(exitMenu);

        this.setJMenuBar(menu);
    }

    /**
     * Gets edited canvas flag.
     *
     * @return the edited canvas flag
     */
    public boolean getEditedCanvasFlag() {
        return editedCanvasFlag;
    }

    /**
     * Sets edited canvas flag.
     *
     * @param e the e
     */
    public void setEditedCanvasFlag(boolean e) {
        editedCanvasFlag = e;
    }

}
