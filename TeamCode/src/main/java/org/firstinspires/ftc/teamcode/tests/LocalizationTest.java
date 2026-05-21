package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;

import localizers.Localizer;
import util.Pose;

@TeleOp(name = "Localization Test", group = "Apex Pathing Tests")
public class LocalizationTest extends OpMode {
    private Localizer localizer;

    @Override
    public void init() {
        Constants constants = new Constants();
        localizer = constants.buildOnlyLocalizer(hardwareMap, Pose.zero());
    }

    @Override
    public void loop() {
        localizer.update();
        telemetry.addLine("Position: " + localizer.getPose().toString());
    }
}
