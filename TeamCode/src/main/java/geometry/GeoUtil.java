package geometry;

public class GeoUtil {
    /**
     * Calculates the shortest signed angular difference between two angles in radians.
     * Result is always in the range [-PI, PI].
     */
    public static double getShortestAngularDifference(Angle from, Angle to) {
        double diff = to.getRad() - from.getRad();

        // Wrap the difference into the [-PI, PI] range
        diff = (diff + Math.PI) % (2 * Math.PI) - Math.PI;
        if (diff < -Math.PI) {
            diff += 2 * Math.PI;
        }
        return diff;
    }
}
