package org.firstinspires.ftc.teamcode.VectorCode;

import android.util.Log;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="GoldenSpotTest", group="robot")
public class GoldenSpotTest extends AutoTemplate {


    @Override
    public void runOpMode() {
        initialize();
        autoDeliveryRight.setPosition(AgnesConstants.RECOVER_RIGHT);
        autoDeliveryLeft.setPosition(AgnesConstants.RECOVER_LEFT);
        grabber.setGrabberHandOpen();
        waitForStart();


        for (int j = 0; j < 5; j = j + 1) {
            armToCollect(j);
            grabCone();
            arm.setArmWinch(520);
            rotateTo(90);
            sleep(2000);
            deliverCone();

            /*armToDeliver();
            rotateTo(118);
            sleep(100);
            arm.setArmWinch(930);
            sleep(500);
            deliverCone();

            deextend();
            rotateTo(90);*/


        }

    }

    public void rotateTo(double degree) {
        arm.setTarget(degree);
        while (arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO, "rotate: ", "angle " + Double.toString(arm.getAngle()));
        }
    }

    public void deextend() {
        int distance = 0;
        arm.setArmWinch(distance);
        while (arm.isWinchBusy() && opModeIsActive()) {
            //Log.println(Log.INFO, "Extension: ", "Inside the loop. ticks " + Double.toString(arm.getArmLength()));
            //Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        }
        //Log.println(Log.INFO, "Extension: ", "Out of the loop. ticks " + Double.toString(arm.getArmLength()));
    }

    public Trajectory turnToPole() {
        Trajectory trajectory = drive.trajectoryBuilder(new Pose2d(), true)
                .splineTo(new Vector2d(-5.45, 1.2), Math.toRadians(147))
                .build();
        drive.followTrajectory(trajectory);
        return trajectory;
    }

    public void turnToCone(Trajectory trajectory) {
        Trajectory coneTrajectory = drive.trajectoryBuilder(trajectory.end(), false)
                .splineTo(new Vector2d(0, 0), Math.toRadians(0))
                .build();
        drive.followTrajectory(coneTrajectory);
    }
}
