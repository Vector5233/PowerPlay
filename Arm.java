package org.firstinspires.ftc.teamcode.VectorCode;

import static java.lang.Math.cos;

import android.util.Log;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.source.tree.WhileLoopTree;

import org.apache.commons.math3.analysis.function.Power;
import org.checkerframework.checker.units.qual.Length;


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
    public static double vMax = AgnesConstants.armVMax;
    public static double kV = AgnesConstants.armKV;
    public static double kA = AgnesConstants.armKA;
    public static double a = AgnesConstants.armAcceleration;
    public static double rampDistance = (a == 0.0)? 0: vMax*vMax/a;


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
    OpMode myParent;

    public Arm() {

    }
    public Arm(OpMode parent) {
        myParent = parent;
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
        armWinch.setTargetPositionTolerance(AgnesConstants.WINCHTOLERANCE);
        armRotation = (DcMotorEx) map.dcMotor.get("armRotation");
        armRotation.setDirection(DcMotorEx.Direction.REVERSE);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        /*Log.println(Log.INFO, "Arm encoder reset value: ",armRotation.getCurrentPosition() + " ticks" );*/
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
        /*Log.println(Log.INFO, "Extension: ", "target  " + armWinch.getTargetPosition());
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(getArmLength()));*/
        return armExtension;
    }

    public int getWinchTol() {
        return armWinch.getTargetPositionTolerance();
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
        setArmWinch(lengthToTicks(length));
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
        /*if (holding){
            holding = false;
            armRotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }*/

        double angle = getAngle();
        if (Math.abs(controller.getSetPoint() - angle) < AgnesConstants.TOL){
            controller.reset();
        }

        double controllerPower = controller.calculate(angle);
        if ((Math.abs(getVelocity()) < THRESHOLDBUSY) && (Math.abs(getTarget() - angle) < THRESHOLDBUSYANGLE)){
            busy = false;
        }

        double power = controllerPower + getFPower(angle);
        armRotation.setPower(power);
        /*Log.println(Log.INFO, "Rotation: ", "target: " + armRotation.getTargetPosition());
        Log.println(Log.INFO, "Rotation: ", "current position " + armRotation.getCurrentPosition());
        Log.println(Log.INFO, "Rotation: ", "current degrees: " + getAngle());*/
        return power;
    }

    public double getFPower(double angle) {
        return f*getArmLength()/2.0* cos(Math.toRadians(angle));
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

    public void holdPosition() {
        double SAFETY_FACTOR = 1.4;
        /*holding = true;
        double power = setPower();
        if (power <.1){
            power = MINPOWER;
        }
        armRotation.setTargetPosition(armRotation.getCurrentPosition());
        armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotation.setPower(power);
    */
        armRotation.setPower(SAFETY_FACTOR*getFPower(getAngle()));
    }


}

