package org.firstinspires.ftc.teamcode;

import controllers.PDSController;
import core.ApexConfig;
import drivetrains.BaseDrivetrainConfig;
import drivetrains.Mecanum;
import localizers.BaseLocalizerConfig;
import localizers.Pinpoint;
import core.FollowerConfig;
import geometry.Angle;
import geometry.Dist;
import util.DistUnit;
import util.MotorFactory;

/**
 * This class extends {@link ApexConfig} and provides the specific constants for the drivetrain,
 * localizer, and follower that we want to use in our OpMode. In this example, we are using a
 * mecanum drivetrain, an OTOS localizer, and a point-to-point follower. You can modify the values in
 * the setDrivetrainConstants(), setLocalizerConstants(), and setFollowerConstants() methods to fit
 * your robot's hardware and tuning preferences.
 *
 * @author Dylan B. 18597 RoboClovers - Delta
 */
public class Config extends ApexConfig {
    @Override
    public BaseDrivetrainConfig<Mecanum.Config> drivetrainConfig() { // Any baseDrivetrainConfig child
        return new Mecanum.Config()
                .setFrontLeftMotor(new MotorFactory("frontLeftMotor"))
                .setBackLeftMotor(new MotorFactory("backLeftMotor"))
                .setFrontRightMotor(new MotorFactory("frontRightMotor").reverse())
                .setBackRightMotor(new MotorFactory("backRightMotor").reverse())
                .setRobotCentric(true)
                .setMaxPower(1.0);
    }

    @Override
    public BaseLocalizerConfig<Pinpoint.Config> localizerConfig() { // Any LocalizerConstants
        return new Pinpoint.Config()
                .setName("pinpoint")
                .setOffsets(0, 0, DistUnit.IN)
                .setEncoderDirections(Pinpoint.EncoderDirection.FORWARD, Pinpoint.EncoderDirection.FORWARD)
                .setEncoderResolution(Pinpoint.GoBildaPods.goBILDA_4_BAR_POD);
    }

    @Override
    public FollowerConfig followerConfig() { // Any FollowerConstants
        return new FollowerConfig()
                .setHeadingCoeffs(new PDSController.PDSCoefficients())
                .setLateralCoeffs(new PDSController.PDSCoefficients())
                .setDriveCoeffs(new PDSController.PDSCoefficients())
                .setVelocityCoeffs(new PDSController.PDSCoefficients())
                .setFeedforwardCoeffs(0.0, 0.0)
                .setVelocityLimit(Dist.fromIn(20))
                .setHeadingTolerance(Angle.fromDeg(2.0))
                .setDistanceTolerance(Dist.fromIn(1.0))
                .setTTolerance(0.95)
                .setMaxLateralAccel(10.0);
    }
}

/* Tank drivetrain constants
new TankConstants()
        .setFourMotorDrive(true)
        .setFrontLeftMotorName("leftFront")
        .setBackLeftMotorName("leftRear")
        .setFrontRightMotorName("rightFront")
        .setBackRightMotorName("rightRear")
        .setFrontRightReversed(true)
        .setBackRightReversed(true)
        .setBrakeMode(true)
        .setRobotCentric(true)
        .setMaxPower(0.5);
 */

/* Swerve drivetrain constants
SwerveConstants()
                .setFrontLeftModuleConstants(
                        new SwerveModuleConstants()
                                .setMotorName("frontLeftMotor")
                                .setServoName("flServo")
                                .setEncoderName("flEncoder")
                                .setMotorReversed(false)
                                .setModuleAngleOffset(0) //degrees
                                .setMaxEncoderVoltage(3.3)
                                .setSteering_kP_val(0.1)
                )
                .setFrontRightModuleConstants(
                        new SwerveModuleConstants()
                                .setMotorName("frontRightMotor")
                                .setServoName("frServo")
                                .setEncoderName("frEncoder")
                                .setMotorReversed(true)
                                .setModuleAngleOffset(0) //degrees
                                .setMaxEncoderVoltage(3.3)
                                .setSteering_kP_val(0.1)
                )
                .setBackLeftModuleConstants(
                        new SwerveModuleConstants()
                                .setMotorName("backLeftMotor")
                                .setServoName("blServo")
                                .setEncoderName("blEncoder")
                                .setMotorReversed(false)
                                .setModuleAngleOffset(0) //degrees
                                .setMaxEncoderVoltage(3.3)
                                .setSteering_kP_val(0.1)
                )
                .setBackRightModuleConstants(
                        new SwerveModuleConstants()
                                .setMotorName("backRightMotor")
                                .setServoName("brServo")
                                .setEncoderName("brEncoder")
                                .setMotorReversed(true)
                                .setModuleAngleOffset(0) //degrees
                                .setMaxEncoderVoltage(3.3)
                                .setSteering_kP_val(0.1)
                )
                .setMaxPower(1.0)
                .setTrackWidth(Dist.fromMm(0))
                .setWheelbase(Dist.fromMm(0))
                .setRobotCentric(true);
    }*/

/* Kiwi drivetrain constants
return new KiwiConstants()
                .setFrontRightMotorName("frMotor")
                .setBackMotorName("bMotor")
                .setFrontLeftMotorName("flMotor")
                .setMaxPower(1.0)
                .setRobotCentric(true);
*/

/* OTOS Constants
new OTOSConstants() // Tuned for Dylan + Mikey strafer chassis with OTOS, don't change these
    .setName("otos")
    .setOffset(new Pose(227, -16, 0, Distance.Units.MILLIMETERS, Angle.Units.DEGREES))
    .setLinearScalar(1.05)
    .setHeadingScalar(1.0);
*/