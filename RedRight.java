package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name= "RedRight", group = "Red")
public class RedRight extends AutoTemplate {

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

        //detects cone tag
        while (!isStarted() && !isStopRequested()) {
            detectAprilTags();
        }  // end of while


        reportAprilTags();

        //drives forward to deliver preloaded cone
        Trajectory initialForwardTrajectory = drive.trajectoryBuilder(new Pose2d())
                .forward(FIRST_FORWARD)
                .build();
        drive.followTrajectory(initialForwardTrajectory);

        //delivers cone
        deliverPreCone();

        //chooses where to park
        if (tagOfInterest == null || tagOfInterest.id == MIDDLE) {
            Trajectory centerTrajectory = drive.trajectoryBuilder(initialForwardTrajectory.end())
                    .forward(CENTER_FORWARD)
                    .build();
            drive.followTrajectory(centerTrajectory);
        }else if (tagOfInterest.id == LEFT) {
            Trajectory leftForwardTrajectory = drive.trajectoryBuilder(initialForwardTrajectory.end())
                    .forward(RIGHT_AND_LEFT_FORWARD)
                    .build();
            drive.followTrajectory(leftForwardTrajectory);

            Trajectory leftStrafeTrajectory = drive.trajectoryBuilder(leftForwardTrajectory.end())
                    .strafeLeft(STRAFE_LEFT)
                    .build();
            drive.followTrajectory(leftStrafeTrajectory);

            Trajectory leftSecondForwardTrajectory = drive.trajectoryBuilder(leftStrafeTrajectory.end())
                    .forward(FINAL_FORWARD)
                    .build();
            drive.followTrajectory(leftSecondForwardTrajectory);
        }else {
            Trajectory rightForwardTrajectory = drive.trajectoryBuilder(initialForwardTrajectory.end())
                    .forward(RIGHT_AND_LEFT_FORWARD)
                    .build();
            drive.followTrajectory(rightForwardTrajectory);

            Trajectory rightStrafeTrajectory = drive.trajectoryBuilder(rightForwardTrajectory.end())
                    .strafeRight(STRAFE_RIGHT)
                    .build();
            drive.followTrajectory(rightStrafeTrajectory);

            Trajectory rightSecondForwardTrajectory = drive.trajectoryBuilder(rightStrafeTrajectory.end())
                    .forward(FINAL_FORWARD)
                    .build();
            drive.followTrajectory(rightSecondForwardTrajectory);
        }

    }

    public void deliverPreCone(){
        //have the cone deliver to the left code here
        sleep(500);
        autoDeliveryLeft.setPosition( DELIVER_LEFT);
        autoDeliveryRight.setPosition( DELIVER_RIGHT);
        sleep(1000);
        autoDeliveryLeft.setPosition( RECOVER_LEFT);
        autoDeliveryRight.setPosition( RECOVER_RIGHT);
        sleep(1000);
    }
}
