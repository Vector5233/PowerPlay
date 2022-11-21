package org.firstinspires.ftc.teamcode.VectorCode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name= "BlueLeft", group = "Blue")
public class BlueLeft extends AutoTemplate {

    public void runOpMode() {
        initialize();

        telemetry.addLine("initialized!");
        telemetry.update();
        while (!isStarted() && !isStopRequested()) {
            detectAprilTags();
        }  // end of while


        reportAprilTags();

        Pose2d startPose = new Pose2d(10, -8, Math.toRadians(90 ));
        drive.setPoseEstimate(startPose);

        if (tagOfInterest == null || tagOfInterest.id == MIDDLE) {
            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
                    .forward(39)
                    .build();
            drive.followTrajectory(myTrajectory);
        }else if (tagOfInterest.id == LEFT) {
            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
                    .splineTo(new Vector2d(5, -25), Math.toRadians(0))
                    .build();
            drive.followTrajectory(myTrajectory);
            /*Trajectory myTrajectory2 = drive.trajectoryBuilder(new Pose2d())
                    .forward(39)
                    .build();
            drive.followTrajectory(myTrajectory);*/
        }else{
            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
                    .strafeRight(23)
                    .build();
            drive.followTrajectory(myTrajectory);
            Trajectory myTrajectory2 = drive.trajectoryBuilder(new Pose2d())
                    .forward(39)
                    .build();
            drive.followTrajectory(myTrajectory);
        }

    }
}