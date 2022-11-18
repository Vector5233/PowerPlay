package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AutoTemplate extends LinearOpMode {
    //declarations
    DcMotor armWinch;
    DcMotor armRotation;
    Servo rightClaw;
    Servo leftClaw;
    double leftClawInit;
    double rightClawInit;
    double fx;
    double fy;
    double cx;
    double cy;
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    SampleMecanumDrive drive;

    //UNITS ARE METERS
    double tagsize = 0.166;


    public abstract void runOpMode();

    public void initialize() {




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
}
