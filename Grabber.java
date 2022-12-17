package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
   // Servo grabberLeftHand, grabberRightHand, grabberRotation;
    Servo grabberHand;
    final double THRESHOLD = AgnesConstants.THRESHOLD;
    final double GRABBERINITSERVO = AgnesConstants.GRABBERINITSERVO;
    final double OPENEDGRABBERHAND = AgnesConstants.OPENEDGRABBERHAND;
    final double CLOSEDGRABBERHAND = AgnesConstants.CLOSEDGRABBERHAND;
    final double INITGRABBERHAND = AgnesConstants.INITGRABBERHAND;


    //final double RIGHTGRABBERINITHAND = AgnesConstants.RIGHTGRABBERINITHAND;
    //final double LEFTGRABBERINITHAND = AgnesConstants.LEFTGRABBERINITHAND;
    //final double CLOSEDRIGHTHAND = AgnesConstants.CLOSEDRIGHTHAND;
    //final double CLOSEDLEFTHAND = AgnesConstants.CLOSEDLEFTHAND;
   // final double OPENEDRIGHTHAND = AgnesConstants.OPENEDRIGHTHAND;
    //final double OPENEDLEFTHAND = AgnesConstants.OPENEDLEFTHAND;
    final double DELTA = AgnesConstants.DELTA;

    public Grabber(){

    }

    //sets grabber servo position
   // public void initGrabberRotation(){grabberRotation.setPosition(GRABBERINITSERVO);}

    //sets grabber servo to initial position. values may be incorrect.
    public void initGrabberServo() {
        grabberHand.setPosition(INITGRABBERHAND);
        //grabberLeftHand.setPosition(OPENEDLEFTHAND);
        //grabberRightHand.setPosition(OPENEDRIGHTHAND);
    }

    //inits grabber
    public void initialize(HardwareMap map){
       grabberHand = map.servo.get("grabberHand");
       /*JRC: call initGrabberServo() here?

         once ...
        grabberLeftHand = map.servo.get("grabberLeftHand");
        grabberRotation = map.servo.get("grabberRotation");


        grabberRightHand = map.servo.get("grabberRightHand");
         ... twice.  Why?
        grabberLeftHand = hardwareMap.servo.get("grabberLeftHand");
         grabberRotation = hardwareMap.servo.get("grabberRotation"); */
    }

    public void setGrabberHandOpen(){
        grabberHand.setPosition(OPENEDGRABBERHAND);
        //grabberRightHand.setPosition(OPENEDRIGHTHAND);
        //grabberLeftHand.setPosition(OPENEDLEFTHAND);
    }

    public void setGrabberHandClosed(){
        grabberHand.setPosition(CLOSEDGRABBERHAND);
        //grabberRightHand.setPosition(CLOSEDRIGHTHAND);
        //grabberLeftHand.setPosition(CLOSEDLEFTHAND);
    }

    /*public void setGrabberRotation(double position){

        grabberRotation.setPosition(position);

    }
    public double getGrabberRotation(){
        return grabberRotation.getPosition();
    }



    public void setGrabberAngle(){

    }*/
}
