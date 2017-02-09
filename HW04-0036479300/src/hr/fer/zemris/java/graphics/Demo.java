package hr.fer.zemris.java.graphics;

import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.*;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Demo showcase class implementation for the graphics library
 */
public class Demo {

    /**
     * Entry point of the application
     *
     * @param args command line arguments, must be either 1 or 2 of them
     */
    public static void main(String[] args) {
        if (args.length < 1 && args.length > 2)
            throw new IllegalArgumentException("One or two arguments required as input");

        int width = 0;
        int height = 0;

        if (args.length == 1) {
            try {
                width = height = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Illegal format of the number given");
                System.exit(-1);
            }
        } else {
            try {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Illegal format of the numbers given");
                System.exit(-1);
            }
        }

        GeometericShape[] shapes = readUserInput();

        //draw on raster, with a simpleView
        BWRasterMem raster = new BWRasterMem(width, height);
        SimpleRasterView rasterView = new SimpleRasterView();

        for (GeometericShape shape : shapes) {

            //FLIP element
            if (shape == null) {
                if (raster.isFlipModeEnabled())
                    raster.disableFlipMode();
                else
                    raster.enableFlipMode();
            continue;
            }

            shape.draw(raster);
        }

        rasterView.produce(raster);
    }

    /*
     * Method for reading all of the user input for later drawing
     */
    private static GeometericShape[] readUserInput() {
        Scanner input = new Scanner(System.in);

        int n = readNumberOfShapes(input);
        int size = n;
        GeometericShape[] shapes = new GeometericShape[n];

        while (n != 0) {

            String line = input.next();
            //line = input.nextLine();

            if (line.startsWith("FLIP")) {

                shapes[size - n] = null;

            } else if (line.startsWith("RECTANGLE")) {
                readRectangleParameters(n, size, shapes, input);

            } else if (line.startsWith("SQUARE")) {

                readSquareParameters(n, size, shapes, input);

            } else if (line.startsWith("ELLIPSE")) {

                readEllipseParameters(n, size, shapes, input);

            } else if (line.startsWith("CIRCLE")) {
                readerCircleParams(n, size, shapes, input);
            } else {
                System.err.println("Shape name not recognized");
                System.exit(-1);
            }

            n--;
        }

        return shapes;
    }

    private static void readerCircleParams(int n, int size, GeometericShape[] shapes, Scanner line) {
        int[] params = readerParams(line);

        if (params.length != 3) {
            System.err.println("Illegal number of parameters provided in circle declaration");
            System.exit(-1);
        }

        try {

            shapes[size - n] = new Circle(params[0], params[1], params[2]);

        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument given in circle declaration");
            System.exit(-1);
        }

    }


    /*
     * Read Ellipse input parameters for the user.
     */
    private static void readEllipseParameters(int n, int size, GeometericShape[] shapes, Scanner line) {

        int[] params = readerParams(line);

        if (params.length != 4) {
            System.err.println("Illegal number of parameters provided in ellipse declaration");
            System.exit(-1);
        }

        try {

            shapes[size - n] = new Ellipse(params[0], params[1], params[2], params[3]);

        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument given in ellipse declaration");
            System.exit(-1);
        }

    }

    /*
    * Read Square input parameters for the user.
    */
    private static void readSquareParameters(int n, int size, GeometericShape[] shapes, Scanner line) {

        int[] params = readerParams(line);

        if (params.length != 3) {
            System.err.println("Illegal number of parameters provided in square declaration");
            System.exit(-1);
        }

        try {

            shapes[size - n] = new Square(params[0], params[1], params[2]);

        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument given in square declaration");
            System.exit(-1);
        }

    }

    /*
    * Read Rectangle input parameters for the user.
    */
    private static void readRectangleParameters(int n, int size, GeometericShape[] shapes, Scanner line) {
        int[] params = readerParams(line);

        if (params.length != 4) {
            System.err.println("Illegal number of parameters provided in rectangle declaration");
            System.exit(-1);
        }

        try {

            shapes[size - n] = new Rectangle(params[0], params[1], params[2], params[3]);

        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument given in rectangle declaration");
            System.exit(-1);
        }

    }

    /*
    * Read number of Shapes to be drawn from the user
    */
    private static int readNumberOfShapes(Scanner input) {
        int n = 0;
        try {
            n = input.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Illegal information given for number of elements");
            System.exit(-1);
        }
        return n;
    }

    /*
    Method for reading input parameters from the input stream
     */
    private static int[] readerParams(Scanner scannerInput) {

        String parameterLine = scannerInput.nextLine();
        Scanner scanner = new Scanner(parameterLine);

        int size = parameterLine.trim().split(" ").length;  //number of elements
        int n = size;
        int[] elements = new int[size];

        while (scanner.hasNextInt()) {
            try {
                elements[size - n] = scanner.nextInt();
                n--;
            } catch (InputMismatchException e) {
                System.err.println("Illegal data format given to square declaration");
                System.exit(-1);
            }
        }
        
        scanner.close();

        return elements;
    }


}
