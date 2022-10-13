package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="WheelDebugging", group="debug")
public class WheelDebugging extends OpMode {
    DcMotor leftFront, leftRear, rightFront, rightRear;
    public void init() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop() {
        double motorPower = -gamepad1.left_stick_y;
        telemetry.addData("power: " , motorPower);

        leftFront.setPower(motorPower);
        leftRear.setPower(motorPower);
        rightFront.setPower(motorPower);
        rightRear.setPower(motorPower);

    }
}
