package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public abstract class AutoTemplate extends LinearOpMode {
    //declarations
    DcMotor armWinch;
    DcMotor armRotation;
    Servo rightClaw;
    Servo leftClaw;
    double leftClawInit=AgnesConstants.LEFTGRABBERINITHAND;
    double rightClawInit=AgnesConstants.RIGHTGRABBERINITHAND;

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

        armWinch= hardwareMap.dcMotor.get("armWinch");
        armWinch.setDirection(DcMotor.Direction.FORWARD);
        armWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armRotation = hardwareMap.dcMotor.get("armRotation");
        armRotation.setDirection(DcMotor.Direction.FORWARD);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //?? From Elizabeth

        rightClaw=hardwareMap.servo.get("grabberRightHand");
        telemetry.addData("rightClawInit: ", rightClawInit);
       // rightClawInit = 0.5;
        rightClaw.setPosition(rightClawInit);


        leftClaw=hardwareMap.servo.get("grabberLeftHand");
        telemetry.addData("leftClawInit: ", leftClawInit);
        // leftClawInit = 0.5;
        leftClaw.setPosition(leftClawInit) ;
        telemetry.update();

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
