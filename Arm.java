package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.roadrunner.control.PIDCoefficients;

@Config
public class Arm {
    private PIDController controller;

    public static double p=0, i=0, d=0;
    public static double f=0;

    public static int target = 0;

    private final double ticks_in_degree = 700/180.0; //need to find actual ticks



    DcMotorEx armWinch, armRotation;
    final double MAXTICKS = AgnesConstants.MAXTICKS;
    final double MINTICKS = AgnesConstants.MINTICKS;

    public Arm() {

    }

    //inits arm winch and arm rotation
    public void initialize(HardwareMap map){

        controller = new PIDController(p,i,d);

        armWinch = (DcMotorEx) map.dcMotor.get("armWinch");
        armWinch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        armRotation = (DcMotorEx) map.dcMotor.get("armRotation");
        armRotation.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

//prob get rid of bc of new method of doing rotation
    /*
    public void setArmRotation(int rotation){
        armRotation.setTargetPosition(rotation);
        armRotation.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armRotation.setPower(.95);
    }*/

    public void setArmWinch(int armExtension){
        double liftPower = .3;
        armWinch.setTargetPosition(armExtension);
        armWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armWinch.setPower(liftPower);
    }

    //fix get armAngle
    public double getArmAngle(int position){
        double angle = ((6* (Math.abs(MAXTICKS) + Math.abs(MINTICKS))) / (5* 3.14159)) * position + MINTICKS;
        return angle;
    }

    public void setTarget(double degrees){
        // ^^^ JRC: name should be consistent - both setTarget() and getTarget or else setSetPoint() and getSetPoint()
        //have the robot check min and max ticks here
        controller.setSetPoint(degrees);
    }

    public double getSetPoint(){
        return controller.getSetPoint();
    }

    public void setPower(){
        int position = armRotation.getCurrentPosition();
        int angle = (int) getArmAngle(position);  // why int?
        double power = controller.calculate(angle);
        armRotation.setPower(power);
    }

    public void updatePIDController (double a, double b, double c){

    }
}
