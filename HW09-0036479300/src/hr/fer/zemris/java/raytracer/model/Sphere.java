package hr.fer.zemris.java.raytracer.model;


/**
 * Sphere object class abstraction, inhereiting all the properties from the GraphicalObject class description
 *
 * @author Filip Saina
 */
public class Sphere extends GraphicalObject {

    /**
     * Sphere center vector component
     */
    private Point3D center;

    /**
     * Radius of the sphere
     */
    private double radius;

    /**
     * Diffuse components values RGB
     */
    private double kdr;
    private double kdg;
    private double kdb;

    /**
     * Reflection compoenet values RGB and N
     */
    private double krr;
    private double krg;
    private double krb;
    private double krn;

    /**
     * Default class constructor
     *
     * @param center sphere center vector
     * @param radius radius of the sphere
     * @param kdr    difuse red component
     * @param kdg    diffuse green component
     * @param kdb    diffuse blue componenet
     * @param krr    reflection red component
     * @param krg    reflection green component
     * @param krb    reflection blue component
     * @param krn    reflection power value
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg,
                  double kdb, double krr, double krg, double krb, double krn) {
        super();
        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {

        Point3D dir = ray.direction;
        Point3D start = ray.start;
        Point3D centerDist = start.sub(center);

        double B = dir.scalarMultiply(2).scalarProduct(centerDist);
        double C = centerDist.scalarProduct(centerDist) - radius * radius;

        //-B +/- sqrt(B^2-4C)/2
        Double d1 = (-1.0 * B + Math.sqrt(B * B - 4.0 * C)) / 2.0;
        Double d2 = (-1.0 * B - Math.sqrt(B * B - 4.0 * C)) / 2.0;

        if (d1.equals(Double.NaN) && d2.equals(Double.NaN)) {
            return null;
        }

        double minimalDistance = 0;

        if (d1 < 0) {

            minimalDistance = d2.doubleValue();

        } else if (d2 < 0) {

            minimalDistance = d1.doubleValue();

        } else {
            minimalDistance = Math.min(d1.doubleValue(),
                    d2.doubleValue());
        }

        Point3D point = start.add(dir.scalarMultiply(minimalDistance));

        return new RayIntersection(point, minimalDistance, true) {

            @Override
            public Point3D getNormal() {
                return center.sub(this.getPoint()).normalize();
            }

            @Override
            public double getKrr() {
                return krr;
            }

            @Override
            public double getKrn() {
                return krn;
            }

            @Override
            public double getKrg() {
                return krg;
            }

            @Override
            public double getKrb() {
                return krb;
            }

            @Override
            public double getKdr() {
                return kdr;
            }

            @Override
            public double getKdg() {
                return kdg;
            }

            @Override
            public double getKdb() {
                return kdb;
            }
        };
    }

}
