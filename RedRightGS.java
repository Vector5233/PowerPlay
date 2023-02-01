package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.ARMEXTENSION;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.CONEDEGREE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBERCLOSETIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBEROPENTIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name= "RedRightGS", group = "Red", preselectTeleOp = "AgnesTeleOp")
public class RedRightGS extends AutoTemplate {

    final double FIRST_FORWARD = 16;
    final double CENTER_FORWARD = 25;
    final double RIGHT_AND_LEFT_FORWARD = 11;
    final double STRAFE_LEFT = 25.5;
    final double STRAFE_RIGHT = 25;
    final double FINAL_FORWARD = 12;
    double redRightTallPoleX = 56.000;
    double redRightTallPoleY = -9.132;
    double redRightTallPoleHeading = 4.5; //radians
    double middleParkingSpotX = 46;
    double middleParkingSpotY = 0;
    double middleParkingSpotHeading = -90;
    double rightParkingX = 50;
    double rightParkingY = -28;
    double rightSideRightParkingHeading = Math.toRadians(90); //if its redleft or blueleft use Math.toRadians(270)
    double goldenSpotX = 40.0;
    double goldenSpotY = -4.0;                   //these are rough measurements and need to tested
    double goldenSpotHeading = -80.0; //degrees
    boolean backwards = true;
    final Pose2d GOLDEN_SPOT = new Pose2d(goldenSpotX, goldenSpotY, Math.toRadians(goldenSpotHeading));
    final Pose2d SECOND_FORWARD = new Pose2d(36.5,-1.5, 0);
    Trajectory redRightTallPole;
    Trajectory goldenSpotTrajectory;

    public void runOpMode() {
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

        goldenSpotTrajectory = drive.trajectoryBuilder(initialForwardTrajectory.end())//if this doesn't work we can try to simply move forward and turn
                .lineToSplineHeading(GOLDEN_SPOT)
                .build();
        drive.followTrajectory(goldenSpotTrajectory);

        /*redRightTallPole = drive.trajectoryBuilder(goldenSpotTrajectory.end())
                .lineToSplineHeading(new Pose2d(goldenSpotX,  goldenSpotY, goldenSpotHeading)) //x coordinate changelog: 61.003 --> 59.000 --> 56.000
                .build();
        drive.followTrajectory(redRightTallPole);*/

        //grabber.setGrabberHandOpen();

        /* & for(int cone = 0; cone <= 4; cone++) { //need to find arm angle and extension for collection and delivery
            armToCollect(cone);
            grabCone();
            armToDeliver();
            deliverCone();

        }*/
        armToVertical();
        if(tagOfInterest == null || tagOfInterest.id == MIDDLE) {
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
    public void armToCollect(int cone){
        arm.setArmWinch(ARMEXTENSION);
        while(arm.isWinchBusy() && opModeIsActive()) {
        }
        double degree = (CONEDEGREE[cone]);
        arm.setTarget(degree);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }
    /*public void armToDeliver() {    //trying for medium pole
        double degree = (MEDIUMPOLEDEGREE); //need to find!
        arm. setTarget(degree);
        while (arm. isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
        arm.setArmWinch(MEDIUMARMEXTENSION); //need to find!
        while(arm.isWinchBusy() && opModeIsActive()) {
        }
    }*/

    //double goldenSpotX = 42.0, double goldenSpotY = -4.0, double goldenSpotHeading = -350.0; //degrees
    public void parkLeft() {            //try .lineToSplineHeading .setReversed(true)
        Trajectory parkLeftTrajectory = drive.trajectoryBuilder((goldenSpotTrajectory.end()))   //might need to change to fit new golden spot
                .lineToSplineHeading(new Pose2d(middleParkingSpotX, middleParkingSpotY, middleParkingSpotHeading))
                .build();
        drive.followTrajectory(parkLeftTrajectory);
        Trajectory backLeft = drive.trajectoryBuilder(parkLeftTrajectory.end())
                .back(19)
                .build();
        drive.followTrajectory(backLeft);

        /*Trajectory parkLeftTrajectory = drive.trajectoryBuilder(redRightTallPole.end())
            .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
            .build();
        drive.followTrajectory(parkLeftTrajectory);
        Trajectory strafeLR = drive.trajectoryBuilder(parkLeftTrajectory.end())
                .strafeLeft(parkingStrafeValue)
                .build();
        drive.followTrajectory(strafeLR);*/

    }
    public void parkRight() {   //might need to change
        Trajectory parkRightTrajectory = drive.trajectoryBuilder(goldenSpotTrajectory.end())
                .lineToSplineHeading(new Pose2d(middleParkingSpotX, middleParkingSpotY, middleParkingSpotHeading))
                .build();
        drive.followTrajectory(parkRightTrajectory);
        Trajectory forwardRight = drive.trajectoryBuilder(goldenSpotTrajectory.end())
                .forward(19)
                .build();
        drive.followTrajectory(forwardRight);


        /*Trajectory parkRightTrajectory = drive.trajectoryBuilder(redRightTallPole.end())
                .splineTo(new Vector2d(middleParkingSpotX,middleParkingSpotY),middleParkingSpotHeading)
                .build();
        drive.followTrajectory(parkRightTrajectory);
        Trajectory strafeLR = drive.trajectoryBuilder(parkRightTraje    ctory.end())
                .strafeRight(parkingStrafeValue)
                .build();
        drive.followTrajectory(strafeLR);*/

    }
    public void parkMiddle() {  //might need to change
        Trajectory parkMidTrajectory = drive.trajectoryBuilder((goldenSpotTrajectory.end()))
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


    public void armToVertical(){
        arm.setTarget(90);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }


}