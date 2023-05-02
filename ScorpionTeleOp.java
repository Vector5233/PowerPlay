package org.firstinspires.ftc.teamcode.VectorCode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp (name = "ScorpionTeleOp", group = "robot")
public class ScorpionTeleOp {
    DcMotor shoulderLeft, shoulderRight, elbowOne, elbowTwo;

    public void init() {
        shoulderLeft = hardwareMap.dcMotor.get("shoulderLeft");
        shoulderRight = hardwareMap.dcMotor.get("shoulderRight");
        elbowOne = hardwareMap.dcMotor.get("elbowOne");
        elbowTwo = hardwareMap.dcMotor.get("elbowTwo");

        shoulderLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shoulderLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        shoulderRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shoulderRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        elbowOne.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        elbowOne.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        elbowTwo.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        elbowTwo.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
   }

   public void loop(){
        setPositionOne();
        setPositionTwo();
        setPositionThree();
        setPositionFour();
   }

   public void setPositionOne(){
        if (gamepad2.a){
            telemetry.addLine("position 1");
        }
   }
   public void setPositionTwo(){
        if (gamepad2.b){
            telemetry.addLine("position 2");
        }
   }
   public void setPositionThree(){
        if (gamepad2.y){
            telemetry.addLine("position 3");
        }
   }
   public void setPositionFour(){
        if (gamepad2.x){
            telemetry.addLine("position 4");
        }
   }





}
