package org.firstinspires.ftc.teamcode.VectorCode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp (name = "ScorpionTeleOp", group = "robot")
public class ScorpionTeleOp {
    DcMotor baseLeft, baseRight, rotationOne, rotationTwo;

    public void init() {
        baseLeft = hardwareMap.dcMotor.get("baseLeft");
        baseRight = hardwareMap.dcMotor.get("baseRight");
        rotationOne= hardwareMap.dcMotor.get("rotationOne");
        rotationTwo = hardwareMap.dcMotor.get("rotationTwo");

        baseLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        baseLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        baseRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        baseRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        rotationOne.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rotationOne.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        rotationTwo.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rotationTwo.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


   }









}
