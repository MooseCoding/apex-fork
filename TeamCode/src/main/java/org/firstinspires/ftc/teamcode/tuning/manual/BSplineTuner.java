package org.firstinspires.ftc.teamcode.tuning.manual;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;

import drivetrains.Drivetrain;
import followers.BSplineFollower;
import followers.constants.BSplineFollowerConstants;
import localizers.Localizer;
import paths.Path;
import paths.PathBuilder;
import util.Pose;

/**
 * @author Sohum Arora - 22985 Paraducks
 * OpMode for tuning the BSpline follower with Panels.
 * Hold X to execute the test path forward,
 * and hold A to reset and drive back to the start position.
 * Adjust the PDS coefficients and velocity feedforward in Panels.
*/
@Configurable
@TeleOp(name = "BSpline Tuner", group = "Apex Pathing Tuning")
public class BSplineTuner extends OpMode {
    // Note: Make sure drivetrain and localizer actually get assigned inside init()
// via your constants builder, or this will throw a NullPointerException!
    private Drivetrain drivetrain;
    private Localizer localizer;

    private BSplineFollower follower;
    private BSplineFollowerConstants activeConstants;
    private JoinedTelemetry fullTelem;

    private Path currentPath;
    private boolean pathActive = false;

    // Exposed tuning constants for the Translational PDS Controller
    public static double trans_kP;
    public static double trans_kD;
    public static double trans_kS;
    public static double trans_kSDeadzone;

    // Exposed tuning constants for the Heading PDS Controller
    public static double heading_kP;
    public static double heading_kD;
    public static double heading_kS;
    public static double heading_kSDeadzone;

    // Feedforward and tolerances
    public static double velocityFF;
    public static double headingTolerance;
    public static double distanceTolerance;
    public static double tTolerance;

    @Override
    public void init() {
        Constants constants = new Constants();
        fullTelem = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

        follower = (BSplineFollower) constants.build(hardwareMap, Pose.zero());

        // Grab the ACTUAL live constants object from the instantiated follower
        activeConstants = follower.getConstants();

        // Pull initial defaults into the static configurable fields
        trans_kP = activeConstants.translationCoeffs.kP;
        trans_kD = activeConstants.translationCoeffs.kD;
        trans_kS = activeConstants.translationCoeffs.kS;
        trans_kSDeadzone = activeConstants.translationCoeffs.kSDeadzone;

        heading_kP = activeConstants.headingCoeffs.kP;
        heading_kD = activeConstants.headingCoeffs.kD;
        heading_kS = activeConstants.headingCoeffs.kS;
        heading_kSDeadzone = activeConstants.headingCoeffs.kSDeadzone;

        velocityFF = activeConstants.velocityFF;
        headingTolerance = activeConstants.headingTolerance;
        distanceTolerance = activeConstants.distanceTolerance;
        tTolerance = activeConstants.tTolerance;

        fullTelem.addLine(
                "Hold X to run the 48-inch multi-stage B-Spline test path, or hold A to force return home."
        );
        fullTelem.update();
    }

    @Override
    public void loop() {
        if (localizer != null) localizer.update();

        // Mutate the live constants object. Since the PDSControllers hold a reference
        // to these exact Coefficients objects, they will update instantly!
        activeConstants.translationCoeffs.kP = trans_kP;
        activeConstants.translationCoeffs.kD = trans_kD;
        activeConstants.translationCoeffs.kS = trans_kS;
        activeConstants.translationCoeffs.kSDeadzone = trans_kSDeadzone;

        activeConstants.headingCoeffs.kP = heading_kP;
        activeConstants.headingCoeffs.kD = heading_kD;
        activeConstants.headingCoeffs.kS = heading_kS;
        activeConstants.headingCoeffs.kSDeadzone = heading_kSDeadzone;

        activeConstants.velocityFF = velocityFF;
        activeConstants.headingTolerance = headingTolerance;
        activeConstants.distanceTolerance = distanceTolerance;
        activeConstants.tTolerance = tTolerance;

        if (gamepad1.x) {
            if (!pathActive) {
                currentPath = new PathBuilder(localizer != null ? localizer.getPose() : Pose.zero())
                        .holdPose(1.5)
                        .build();
                follower.followPath(currentPath);
                pathActive = true;
            }
            follower.update();
        } else if (gamepad1.a) {
            if (!pathActive) {
                currentPath = new PathBuilder(localizer != null ? localizer.getPose() : Pose.zero())
                        .build();
                follower.followPath(currentPath);
                pathActive = true;
            }
            follower.update();
        } else {
            // Safe fallback sequence clearing operational active paths to protect drive system
            follower.stop();
            if (drivetrain != null) drivetrain.stop();
            pathActive = false;
        }

        // Handle path tracking termination notifications through standard hardware feedback loops
        if (pathActive && !follower.isBusy()) {
            gamepad1.rumble(0.5, 0.5, 100);
            gamepad1.setLedColor(0, 1, 0, 300);
            pathActive = false;
        } else if (pathActive) {
            gamepad1.setLedColor(1, 0, 0, 100);
        }

        if (localizer != null) {
            fullTelem.addData("Current X", localizer.getPose().getX());
            fullTelem.addData("Current Y", localizer.getPose().getY());
            fullTelem.addData("Robot Heading", Math.toDegrees(localizer.getPose().getHeading()));
        }
        fullTelem.addLine(follower.isBusy() ? "Follower IS busy" : "Follower is NOT busy");
        fullTelem.update();
    }
}