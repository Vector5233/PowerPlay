package org.firstinspires.ftc.teamcode.VectorCode;

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
    public static double target = 80;

    public void init(){
        stan = new Arm();
        stan.initialize(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    };






    public void loop(){
        telemetry.addData("Target: ", stan.getTarget());
        telemetry.addData("Angle(degrees): ", stan.getAngle());
        telemetry.addData("Rotation Ticks: ", stan.getRightMotorEncoder());
        stan.setTarget(target);
        stan.setPower();
        stan.updatePIDController( p, i, d);
    };


















    }
