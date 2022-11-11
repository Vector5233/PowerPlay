package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoPhase", group = "Auto")
public class AutoPhase extends AutoTemplate {
    public void runOpMode() {
        initialize();
        telemetry.addLine("initialized!");
        telemetry.update();
        waitForStart();
    }
}