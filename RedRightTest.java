package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBERCLOSETIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBEROPENTIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBERTIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name= "RedRightTest", group = "Red", preselectTeleOp = "AgnesTeleOp")
public class RedRightTest extends AutoTemplate {

    final double FIRST_FORWARD = 14.72;
    final double CENTER_FORWARD = 25;
    final double RIGHT_AND_LEFT_FORWARD = 11;
    final double STRAFE_LEFT = 25.5;
    final double STRAFE_RIGHT = 25;
    final double FINAL_FORWARD = 12;

    public void runOpMode() {
        initialize();

        telemetry.addLine("initialized!");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            detectAprilTags();
        }

        reportAprilTags();

        Trajectory initialForwardTrajectory = drive.trajectoryBuilder(new Pose2d())
                .forward(FIRST_FORWARD)
                .build();
        drive.followTrajectory(initialForwardTrajectory);

        deliverPreCone();

        Trajectory redRightTallPole = drive.trajectoryBuilder(initialForwardTrajectory.end())
                .lineToSplineHeading(new Pose2d(56.000, -9.132, 4.5)) //x coordinate changelog: 61.003 --> 59.000 --> 56.000
                .build();
        drive.followTrajectory(redRightTallPole);

        grabber.setGrabberHandOpen();
        sleep(200);  //change
        arm.setTarget(target);   //find target
        while(arm.isBusy()&opModeIsActive()){
            arm.update();
        }
        arm.setArmWinch(ticks);  //find ticks
        while(armWinch.isBusy()&opModeIsActive()){
            ;
        }


    }

    public void deliverPreCone(){
        sleep(500);
        autoDeliveryLeft.setPosition(DELIVER_LEFT);
        autoDeliveryRight.setPosition(DELIVER_RIGHT);
        sleep(1000);
        autoDeliveryLeft.setPosition( RECOVER_LEFT);
        autoDeliveryRight.setPosition( RECOVER_RIGHT);
        sleep(1000);
    }
    public void grabCone() {
        grabber.setGrabberHandClosed();
        sleep(GRABBERCLOSETIME);
    }
    public void deliverCone() {
        grabber.setGrabberHandOpen();
        sleep(GRABBEROPENTIME);
    }
    public void armToCollect(){
        arm.setTarget(degree);
        while(arm.rotationIsBusy() && opModeIsActive())
    }


}