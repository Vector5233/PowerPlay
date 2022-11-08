package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public abstract class AutoTemplate extends LinearOpMode {
    //declarations
    DcMotor armWinch;
    DcMotor armRotation;
    Servo rightClaw;
    Servo leftClaw;
    public abstract void runOpMode();

    public void initialize() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        armWinch= hardwareMap.dcMotor.get("armWinch");
        armWinch.setDirection(DcMotor.Direction.FORWARD);
        armWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armRotation = hardwareMap.dcMotor.get("armRotation");
        armRotation.setDirection(DcMotor.Direction.FORWARD);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //?? From Elizabeth
        double rightClawInit = 0.5;
        rightClaw=hardwareMap.servo.get("rightClaw");
        rightClaw.setPosition(rightClawInit);

        double leftClawInit = 0.5;
        leftClaw=hardwareMap.servo.get("leftClaw");
        leftClaw.setPosition(leftClawInit) ;

    }
}
