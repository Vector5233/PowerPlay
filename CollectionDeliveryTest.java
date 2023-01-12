package org.firstinspires.ftc.teamcode.VectorCode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="CollectionDeliveryTest", group="robot")
public class CollectionDeliveryTest extends AutoTemplate{


    @Override
    public void runOpMode() {
        initialize();
        autoDeliveryRight.setPosition(AgnesConstants.RECOVER_RIGHT);
        autoDeliveryLeft.setPosition(AgnesConstants.RECOVER_LEFT);
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
        //double rotationToPole = 90;
        arm.setTarget(rotationToPole);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO,"Angle: ", Double.toString(arm.getAngle()));
        }
        arm.holdPostion();
    }

    public void extendToPole(){
        int extentionLength = 1000;
        arm.setArmWinch(extentionLength);
        //while (arm.isWinchBusy() && opModeIsActive());
    }

    public void dropCone(){
        grabber.setGrabberHandOpen();
        sleep(AgnesConstants.GRABBEROPENTIME);
    }
}
