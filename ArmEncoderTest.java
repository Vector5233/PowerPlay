package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;

import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name = "ArmEncoderTest")
public class ArmEncoderTest extends AutoTemplate {
    public void runOpMode(){
        initialize();
        telemetry.addLine("initialized");
        telemetry.update();
        autoDeliveryLeft.setPosition( RECOVER_LEFT);
        autoDeliveryRight.setPosition( RECOVER_RIGHT);
        grabber.setGrabberHandClosed();
        armToVertical();

        waitForStart();

        sleep(1500);

        armToMax();

        telemetry.addLine("arm to 137 degrees");
        telemetry.update();

        Log.println(Log.INFO, "Rotation: ", "made it to max target  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
        armToFrontAngle();
        telemetry.addLine("arm to 5 degrees");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to min target  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
        armToTestAngle();
        telemetry.addLine("arm to 37 degrees");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to 37  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
        armToVertical();
        telemetry. addLine("arm to 90 degrees");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to 90  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
    }
    public void armToMax(){
        arm.setTarget(137);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }
    public void armToFrontAngle() {
        arm.setTarget(5);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }
    public void armToTestAngle() {
        arm.setTarget(37);
        while (arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }

    }
}
