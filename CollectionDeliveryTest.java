package org.firstinspires.ftc.teamcode.VectorCode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="CollectionDeliveryTest", group="robot")
public class CollectionDeliveryTest extends AutoTemplate{


    @Override
    public void runOpMode() {
        initialize();
        autoDeliveryRight.setPosition(AgnesConstants.RECOVER_RIGHT);
        autoDeliveryLeft.setPosition(AgnesConstants.RECOVER_LEFT);
        grabber.setGrabberHandOpen(); // added JRC
        waitForStart(); // added JRC

        rotateTo(100);
        rotateToCone(0);
        sleep(1000);
        extendToCone();
        sleep(2000);
        pickUpCone();
        rotatePartiallyToPole();
        //Log.println(Log.INFO, "runOpMode: ", "Run 1 - skipping extendToPole()");
        sleep(5000);
        /*extendToPole();
        //Log.println(Log.INFO, "runOpMode: ", "Arm angle after sleep "+arm.getAngle());
        rotateToPole();
        //Log.println(Log.INFO, "runOpMode: ", "Run 1 - skipping extendToPole()");
        sleep(5000);
        dropCone();
        retractArm();*/
    }

    public void rotateTo(double degree){
        arm.setTarget(degree);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO,"rotate: ", "angle "+ Double.toString(arm.getAngle()));
        }

    }
    public void extendToCone (){
        int distance = AgnesConstants.ARMEXTENSION; // need to put into ticks
        grabber.setGrabberHandOpen();
        arm.setArmWinch(distance);
        while (arm.isWinchBusy() && opModeIsActive()){
            //Log.println(Log.INFO, "Extension: ", "Inside the loop. ticks " + Double.toString(arm.getArmLength()));
            //Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        }
        //Log.println(Log.INFO, "Extension: ", "Out of the loop. ticks " + Double.toString(arm.getArmLength()));
    }

    public void rotateToCone(int arrayValue){
       double coneDegree = AgnesConstants.CONEDEGREE [arrayValue];
       arm.setTarget(coneDegree);
       while(arm.isRotationBusy() && opModeIsActive()) {
           arm.setPower();
           Log.println(Log.INFO,"rotate: ", "angle "+ Double.toString(arm.getAngle()));
       }
       arm.holdPostion();
    }


    public void pickUpCone(){
        grabber.setGrabberHandClosed();
        sleep(AgnesConstants.GRABBERCLOSETIME);
    }

    public void rotatePartiallyToPole(){
        double rotationToPole = AgnesConstants.FIRSTPOLEDEGREE;
        //double rotationToPole = 90;
        arm.setTarget(rotationToPole);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO,"rotate: ", "angle "+Double.toString(arm.getAngle()));
        }
        arm.holdPostion();
        //Log.println(Log.INFO, "rotate: ", "Done.");
    }

    public void extendToPole(){
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        int extensionLength = AgnesConstants.ARMEXTENSIONPOLE;
        arm.setArmWinch(extensionLength);
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        Log.println(Log.INFO, "Extension: ", "Is Winch Busy " + arm.isWinchBusy());

        while (arm.isWinchBusy() && opModeIsActive()){
            Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        }
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        Log.println(Log.INFO, "Extension: ", "Is Winch Busy " + arm.isWinchBusy());
    }

    public void rotateToPole(){
        double rotationToPole = AgnesConstants.SECONDPOLEDEGREE;
        //double rotationToPole = 90;
        arm.setTarget(rotationToPole);
        while(arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            //Log.println(Log.INFO,"rotate: ", "angle "+Double.toString(arm.getAngle()));
        }
        arm.holdPostion();
        //Log.println(Log.INFO, "rotate: ", "Done.");
    }
    public void dropCone(){
        grabber.setGrabberHandOpen();
        sleep(AgnesConstants.GRABBEROPENTIME);
    }

    public void retractArm(){
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        int extensionLength = 0;
        arm.setArmWinch(extensionLength);
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        Log.println(Log.INFO, "Extension: ", "Is Winch Busy " + arm.isWinchBusy());

        while (arm.isWinchBusy() && opModeIsActive()){
            Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        }
        Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        Log.println(Log.INFO, "Extension: ", "Is Winch Busy " + arm.isWinchBusy());
    }
}
