package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="MyAuto", group="learning")
public class MyAuto extends LinearOpMode {
    VirtualBot agnes;
    final int TICKS_PER_INCH = 25;  // replace this value with what you calculated.

    public void runOpMode() {
        agnes = new VirtualBot(this);
        agnes.init();
        waitForStart();

        int targetTicks = TICKS_PER_INCH * 12;

        agnes.reset();
        sleep(1);
        agnes.getReady();




    }
}
