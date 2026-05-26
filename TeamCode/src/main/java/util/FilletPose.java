package util;

public class FilletPose extends Pose {
    private final double radius;
    public FilletPose(Pose basePose, double radius) {
        super(basePose.getX(), basePose.getY(), basePose.getHeading(),
                basePose.getDistanceUnit(), basePose.getAngleUnit(), false);
        Distance.from(basePose.getDistanceUnit(), radius);
        this.radius = radius;
    }
    public double getRadius() {
        return radius;
    }
}