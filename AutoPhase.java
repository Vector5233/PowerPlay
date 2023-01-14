package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Disabled
@TeleOp(name = "AutoPhase", group = "Auto")
public class AutoPhase extends AutoTemplate {



    public void runOpMode() {
        initialize();

        telemetry.addLine("initialized!");
        telemetry.update();
        while (!isStarted() && !isStopRequested()) {
            detectAprilTags();
        }  // end of while


        reportAprilTags();

        /* Actually do something useful  */
        if(tagOfInterest == null || tagOfInterest.id == LEFT){
            //trajectory
        }else if(tagOfInterest.id == MIDDLE){
            //trajectory
        }else{
            //trajectory
        }


        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
        while (opModeIsActive()) {sleep(20);} //DELETE ONCE WE HAVE AN AUTONOMOUS
    }


}