package org.firstinspires.ftc.teamcode.VectorCode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="StopAndResetTest", group="testing")
public class StopAndResetTest extends AutoTemplate {
    public void runOpMode() {

        initialize();
        Log.println(Log.INFO, "post-initialization", "arm angle: "+arm.getAngle());
        waitForStart();

        for(int j=0; j<10; j++) {
            Log.println(Log.INFO, "opMode running","arm angle: "+arm.getAngle());
        }
    }
}
