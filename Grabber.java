package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
    Servo grabberHand;
    final double OPENEDGRABBERHAND = AgnesConstants.OPENEDGRABBERHAND;
    double CLOSEDGRABBERHAND = AgnesConstants.CLOSEDGRABBERHAND;  // set to final when done with testing
    final double INITGRABBERHAND = AgnesConstants.INITGRABBERHAND;

    public Grabber(){

    }

    //sets grabber servo to initial position. values may be incorrect.
    public void initGrabberServo() {
        grabberHand.setPosition(INITGRABBERHAND);
    }

    //inits grabber
    public void initialize(HardwareMap map){
       grabberHand = map.servo.get("grabberHand");
       initGrabberServo();
    }

    public void setGrabberHandOpen(){
        grabberHand.setPosition(OPENEDGRABBERHAND);
    }

    public void setGrabberHandClosed(){
        grabberHand.setPosition(CLOSEDGRABBERHAND);

    }

    public void setGrabberHandClosedValue(double value) {
        /** set grabber hand to different values for teleop and auto
         */
        if (0.0<= value && value <= 1.0) {
            CLOSEDGRABBERHAND = value;
        }
    }

    public void setGrabberHandTravel() {
        grabberHand.setPosition(AgnesConstants.grabberHandTravelPosition);


    }

}
