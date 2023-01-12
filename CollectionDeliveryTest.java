package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="CollectionDeliveryTest", group="robot")
public class CollectionDeliveryTest extends AutoTemplate{


    @Override
    public void runOpMode() {
        initialize();


    }

    public void pickUpCone(int arrayValue){
        double coneDegree = AgnesConstants.CONEDEGREE [arrayValue];
        int distance = 0; //will equal the distance the arm has to extend to reach the cone
        grabber.setGrabberHandOpen();
        arm.setArmWinch(distance);
        arm.setTarget(coneDegree);
        arm.setPower();
        grabber.setGrabberHandClosed();
    }

    public void rotateToPole(int arrayValue){
        double basicRotation = 0; // found rotational degrees from top cone to pole
        double rotationBasedOnCone = basicRotation + AgnesConstants.CONEDEGREE[arrayValue];
        arm.setTarget(rotationBasedOnCone);

        arm.setPower();
    }
}
