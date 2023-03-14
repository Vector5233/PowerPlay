package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.RECOVER_RIGHT;

import static java.lang.Math.cos;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "ArmEncoderTest2")
public class ArmEncoderTest2 extends AutoTemplate {

    boolean holding = false;
    boolean busy = false;
    public static double vMax = AgnesConstants.armVMax;
    public static double kV = AgnesConstants.armKV;
    public static double kA = AgnesConstants.armKA;
    public static double a = AgnesConstants.armAcceleration;
    public static double rampDistance = (a == 0.0)? 0: vMax*vMax/a;

    public void accelerate(double time) {
        double power;
        ElapsedTime timer= new ElapsedTime();
        while(timer.milliseconds() < time) {
            power= kV * armRotation.getVelocity() + kA * a + f * getArmLength() * cos(Math.toRadians(getAngle()));
            armRotation.setPower(power);
        }
    }

    public void decelerate(double time){
        double power;
        ElapsedTime timer= new ElapsedTime();
        while(timer.milliseconds() < time){
            power= kV * armRotation.getVelocity() - kA * a + getArmLength() * cos(Math.toRadians(getAngle()));
            armRotation.setPower(power);
        }
    }

    public void moveArm(double distance) {
        double time; // needs initialization
        if (a == 0) return;
        if (distance < rampDistance) {  // fix condition

            time = Math.sqrt(4 * distance / a); // div 0 error
            accelerate(time / 2);
            decelerate(time / 2);
        } else {
            double excess = distance - (rampDistance);
            time = (2 * vMax / a);
            accelerate(time / 2);

            decelerate(time / 2);
        }
    }

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
