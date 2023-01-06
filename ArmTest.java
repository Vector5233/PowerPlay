package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.TELEOP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Config
@TeleOp(name = "ArmTest")
public class ArmTest extends OpMode {

    Arm stan;
    public static double p =0;
    public static double i = 0;
    public static double d =0;
    public static double f = 0;
    public static double target = 80;
    int armExtension =0;
    int MIN_EXT_TICKS = AgnesConstants.MIN_EXT_TICKS;
    int MAX_EXT_TICKS = AgnesConstants.MAX_EXT_TICKS;
    int ARMDELTA_EXT = AgnesConstants.ARMDELTA_EXT;


    public void init(){
        stan = new Arm();
        stan.initialize(hardwareMap, TELEOP );
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
        telemetry.addLine("arm is moving up and down");  // needed for timing!  Do not remove

    }

















    }
