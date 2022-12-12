package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
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


    public void initialize(HardwareMap map){
        armWinch = (DcMotorEx) map.dcMotor.get("armWinch");
        armWinch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        armRotation = (DcMotorEx) map.dcMotor.get("armRotation");
        armRotation.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }


    public void setArmRotation(int rotation){
        //double angle = ((6* (Math.abs(MAXTICKS) + Math.abs(MINTICKS))) / (5* 3.14159)) * rotation + MINTICKS;
        armRotation.setTargetPosition(rotation);
        armRotation.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armRotation.setPower(.95);
    }

    public void setArmWinch(int armExtension){
        double liftPower = .3;
        armWinch.setTargetPosition(armExtension);
        armWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armWinch.setPower(liftPower);
    }
//fix get armAngle
    public double getArmAngle(int rotation){
        double angle = ((6* (Math.abs(MAXTICKS) + Math.abs(MINTICKS))) / (5* 3.14159)) * rotation + MINTICKS;
        return angle;
    }
}
