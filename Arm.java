package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    DcMotorEx armWinch, armRotation;
    final double THRESHOLD = AgnesConstants.THRESHOLD;
    final double MAXTICKS = AgnesConstants.MAXTICKS;
    final double MINTICKS = AgnesConstants.MINTICKS;
    final int ARMDELTA = AgnesConstants.ARMDELTA;
    final int ARMDELTA_EXT = 10;



    public Arm() {

    }


    public void initialize(){
        armWinch = (DcMotorEx) hardwareMap.dcMotor.get("armWinch");
        armWinch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        armRotation = (DcMotorEx) hardwareMap.dcMotor.get("armRotation");
        armRotation.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        int joystick;
    }


    public void setArmRotation(double angle){
        /*armRotation.setTargetPosition(Math.round(angle + ARMDELTA * joystick));
        armRotation.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armRotation.setPower(.95);*/
    }

    public void setArmWinch(){

    }

    public double getArmAngle(){

        return 0;
    }
}
