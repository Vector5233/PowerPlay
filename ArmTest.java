package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.AUTO;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.TELEOP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@Config
@TeleOp(name = "ArmTest")
public class ArmTest extends OpMode {

    Arm stan;
    Grabber hand;
    public static double p = AgnesConstants.p;
    public static double i = AgnesConstants.i;
    public static double d = AgnesConstants.d;
    public static double f = AgnesConstants.f;
    public static double target = 80;
    int armExtension =0;
    int MIN_EXT_TICKS = AgnesConstants.MIN_EXT_TICKS;
    int MAX_EXT_TICKS = AgnesConstants.MAX_EXT_TICKS;
    int ARMDELTA_EXT = AgnesConstants.ARMDELTA_EXT;
    public static double grabberClose = AgnesConstants.CLOSEDGRABBERHAND;


    public void init(){
        stan = new Arm();
        stan.initialize(hardwareMap, AUTO );
        hand = new Grabber();
        hand.initialize(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }


    public void loop(){
        telemetry.addData("Target: ", stan.getTarget());
        telemetry.addData("Angle(degrees): ", stan.getAngle());
        telemetry.addData("Rotation Ticks: ", stan.getArmMotorEncoder());
        telemetry.addData("Arm Length: ", stan.getArmLength());
        telemetry.addData("Power Given: ", stan.getRotationPower());
        //telemetry.addData("Cosine of angle: ", Math.cos(Math.toRadians(stan.getAngle())));
        //telemetry.addData("To radians of angle: ", Math.toRadians(stan.getAngle()));

        stan.setTarget(target);
        stan.updatePIDFController( p, i, d, f);
        stan.setPower();

        hand.setGrabberHandClosedValue(grabberClose);
        setGrabberPosition();
        setArmExtension();
    }
    public void setArmExtension() {
        if (gamepad2.a && armExtension > MIN_EXT_TICKS){
            armExtension = armExtension - ARMDELTA_EXT;
        } else if (gamepad2.y && armExtension < MAX_EXT_TICKS){
            armExtension = armExtension + ARMDELTA_EXT;
        }

        stan.setArmWinch(armExtension);
        telemetry.addData("armExtension: ", armExtension);

    }

    public void setGrabberPosition() {
        if (gamepad2.left_bumper) hand.setGrabberHandClosed();
        else if (gamepad2.right_bumper) hand.setGrabberHandOpen();
    }

















    }
