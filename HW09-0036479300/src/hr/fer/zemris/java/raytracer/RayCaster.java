package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Ray caster class implementation showcase of the graphicalR properties of the Java language
 *
 * @author Filip Saina
 */
public class RayCaster {


    /**
     * Entry point of the Application
     *
     * @param args input arguments of the application -- all ignored
     */
    public static void main(String[] args) {

        RayTracerViewer.show(new RayTracerProducer(),
                new Point3D(10, 0, 0),
                new Point3D(),
                new Point3D(0, 0, 10), 20, 20);
    }

    /**
     * Producer class for the RayTraceViewer implementation
     */
    public static class RayTracerProducer implements IRayTracerProducer {

        @Override
        public void produce(Point3D eye, Point3D view, Point3D viewUp,
                            double horizontal, double vertical, int width, int height,
                            long requestNo, IRayTracerResultObserver observer) {

            System.out.println("Starting calculations...");

            short[] red = new short[width * height];
            short[] green = new short[width * height];
            short[] blue = new short[width * height];

            Point3D zAxis = eye.negate().add(view).normalize();
            Point3D yAxis = viewUp
                    .normalize()
                    .sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize())))
                    .normalize();
            Point3D xAxis = zAxis.vectorProduct(yAxis).normalize().negate();

            Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
                    .sub(yAxis.scalarMultiply(vertical / 2));

            Scene scene = RayTracerViewer.createPredefinedScene();


            computationalJob(red, green, blue, zAxis, yAxis, xAxis, screenCorner,
                    scene, eye, view, viewUp, horizontal, vertical, width,
                    height, 0, height - 1);


            System.out.println("Calculations done...");

            observer.acceptResult(red, green, blue, requestNo);

            System.out.println("Report done...");

        }


        /**
         * Jos description to be done per thread
         *
         * @param red          array of color values
         * @param green        array of color values
         * @param blue         array of color values
         * @param zAxis        Vector value of the z axis
         * @param yAxis        Vector value of the y axis
         * @param xAxis        Vector value of the x axis
         * @param screenCorner Vector value of the corner of the screen
         * @param scene        Scene object
         * @param eye          Vector value of the eye
         * @param view         Vector value of the view
         * @param viewUp       Vector representing the viewUp
         * @param horizontal   horizontal calcualting paramater
         * @param vertical     vertical calcucaltion paramter
         * @param width        width of the screen
         * @param height       height of the screen
         * @param yMin         calcualtion start y value
         * @param yMax         calcualation end y value
         */
        private static void computationalJob(short[] red, short[] green, short[] blue,
                                             Point3D zAxis, Point3D yAxis, Point3D xAxis, Point3D screenCorner,
                                             Scene scene, Point3D eye, Point3D view, Point3D viewUp,
                                             double horizontal, double vertical, int width, int height,
                                             int yMin, int yMax) {

            short[] rgb = new short[3];
            int offset = yMin * width;

            for (int y = yMin; y <= yMax; y++) {
                for (int x = 0; x < width; x++) {

                    double xOff = ((double) x / width - 1.0) * horizontal;
                    double yOff = ((double) y / height - 1.0) * vertical;

                    Point3D screenPoint = screenCorner.sub(
                            xAxis.scalarMultiply(xOff)).sub(
                            yAxis.scalarMultiply(yOff));

                    Ray ray = Ray.fromPoints(eye, screenPoint);

                    RayIntersection closestIntersection = findClosestIntersection(ray, scene);

                    if (closestIntersection != null) {

                        rgb = determineColor(closestIntersection, scene, eye);

                    } else {

                        rgb[0] = rgb[1] = rgb[2] = 0;
                    }

                    red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                    green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                    blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

                    offset++;
                }
            }
        }


        /**
         * Method determines the color on a given intersection point
         *
         * @param intersection point to be analyed
         * @param scene        scene description
         * @param eye          position of the eye element
         * @return short[] array consistiong of rgb values for the given point
         */
        private static short[] determineColor(RayIntersection intersection,
                                              Scene scene, Point3D eye) {
            short[] rgb = {15, 15, 15};

            for (LightSource source : scene.getLights()) {

                Ray ray = Ray.fromPoints(source.getPoint(), intersection.getPoint());

                RayIntersection closestIntersection = findClosestIntersection(ray, scene);

                double lightVectorLen = intersection.getPoint().sub(source.getPoint()).norm() - 0.1;
                double closestToLightVectorLen = closestIntersection.getPoint().sub(source.getPoint()).norm();

                if (lightVectorLen > closestToLightVectorLen) {
                    continue;
                }

                //call by reference
                diffuseComponent(intersection, source, rgb);
                reflectiveComponent(intersection, source, rgb, eye);
            }

            return rgb;
        }


        /**
         * Method calculates the reflective component part contribution to the final RGB value at a point.
         * Method is called by reference.
         *
         * @param intersection point to be analyzed
         * @param source       LightSource object
         * @param color        short into which the new values will be added
         * @param eye          vector representing the eye position
         */
        private static void reflectiveComponent(RayIntersection intersection, LightSource source, short[] color, Point3D eye) {

            //red, green, blue
            short[] kValues = {(short) intersection.getKrr(),
                    (short) intersection.getKrg(),
                    (short) intersection.getKrb()
            };

            int[] sourceColors = {
                    source.getR(),
                    source.getG(),
                    source.getB()
            };

            Point3D l = intersection.getPoint().sub(source.getPoint()).normalize();
            Point3D n = intersection.getNormal().normalize();
            Point3D v = intersection.getPoint().sub(eye).normalize();

            //reflection
            Point3D r = (l.scalarMultiply(n.norm())).scalarMultiply(n.norm()).scalarMultiply(2).sub(l).normalize();
            r = r.scalarMultiply(-1); //reverse the vector


            for (int i = 0; i < 3; i++) {

                color[i] += (kValues[i] * sourceColors[i] * Math.pow(r.scalarProduct(v), intersection.getKrn()));

            }
        }

        /**
         * Method calculates the diffusive component part contribution to the final RGB value at a point.
         * Method is called by reference.
         *
         * @param intersection point to be analyzed
         * @param source       LightSource object
         * @param color        short into which the new values will be added
         */
        private static void diffuseComponent(RayIntersection intersection, LightSource source, short[] color) {

            //red, green, blue
            short[] kValues = {(short) intersection.getKdr(),
                    (short) intersection.getKdg(),
                    (short) intersection.getKdb()};

            int[] sourceColors = {
                    source.getR(),
                    source.getG(),
                    source.getB()};

            Point3D l = intersection.getPoint().sub(source.getPoint()).normalize();
            Point3D n = intersection.getNormal().normalize();


            for (int i = 0; i < 3; i++) {

                color[i] += (kValues[i] * sourceColors[i] * l.scalarProduct(n));

            }

        }


        /**
         * Method for finding the closest intersectiong RayIntersection object of a given object on scene
         *
         * @param ray   Ray object to be analyzed
         * @param scene Scene object holding the discription of the objects
         * @return Closest found RayIntersection
         */
        private static RayIntersection findClosestIntersection(Ray ray, Scene scene) {
            RayIntersection closest = null;

            for (GraphicalObject object : scene.getObjects()) {

                RayIntersection intersection = object.findClosestRayIntersection(ray);
                if (intersection == null) {
                    continue;
                }
                if (closest == null || intersection.getDistance() < closest.getDistance()) {
                    closest = intersection;
                }
            }
            return closest;
        }
    }
}
