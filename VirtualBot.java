package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class VirtualBot {
    DcMotor leftFront, rightFront, leftRear, rightRear;
    LinearOpMode parent;

    public VirtualBot(LinearOpMode p) {
        parent = p;
    }
    public void init() {
        leftFront = parent.hardwareMap.dcMotor.get("leftFront");
        rightFront = parent.hardwareMap.dcMotor.get("rightFront");
        leftRear = parent.hardwareMap.dcMotor.get("leftRear");
        rightRear = parent.hardwareMap.dcMotor.get("rightRear");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void reset() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void getReady() {
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void setPowerAll(double power) {
        leftFront.setPower(power);
        rightFront.setPower(power);
        leftRear.setPower(power);
        rightRear.setPower(power);
    }

    public void goForward(int ticks) {
        reset();
        getReady();
        setPowerAll(1);
        while (leftFront.getCurrentPosition() < ticks) {
            parent.sleep(1);
        }
        setPowerAll(0);
    }
}
