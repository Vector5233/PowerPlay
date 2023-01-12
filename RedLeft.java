package org.firstinspires.ftc.teamcode.VectorCode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name= "RedLeft", group = "Red", preselectTeleOp = "AgnesTeleOp")
public class RedLeft extends AutoTemplate {

    final double FIRST_FORWARD = 16;
    final double CENTER_FORWARD = 25;
    final double RIGHT_AND_LEFT_FORWARD = 11;
    final double STRAFE_LEFT = 25.5;
    final double STRAFE_RIGHT = 25;
    final double FINAL_FORWARD = 12;
    double redLeftTallPoleX = 54.300;
    double redLeftTallPoleY = 10.432;
    double redLeftTallPoleHeading = 1.83; //radians
    double middleParkingSpotX = 48;
    double middleParkingSpotY = 0;
    double middleParkingSpotHeading = 0;
    double leftParkingSpotX = 48;
    double leftParkingSpotY = 29;
    double leftParkingHeading = Math.toRadians(90);
    double rightParkingHeading = Math.toRadians(90);
    double rightParkingSpotX = 48;
    double rightParkingSpotY = 0;
    double parkRightBack = 22;
    final Pose2d SECOND_FORWARD = new Pose2d(36.5,1.7,0);
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
        Trajectory secondForwardTrajectory = drive.trajectoryBuilder(initialForwardTrajectory.end())
                .lineToSplineHeading(SECOND_FORWARD)
                .build();
        drive.followTrajectory(secondForwardTrajectory);

        redLeftTallPole = drive.trajectoryBuilder(secondForwardTrajectory.end())
                .lineToSplineHeading(new Pose2d(redLeftTallPoleX,  redLeftTallPoleY, redLeftTallPoleHeading)) //x coordinate changelog: 61.003 --> 59.000 --> 56.000
                .build();
        drive.followTrajectory(redLeftTallPole);
        grabber.setGrabberHandOpen();

        //grabber.setGrabberHandOpen();

        /* & for(int cone = 0; cone <= 4; cone++) {
            armToCollect(cone);
            grabCone();
            armToDeliver();
            deliverCone();

        }*/
        //armToVertical();
        if(tagOfInterest == null || tagOfInterest.id == MIDDLE) {       //ALL OF THIS WILL NEED TESTING
            parkMiddle();

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







}
    public void parkLeft() {
        Trajectory parkLeftTrajectory = drive.trajectoryBuilder((redLeftTallPole.end()))
                .lineToSplineHeading(new Pose2d (leftParkingSpotX, leftParkingSpotY, leftParkingHeading))
            .build();
        drive.followTrajectory(parkLeftTrajectory);



    }
    public void parkRight() {
        Trajectory parkRightTrajectory = drive.trajectoryBuilder(redLeftTallPole.end())
                .lineToSplineHeading(new Pose2d(rightParkingSpotX, rightParkingSpotY, rightParkingHeading))
                .build();
        drive.followTrajectory(parkRightTrajectory);
        Trajectory backRightTrajectory = drive.trajectoryBuilder(parkRightTrajectory.end())
                .back(parkRightBack)
                .build();
        drive.followTrajectory((backRightTrajectory));
    }
    public void parkMiddle() {
        Trajectory parkMidTrajectory = drive.trajectoryBuilder((redLeftTallPole.end()))
                .lineToSplineHeading(new Pose2d(middleParkingSpotX, middleParkingSpotY, middleParkingSpotHeading))
                .build();
        drive.followTrajectory(parkMidTrajectory);


        /*Trajectory parkMidTrajectory = drive.trajectoryBuilder(redRightTallPole.end())
                .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
                .build();
        drive.followTrajectory(parkMidTrajectory);*/

    }


    public void grabberToVertical(){
        grabber.setGrabberHandOpen();
    }
    /*

    public void armToVertical(){
        arm.setTarget(90);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }
     */

}