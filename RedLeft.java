package org.firstinspires.ftc.teamcode.VectorCode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name= "RedLeft", group = "Red", preselectTeleOp = "AgnesTeleOp")
public class RedLeft extends AutoTemplate {

    final double FIRST_FORWARD = 14.72;
    final double CENTER_FORWARD = 25;
    final double RIGHT_AND_LEFT_FORWARD = 11;
    final double STRAFE_LEFT = 25.5;
    final double STRAFE_RIGHT = 25;
    final double FINAL_FORWARD = 12;
    double redLeftTallPoleX = 56.000;
    double redLeftTallPoleY = 9.132;
    double redLeftTallPoleHeading = 4.5; //radians
    double middleParkingSpotX = 26;
    double middleParkingSpotY = 0;
    double middleParkingSpotHeading = 0;
    Trajectory redLeftTallPole;

    public void runOpMode(){

        initialize();


        telemetry.addLine("initialized!");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            detectAprilTags();
        }

        reportAprilTags();
        //brings grabber hands to vertical
        grabberToVertical();

        Trajectory initialForwardTrajectory = drive.trajectoryBuilder(new Pose2d())
                .forward(FIRST_FORWARD)
                .build();
        drive.followTrajectory(initialForwardTrajectory);

        deliverPreCone();

        redLeftTallPole = drive.trajectoryBuilder(initialForwardTrajectory.end())
                .lineToSplineHeading(new Pose2d(redLeftTallPoleX,  redLeftTallPoleY, redLeftTallPoleHeading)) //x coordinate changelog: 61.003 --> 59.000 --> 56.000
                .build();
        drive.followTrajectory(redLeftTallPole);
        grabber.setGrabberHandOpen();

        for(int cone = 0; cone <= 4; cone++) {
            armToCollect(cone);
            grabCone();
            armToDeliver();
            deliverCone();
        }
        armToVertical();
        if(tagOfInterest == null || tagOfInterest.id == MIDDLE) {       //ALL OF THIS WILL NEED TESTING
            parkMiddle();

            /* Trajectory parkMiddle = drive.trajectoryBuilder(redRightTallPole.end())
                    .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
                    .build();
            drive.followTrajectory(parkMiddle);*/
        }
        else if (tagOfInterest.id == LEFT) {
            parkLeft();


            /*Trajectory parkLeft = drive.trajectoryBuilder(redRightTallPole.end())
                    .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
                    .strafeLeft(23.5)
                    .build();
            drive.followTrajectory(parkLeft);*/

        }
        else {
            parkRight();


           /* Trajectory parkRight = drive.trajectoryBuilder(redRightTallPole.end())
                    .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
                    .strafeRight(23.5)
                    .build();
            drive.followTrajectory(parkRight);*/

        }

        //keep at the very very very end of loop
        armToVertical();




}
    public void parkLeft()
    {Trajectory parkLeft = drive.trajectoryBuilder(redLeftTallPole.end())
            .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
            .strafeLeft(23.5)
            .build();
        drive.followTrajectory(parkLeft);


    }
    public void parkRight() {
        Trajectory parkRight = drive.trajectoryBuilder(redLeftTallPole.end())
                .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
                .strafeRight(23.5)
                .build();
        drive.followTrajectory(parkRight);

    }
    public void parkMiddle() {
        Trajectory parkMid = drive.trajectoryBuilder(redLeftTallPole.end())
                .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
                .build();
        drive.followTrajectory(parkMid);

    }




}
