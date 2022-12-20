package org.firstinspires.ftc.teamcode.VectorCode;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Arm {
    private PIDController controller;

    public static double p=0, i=0, d=0;
    public static double f=0;

    public static int target = 0;

    private final double ticks_in_degree = 700/180.0; //need to find actual ticks



    DcMotorEx armWinch, armRotationRightMotor , armRotationLeftMotor;
    final double MAX_EXT_TICKS = AgnesConstants.MAX_EXT_TICKS;
    final double MIN_EXT_TICKS = AgnesConstants.MIN_EXT_TICKS;
    final double MAXANGLE = AgnesConstants.MAXANGLE;
    final double MINANGLE = AgnesConstants.MINANGLE;
    final double ARMROTATIONTICKSPERREV = AgnesConstants.ARMROTATIONTICKSPERREV;
    double MAX_ARM_ANG_TICKS;
    double MIN_ARM_ANG_TICKS;
    final double MINARMLENGTH = AgnesConstants.MINARMLENGTH;
    final double MAXARMLENGTH = AgnesConstants.MAXARMLENGTH;
    double MAXTELEOPTICKS;
    double MINTELEOPTICKS;


    public Arm() {

    }

    //inits arm winch and arm rotation
    public void initialize(HardwareMap map, boolean auto){
        if (auto) {
            MAX_ARM_ANG_TICKS = AgnesConstants.MAXAUTOTICKS;
            MIN_ARM_ANG_TICKS = AgnesConstants.MINAUTOTICKS;
        }
        else {
            MAX_ARM_ANG_TICKS = AgnesConstants. MAXTELEOPTICKS;
            MIN_ARM_ANG_TICKS = AgnesConstants. MINTELEOPTICKS;

        }

        controller = new PIDController(p,i,d);

        armWinch = (DcMotorEx) map.dcMotor.get("armWinch");
        armWinch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        armRotationRightMotor = (DcMotorEx) map.dcMotor.get("armRotationRightMotor");
        armRotationRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotationRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRotationLeftMotor = (DcMotorEx) map.dcMotor.get("armRotationLeftMotor");
        armRotationLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotationLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRotationLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

       /* armRotation = (DcMotorEx) map.dcMotor.get("armRotation");
        armRotation.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); */
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

    public double getAngle(){
       int position = armRotationRightMotor.getCurrentPosition();  // right arm is the prime arm
       double angle = (MINANGLE + (360/ARMROTATIONTICKSPERREV)*(position- MIN_ARM_ANG_TICKS));
       return angle;
    }

    public double getArmLength(){
        int position = armWinch.getCurrentPosition();
        double length = MINARMLENGTH + ((MINARMLENGTH - MAXARMLENGTH)/(MIN_EXT_TICKS - MAX_EXT_TICKS))*(position - MIN_EXT_TICKS);
        return length;
    }


    public void setTarget(double degrees){
        if (target > MAX_ARM_ANG_TICKS || target < MIN_ARM_ANG_TICKS){
            return;
        }
        controller.setSetPoint(degrees);
    }

    public double getTarget(){
        return controller.getSetPoint();
    }

    public void setPower(){
        double angle = getAngle();
        double power = controller.calculate(angle);
        armRotationRightMotor.setPower(power);
        armRotationLeftMotor.setPower(power);
    }

    public void updatePIDController (double p, double i, double d){
        controller.setPID(p,i,d);
    }


    public int getRightMotorEncoder (){
        return armRotationRightMotor.getCurrentPosition();

    }

    public int getLeftMotorEncoder (){
        return  armRotationLeftMotor.getCurrentPosition();
    }

    public boolean isWinchBusy(){
        return armWinch.isBusy();
    }

    public boolean isRotationBusy(){
        double difference = getTarget() - getAngle();
        double THRESHOLD = 3;
        if (difference <= THRESHOLD){
            return true;
        } else {
            return false;
        }
    }
}
