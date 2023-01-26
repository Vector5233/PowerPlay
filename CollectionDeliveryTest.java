package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.ARMEXTENSIONPOLE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.POLEDEGREE;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name="CollectionDeliveryTest", group="robot")
public class CollectionDeliveryTest extends AutoTemplate{


    @Override
    public void runOpMode() {
        initialize();
        autoDeliveryRight.setPosition(AgnesConstants.RECOVER_RIGHT);
        autoDeliveryLeft.setPosition(AgnesConstants.RECOVER_LEFT);
        grabber.setGrabberHandOpen();
        waitForStart();


        for (int j = 0; j<5; j = j+1){
            armToCollect(j);
            grabCone();

            rotateTo(120);
            deliverCone();
        }


        /*
        deextend();
        rotateTo(90);

        turnToPole();
        armToDeliver();
        deliverCone();
        deextend();
        rotateTo(90);

        turnToCone();
*/
    }

    public void rotateTo(double degree) {
        arm.setTarget(degree);
        while (arm.isRotationBusy() && opModeIsActive()) {
            arm.setPower();
            Log.println(Log.INFO, "rotate: ", "angle " + Double.toString(arm.getAngle()));
        }
    }

    public void deextend (){
        int distance = 0;
        arm.setArmWinch(distance);
        while (arm.isWinchBusy() && opModeIsActive()){
            //Log.println(Log.INFO, "Extension: ", "Inside the loop. ticks " + Double.toString(arm.getArmLength()));
            //Log.println(Log.INFO, "Extension: ", "ticks " + Double.toString(arm.getArmLength()));
        }
        //Log.println(Log.INFO, "Extension: ", "Out of the loop. ticks " + Double.toString(arm.getArmLength()));
    }

    public void turnToPole(){
        drive.turn(AgnesConstants.TURNTOPOLE);
    }

    public void turnToCone(){
        drive.turn(-AgnesConstants.TURNTOPOLE);
    }


   /*
    public void rotateToCone(int arrayValue){
       double coneDegree = AgnesConstants.CONEDEGREE [arrayValue];
       arm.setTarget(coneDegree);
       while(arm.isRotationBusy() && opModeIsActive()) {
           arm.setPower();
           Log.println(Log.INFO,"rotate: ", "angle "+ Double.toString(arm.getAngle()));
       }
       arm.holdPostion();
    }


    public void pickUpCone() {
        grabber.setGrabberHandClosed();
        sleep(AgnesConstants.GRABBERCLOSETIME);
    }



    public void rotateBotToPole(){

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

    public void rotateToPole() {
        double rotationToPole = AgnesConstants.POLEDEGREE;
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

    */
}
