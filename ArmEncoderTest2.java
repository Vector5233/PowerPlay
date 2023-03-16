package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;

import static java.lang.Math.cos;

import android.util.Log;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "ArmEncoderTest2")
public class ArmEncoderTest2 extends AutoTemplate {
    public void runOpMode(){
        initialize();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
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
        telemetry.addLine("hold this angle for a bit");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to min target  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
        armToTestAngle();
        telemetry.addLine("arm to 37 degrees");
        telemetry.addLine("hold this angle for a bit");
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
        smallArmAngleChange();
        telemetry.addLine("arm to 91 degrees");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to 91  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
        smallArmAngleChange2();
        telemetry.addLine("arm to 92 degrees");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to 92  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
        smallArmAngleChange();
        telemetry.addLine("arm to 91 degrees");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to 91  " + arm.getAngle());
        while (!gamepad1.a && opModeIsActive()) {
            ;
        }
        smallArmAngleChange2();
        telemetry.addLine("arm to 92 degrees");
        telemetry.update();
        Log.println(Log.INFO, "Rotation: ", "made it to 92  " + arm.getAngle());
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
    public void smallArmAngleChange() {
        arm.setTarget(91);
        while (arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }
    public void smallArmAngleChange2() {
        arm.setTarget(92);
        while (arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }

}
