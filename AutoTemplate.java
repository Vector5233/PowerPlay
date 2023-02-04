package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.AUTO;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.COLLECTIONLENGTH;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.CONEDEGREE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVERYADJUST;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.FIRSTDELIVERYLENGTH;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBERCLOSETIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBEROPENTIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.INIT_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.INIT_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.POLEDEGREE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.PRECOLLECTIONLENGTH;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.SECONDDELIVERYLENGTH;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.SECONDPOLEDEGREE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.SENSORDELAYVALUE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.grabberHandTravelPosition;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public abstract class AutoTemplate extends LinearOpMode {
    //declarations
    ElapsedTime armTimer = new ElapsedTime();
    Arm arm;
    Grabber grabber;
    Servo preConeRight;
    Servo preConeLeft;
    //double leftClawInit=AgnesConstants.LEFTGRABBERINITHAND;
   //double rightClawInit=AgnesConstants.RIGHTGRABBERINITHAND;
    Servo autoDeliveryLeft;
    Servo autoDeliveryRight;

    SampleMecanumDrive drive;

    //OpenCvCamera camera;
    //AprilTagDetectionPipeline aprilTagDetectionPipeline;

    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;
    //UNITS ARE METERS
    double tagsize = 0.166;

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    int ID_TAG_OF_INTEREST = 18; // Tag ID 18 from the 36h11 family

    AprilTagDetection tagOfInterest = null;
    static final double FEET_PER_METER = 3.28084;


    // Tag ID 1, 2, 3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    @Override
    public abstract void runOpMode();

    public void initialize() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);



       drive = new SampleMecanumDrive(hardwareMap);

       arm=new Arm();
       arm.initialize(hardwareMap, AUTO);  // initialize for autonomous opmode

       grabber=new Grabber();
       grabber.initialize(hardwareMap);
       grabber.setGrabberHandClosedValue(AgnesConstants.AUTOCLOSEDGRABBERHAND);
       /* armWinch= hardwareMap.dcMotor.get("armWinch");
        armWinch.setDirection(DcMotor.Direction.FORWARD);
        armWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armRotation = hardwareMap.dcMotor.get("armRotation");
        armRotation.setDirection(DcMotor.Direction.FORWARD);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);*/

        //?? From Elizabeth

      /*  rightClaw=hardwareMap.servo.get("grabberRightHand");
       // telemetry.addData("rightClawInit: ", rightClawInit);
       // rightClawInit = 0.5;
        //rightClaw.setPosition(rightClawInit);


        leftClaw=hardwareMap.servo.get("grabberLeftHand");*/
       // telemetry.addData("leftClawInit: ", leftClawInit);
        // leftClawInit = 0.5;
        //leftClaw.setPosition(leftClawInit);
        telemetry.update();
        autoDeliveryRight=hardwareMap.servo.get("autoDeliveryRight");
        autoDeliveryLeft=hardwareMap.servo.get("autoDeliveryLeft");
        autoDeliveryLeft.setPosition(INIT_LEFT);
        autoDeliveryRight.setPosition(INIT_RIGHT);

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
        armToVertical();
        grabber.setGrabberHandTravel();
        sleep(100);
        extendAndRotateTo(PRECOLLECTIONLENGTH, CONEDEGREE[cone]);
        grabber.setGrabberHandOpen();
        extendAndRotateTo(COLLECTIONLENGTH, CONEDEGREE[cone]);


        /*double degree = (CONEDEGREE[cone]);
        grabber.grabberHand.setPosition(0);
        arm.setTarget(degree);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO, "Rotation: ", "Current Rotation Power " + Double.toString(arm.getRotationPower()));
            Log.println(Log.INFO, "Rotation: ", "Current Rotation " + Double.toString(arm.getAngle()));
            Log.println(Log.INFO, "Rotation: ", "Is Busy? " + Boolean.toString(arm.busy));
            Log.println(Log.INFO,"Rotation", "Rotational velocity " + Double.toString(arm.getVelocity()));
            Log.println(Log.INFO,"Rotation", "Difference " + Double.toString(arm.getDifference()));
            double armLength = arm.getArmLength();
            Log.println(Log.INFO, "Extension: ", "extension ticks:  " + Double.toString(arm.lengthToTicks(armLength)));
            sleep(20);
        }
        grabber.setGrabberHandOpen();
        sleep(500);
        arm.setArmLength(COLLECTIONLENGTH);
        while(arm.isWinchBusy() && opModeIsActive()) {

        }*/
    }


    public void armToDeliver() {
        double degree = (POLEDEGREE);
        arm.setTarget(degree);
        while (arm. isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO, "Rotation: ", "Current Rotation Power " + Double.toString(arm.getRotationPower()));
            Log.println(Log.INFO, "Rotation: ", "Current Rotation " + Double.toString(arm.getAngle()));
            Log.println(Log.INFO, "Rotation: ", "Is Busy? " + Boolean.toString(arm.busy));
            Log.println(Log.INFO,"Rotation", "Rotational velocity " + Double.toString(arm.getVelocity()));
            Log.println(Log.INFO,"Rotation", "Difference " + Double.toString(arm.getDifference()));
            sleep(20);
        }
        sleep(50);
        arm.setArmLength(FIRSTDELIVERYLENGTH);
        while(arm.isWinchBusy() && opModeIsActive()) {
            double armLength = arm.getArmLength();
            Log.println(Log.INFO, "Extension: ", "extension ticks:  " + Double.toString(arm.lengthToTicks(armLength)));

        }
        sleep(1000);
        arm.setTarget(DELIVERYADJUST);
        while(arm.isRotationBusy() && opModeIsActive()){
            arm.setPower();
            sleep(20);
        }
        arm.setArmLength(SECONDDELIVERYLENGTH);
        while (arm.isWinchBusy() && opModeIsActive()){
            double armLength = arm.getArmLength();
            Log.println(Log.INFO, "Extension: ", "extension ticks:  " + Double.toString(arm.lengthToTicks(armLength)));
        }
    }

    public void grabberToVertical(){
        grabber.setGrabberHandOpen();
    }

    public void rotateTo(double degree) {
        arm.setTarget(degree);
        while (arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO, "rotate: ", "angle " + Double.toString(arm.getAngle()));
            sleep(20);
        }

    }

    public void extendAndRotateTo (double length,double degree ){
        arm.setTarget(degree);
        arm.setArmLength(length);
        while((arm.isRotationBusy() || arm.isWinchBusy()) && opModeIsActive()){
          arm.setPower();
          Log.println(Log.INFO, "extendAndRotate:  ", "angle" + arm.getAngle());
          Log.println(Log.INFO, "extendAndRotate:  ", "length" + arm.getArmLength());
          Log.println(Log.INFO, "WinchBusy   RotationBusy: ", ""+arm.isWinchBusy() + "   "+arm.isRotationBusy());

          sleep(SENSORDELAYVALUE);

        }
    }

    public void holdPosition (double time){
        armTimer.reset();
        while (armTimer.milliseconds()< time && opModeIsActive()){
            arm.setPower();
            sleep(20);
        }
    }



    public void armToVertical(){
        extendAndRotateTo(arm.getArmLength(),90);
        }


    public void detectAprilTags() {
        ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

        if (!currentDetections.isEmpty())
        {
            boolean tagFound = false;
            telemetry.addLine("Found one!");
            //telemetry.update();

            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                    tagOfInterest = tag;
                    tagFound = true;
                    break;
                }
            }

            if (tagFound) {
                telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                tagToTelemetry(tagOfInterest);
            } else {
                telemetry.addLine("Hi Chris. Don't see tag of interest :(");

                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }
            }

        } else {
            telemetry.addLine("Hi Mr P.  Don't see tag of interest :(");

            if (tagOfInterest == null) {
                telemetry.addLine("(The tag has never been seen)");
            } else {
                telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                tagToTelemetry(tagOfInterest);
            }
        }

        telemetry.update();
        sleep(20);
    }

    public void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }

    public void reportAprilTags() {
        if(tagOfInterest != null)  //basic change for commit.
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();


        }
    }

}
