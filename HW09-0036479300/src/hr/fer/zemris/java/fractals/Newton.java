package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Fractal image visualization class that uses the Newton-Raphson iteration method to calculate on screen
 * points
 */
public class Newton {

    /**
     * Polynomial rooted number value
     */
    private static ComplexRootedPolynomial rooted;

    /**
     * First derivation of the rooted value
     */
    private static ComplexPolynomial derivation;

    /**
     * Polynomial value of the rooted value
     */
    private static ComplexPolynomial polynom;

    /**
     * Threshold of the convergences test
     */
    private static final double CONVERGE_THRESHOLD = 0.001;

    /**
     * Maximum number of iterations to process
     */
    private static final double MAX_ITERATIONS = 16 * 16;

    /**
     * Maximum distance for a root element to be considered near a complex point
     */
    private static final double ROOT_DISTANCE = 0.002;

    /**
     * Entry point of the application
     *
     * @param args list of program arguments provided - all ignored
     */
    public static void main(String[] args) {

        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" +
                "Please enter at least two roots, one root per line. Enter 'done' when done.");


        //perform reading of user input for root values
        Scanner scanner = new Scanner(System.in);
        List<Complex> roots = readUserInput(scanner);

        if (roots.size() < 2) {
            System.err.println("At least two complex root values are required!");
            System.exit(-1);
        }

        rooted = new ComplexRootedPolynomial(roots.toArray(new Complex[roots.size()]));
        polynom = rooted.toComplexPolynom();
        derivation = rooted.toComplexPolynom().derive();

        FractalViewer.show(new Producer());
    }

    /**
     * Method indefinitely reads user input for complex numbers
     *
     * @param scanner Scanner object to read user input
     */
    private static List<Complex> readUserInput(Scanner scanner) {
        List<Complex> roots = new ArrayList<>();
        String line;
        int counter = 0;
        do {
            System.out.print("\nRoot " + counter + " > ");
            line = scanner.nextLine();

            if (line.trim().equals("done"))
                break;

            Complex complexNumber;
            try {

                complexNumber = ComplexUtil.readComplex(line);
            } catch (Exception e) {
                System.out.println("Sorry, incorrect user input provided! Try again");
                continue;
            }

            roots.add(complexNumber);

            counter++;

        } while (true);
        return roots;
    }

    /**
     * Producer class implementing the IFractalProduce interface required by the GUI library to draw elements
     * <p/>
     * Class handles the work organization and delegation of work
     */
    public static class Producer implements IFractalProducer {

        /**
         * List of calculated solutions
         */
        private List<Future<Void>> calculated;

        /**
         * Pool of threads
         */
        private ExecutorService executorService;

        /**
         * Number of processors to be utilized
         */
        private int numberOfAvailableProcessors;

        /**
         * Defaults class constructor
         */
        public Producer() {
            calculated = new ArrayList<>();
            numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();
            executorService = Executors.newFixedThreadPool(numberOfAvailableProcessors,
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread thread = new Thread(r);
                            thread.setDaemon(true);
                            return thread;
                        }
                    });
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer) {

            short[] data = new short[width * height];

            int numberOfFragments = height / (8 * numberOfAvailableProcessors);
            int fragmentSize = height / numberOfFragments;

            //divide jobs
            for (int i = 0; i < numberOfFragments + 1; i++) {

                int startOfFragment = i * fragmentSize;
                int endOfFragment = (i + 1) * fragmentSize;

                if (endOfFragment > height)
                    endOfFragment = height;

                NewtonFractalCalculator newtonFractalCalculator = new NewtonFractalCalculator(
                        data,
                        reMax,
                        reMin,
                        imMax,
                        imMin,
                        width,
                        height,
                        startOfFragment,
                        endOfFragment);

                try {
                    calculated.add(executorService.submit(newtonFractalCalculator));
                } catch (RejectedExecutionException e) {
                    System.out.println("Error executing next iteration");
                    System.exit(-1);
                }

            }

            for (Future<Void> fragment : calculated) {

                try {
                    fragment.get();
                } catch (InterruptedException | ExecutionException ignorable) {
                    //ignore
                }

            }

            executorService.shutdown();

            System.out.println("Zapocinjem izracun...");

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");

            observer.acceptResult(data, (short) (rooted.toComplexPolynom().order() + 1), requestNo);
        }


    }

    /**
     * Class representation of a single work block to be distributed
     */
    private static class NewtonFractalCalculator implements Callable {

        /**
         * Data file reference to store calculations
         */
        private short[] data;

        /**
         * Maximal real number
         */
        private double reMax;

        /**
         * Minimal real number
         */
        private double reMin;

        /**
         * Maximal imaginary number
         */
        private double imMax;

        /**
         * Minimal imaginary number
         */
        private double imMin;

        /**
         * Width of the screen area
         */
        private int width;

        /**
         * Height of the screen area
         */
        private int height;

        /**
         * Start value of the y position from which the calculation is started
         */
        private int yMin;

        /**
         * End value of the y position
         */
        private int yMax;

        /**
         * Default class constructor
         *
         * @param data   data array reference
         * @param reMax  maximal real value
         * @param reMin  minimal real value
         * @param imMax  maximal imaginary value
         * @param imMin  minimal imaginary value
         * @param width  width of the screen
         * @param height height of the screen area
         * @param yMin   start y position
         * @param yMax   end y position
         */
        public NewtonFractalCalculator(short[] data, double reMax, double reMin, double imMax, double imMin, int width, int height, int yMin, int yMax) {
            this.data = data;
            this.reMax = reMax;
            this.reMin = reMin;
            this.imMax = imMax;
            this.imMin = imMin;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
        }

        /**
         * Minimal work unit to be performed by the called method - executing thread. Method calculates the
         * fractal (complex) values for every point inside the provided area.
         *
         * @return null --ignorable
         *
         * @throws Exception on error
         */
        @Override
        public Object call() throws Exception {
            int offset = width * yMin;

            for (int y = yMin; y < yMax; y++) {
                for (int x = 0; x < width; x++) {

                    int iterator = 0;
                    double module;

                    Complex zn = mapToComplexPlain(x, y, width, height, reMin, reMax, imMin, imMax);

                    do {
                        Complex numerator = polynom.apply(zn);
                        Complex denominator = derivation.apply(zn);
                        Complex fraction = numerator.divide(denominator);

                        Complex zn1 = zn.sub(fraction);
                        module = zn1.sub(zn).module();
                        zn = zn1;

                        iterator++;

                    } while (module > CONVERGE_THRESHOLD && iterator < MAX_ITERATIONS);

                    int index = rooted.indexOfClosestRootFor(zn, ROOT_DISTANCE);

                    if (index == -1) {
                        data[offset++] = 0;
                    } else {
                        data[offset++] = (short) index;
                    }

                }
            }

            return null;
        }

        /**
         * Method maps the given parameters to a complex plain e.g. a Complex number inside the plain
         *
         * @param x      x point observed
         * @param y      y point observed
         * @param width  width of the screen
         * @param height height of the screen
         * @param reMin  minimal real value
         * @param reMax  maximal real value
         * @param imMin  minimal imaginary value
         * @param imMax  maximal imaginary value
         * @return Complex number mapping the x, y values inside the plain
         */
        private Complex mapToComplexPlain(int x, int y, int width, int height, double reMin, double reMax, double imMin, double imMax) {

            double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
            double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

            return new Complex(cre, cim);
        }
    }
}
