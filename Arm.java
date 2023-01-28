package org.firstinspires.ftc.teamcode.VectorCode;

import android.util.Log;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Arm {
    private PIDController controller;


    public static double p;
    public static double i= AgnesConstants.i;
    public static double d= AgnesConstants.d;
    public static double f= AgnesConstants.f;


    /*
    public static double p= 0;
    public static double i= 0;
    public static double d= 0;
    public static double f= 0;


     */
    public static int target = 0;

    boolean holding = false;
    boolean busy = false;




    DcMotorEx armWinch, armRotation;
    final int MAX_EXT_TICKS = AgnesConstants.MAX_EXT_TICKS;
    final int MIN_EXT_TICKS = AgnesConstants.MIN_EXT_TICKS;
    final double MAXANGLE = AgnesConstants.MAXANGLE;
    final double MINANGLE = AgnesConstants.MINANGLE;
    final double MINPOWER = .1;
    final static double ARMROTATIONTICKSPERREV = AgnesConstants.ARMROTATIONTICKSPERREV;
    double MAX_ARM_ANG_TICKS;  //found in initialize, values decided based on auto or teleOp parameters
    double MIN_ARM_ANG_TICKS;
    final double MINARMLENGTH = AgnesConstants.MINARMLENGTH;
    final double MAXARMLENGTH = AgnesConstants.MAXARMLENGTH;
    final double THRESHOLDBUSY = AgnesConstants.THRESHOLDBUSY;
    final double THRESHOLDBUSYANGLE = AgnesConstants.THRESHOLDBUSYANGLE;

    public Arm() {

    }


    //inits arm winch and arm rotation
    public void initialize(HardwareMap map, boolean auto){
        if (auto) {
            MAX_ARM_ANG_TICKS = AgnesConstants.MAXAUTOTICKS;
            MIN_ARM_ANG_TICKS = AgnesConstants.MINAUTOTICKS;
            p = AgnesConstants.pAuto;
        }
        else {
            MAX_ARM_ANG_TICKS = AgnesConstants. MAXTELEOPTICKS;
            MIN_ARM_ANG_TICKS = AgnesConstants. MINTELEOPTICKS;
            p= AgnesConstants.p;
        }

        controller = new PIDController(p,i,d);

        armWinch = (DcMotorEx) map.dcMotor.get("armWinch");
        armWinch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        armRotation = (DcMotorEx) map.dcMotor.get("armRotation");
        armRotation.setDirection(DcMotorEx.Direction.REVERSE);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);




    }




    public int setArmWinch(int armExtension){
        double liftPower = .5; // increase power and ARM_EX DELTA or whatever for increased speed
        if (armExtension <MIN_EXT_TICKS){
            armExtension = MIN_EXT_TICKS;
        } else if (armExtension>MAX_EXT_TICKS){
            armExtension = MAX_EXT_TICKS;
        }
        armWinch.setTargetPosition(armExtension);
        armWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armWinch.setPower(liftPower);
        Log.println(Log.INFO, "Extension: ", "target  " + armWinch.getTargetPosition());
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(getArmLength()));
        return armExtension;
    }

    public int lengthToTicks(double length){
        int position = (int)(MIN_EXT_TICKS + (MIN_EXT_TICKS - MAX_EXT_TICKS)/(MINARMLENGTH - MAXARMLENGTH) * (length - MINARMLENGTH));
        return position;
    }


    public double setArmLength(double length){

        if (length > MAXARMLENGTH){
            length = MAXARMLENGTH;
        }
        else if (length < MINARMLENGTH){
            length = MINARMLENGTH;
        }
        setArmLength(lengthToTicks(length));
        return length;
    }


    public double getArmLength(){
        /** return length in inches
         */
        int position = armWinch.getCurrentPosition();
        double length = MINARMLENGTH + ((MINARMLENGTH - MAXARMLENGTH)/(MIN_EXT_TICKS - MAX_EXT_TICKS))*(position - MIN_EXT_TICKS);
        return length;
    }

    public double getAngle(){

        int position = armRotation.getCurrentPosition();

        double angle = MINANGLE + (360/ARMROTATIONTICKSPERREV) * (position - MIN_ARM_ANG_TICKS);
        return angle;

    }

    public void setTarget(double degrees){
        //JRC: should be degrees
        if (degrees > MAXANGLE || degrees < MINANGLE){
            return;
        }
        controller.setSetPoint(degrees);
        busy = true;
    }

    public double getVelocity(){
        return armRotation.getVelocity();
    }


    public double getTarget(){
        return controller.getSetPoint();
    }

    public double setPower(){
        if (holding){
            holding = false;
            armRotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        double angle = getAngle();
        if (Math.abs(controller.getSetPoint() - angle) < AgnesConstants.TOL){
            controller.reset();
        }

        double controllerPower = controller.calculate(angle);
        if ((Math.abs(getVelocity()) < THRESHOLDBUSY) && (Math.abs(getTarget() - getAngle()) < THRESHOLDBUSYANGLE)){
            busy = false;
        }

        double power = controllerPower + f*getArmLength()/2.0*Math.cos(Math.toRadians(angle));
        armRotation.setPower(power);
        return power;
    }

    public double getDifference(){
        return Math.abs(getTarget() - getAngle());
    }

    public void updatePIDFController(double new_p, double new_i, double new_d, double new_f) {
        controller.setPID(new_p, new_i, new_d);
        f = new_f;
    }


    public double getRotationPower(){
        double power = armRotation.getPower();
        return power;
    }


    public int getArmMotorEncoder(){
        return armRotation.getCurrentPosition();

    }


    public boolean isWinchBusy(){
        return armWinch.isBusy();
    }

    public boolean isRotationBusy(){
        return busy;
    }

    public void holdPostion(){
        holding = true;
        double power = setPower();
        if (power <.1){
            power = MINPOWER;
        }
        armRotation.setTargetPosition(armRotation.getCurrentPosition());
        armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotation.setPower(power);
    }
}

