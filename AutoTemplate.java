package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.ARMEXTENSION;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.ARMEXTENSIONPOLE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.AUTO;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.CONEDEGREE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.DELIVER_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBERCLOSETIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.GRABBEROPENTIME;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.INIT_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.INIT_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.SECONDPOLEDEGREE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.TELEOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public abstract class AutoTemplate extends LinearOpMode {
    //declarations
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
        double degree = (CONEDEGREE[cone]);
        arm.setArmWinch(ARMEXTENSION);
        while(arm.isWinchBusy() && opModeIsActive()) {

        }
        arm.setTarget(degree);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }

    }
    public void armToDeliver() {
        double degree = (SECONDPOLEDEGREE);
        arm.setTarget(degree);
        while (arm. isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
        arm.setArmWinch(ARMEXTENSIONPOLE);
        while(arm.isWinchBusy() && opModeIsActive()) {
            ;
        }
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
