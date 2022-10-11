package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="JeffDriving", group="learning")
public class JeffDriving extends OpMode {

    private DcMotor leftFront, leftRear, rightFront, rightRear;

    public void init() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop() {
        setDrivePower();
    }

    public void setDrivePower() {
        final double THRESHOLD = 0.10;
        double forwardPower = -gamepad1.left_stick_y;
        double strafePower = gamepad1.left_stick_x;
        double turnPower = gamepad1.right_stick_x;

        if (Math.abs(forwardPower) < THRESHOLD) {forwardPower = 0;}
        if (Math.abs(strafePower) < THRESHOLD) {strafePower = 0;}
        if (Math.abs(turnPower) < THRESHOLD) {turnPower = 0;}

        leftFront.setPower(forwardPower+strafePower+turnPower);
        leftRear.setPower(forwardPower-strafePower+turnPower);
        rightFront.setPower(forwardPower-strafePower-turnPower);
        rightRear.setPower(forwardPower+strafePower-turnPower);
    }
}
