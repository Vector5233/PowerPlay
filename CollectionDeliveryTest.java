package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.ARMEXTENSION;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.ARMEXTENSIONPOLE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.COLLECTIONLENGTH;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.POLEDEGREE;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.SECONDDELIVERYLENGTH;

import android.util.Log;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name="CollectionDeliveryTest", group="robot")
public class CollectionDeliveryTest extends AutoTemplate{
public static final double SECONDPOLEDDEGREE = AgnesConstants.SECONDPOLEDEGREE;

    @Override
    public void runOpMode() {
        initialize();
        telemetry.addData("winch tolerance: ", arm.getWinchTol());
        telemetry.update();
        autoDeliveryRight.setPosition(AgnesConstants.RECOVER_RIGHT);
        autoDeliveryLeft.setPosition(AgnesConstants.RECOVER_LEFT);
        grabber.setGrabberHandOpen();
        waitForStart();


        //for (int j = 0; j<5; j = j+1){
            armToCollect(0);
            holdPosition(1000);
            grabCone();
            /*arm.setArmLength(COLLECTIONLENGTH - .25);
            rotateTo(90);
            sleep(50);
            //Trajectory pole = turnToPole();
            armToDeliver();
            sleep(500);
            deliverCone();
            rotateTo(90); */

        //}

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

    public Trajectory turnToPole(){
        Trajectory trajectory = drive.trajectoryBuilder(new Pose2d(), true)
                .splineTo(new Vector2d(-5.45,1.2), Math.toRadians(147))
                .build();
        drive.followTrajectory(trajectory);
        return trajectory;
    }

    public void turnToCone(Trajectory trajectory){
        Trajectory coneTrajectory = drive.trajectoryBuilder(trajectory.end(), false)
                .splineTo(new Vector2d(0,0), Math.toRadians(0))
                .build();
        drive.followTrajectory(coneTrajectory);
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
