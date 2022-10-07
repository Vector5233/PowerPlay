package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name="GrahameDrining",group="learning")
public class GrahameDrining extends OpMode {
    DcMotor leftFront, leftRear, rightFront, rightRear;
    public void init (){
        leftFront= hardwareMap.dcMotor.get ("leftFront");
        leftRear= hardwareMap.dcMotor.get ("leftRear");
        rightFront= hardwareMap.dcMotor.get ("rightFront");
        rightRear= hardwareMap.dcMotor.get ("rightRear");

    }

    public void loop(){

    }
}
