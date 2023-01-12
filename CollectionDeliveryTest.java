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
        extendToCone();
        rotateToCone(0);
        pickUpCone();
        rotateToPole();
        extendToPole();
        dropCone();
    }

    public void extendToCone (){
        int distance = AgnesConstants.ARMEXTENSION;
        grabber.setGrabberHandOpen();
        arm.setArmWinch(distance);
    }

    public void rotateToCone(int arrayValue){
       double coneDegree = AgnesConstants.CONEDEGREE [arrayValue];
       arm.setTarget(coneDegree);
       while(arm.isRotationBusy() && opModeIsActive()) {
           arm.setPower();
       }
    }


    public void pickUpCone(){
        grabber.setGrabberHandClosed();
        sleep(AgnesConstants.GRABBERCLOSETIME);
    }

    public void rotateToPole(){
        double rotationToPole = AgnesConstants.POLEDEGREE;
        arm.setTarget(rotationToPole);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
        }
    }

    public void extendToPole(){
        int extentionLength = 1000;
        arm.setArmWinch(extentionLength);
    }

    public void dropCone(){
        grabber.setGrabberHandOpen();
        sleep(AgnesConstants.GRABBEROPENTIME);
    }
}
