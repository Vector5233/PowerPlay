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

    final double DELTA = AgnesConstants.DELTA;

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
}
