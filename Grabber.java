package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
    Servo grabberLeftHand, grabberRightHand, grabberRotation;
    final double THRESHOLD = AgnesConstants.THRESHOLD;
    final double GRABBERINITSERVO = AgnesConstants.GRABBERINITSERVO;
    final double RIGHTGRABBERINITHAND = AgnesConstants.RIGHTGRABBERINITHAND;
    final double LEFTGRABBERINITHAND = AgnesConstants.LEFTGRABBERINITHAND;
    final double CLOSEDRIGHTHAND = AgnesConstants.CLOSEDRIGHTHAND;
    final double CLOSEDLEFTHAND = AgnesConstants.CLOSEDLEFTHAND;
    final double OPENEDRIGHTHAND = AgnesConstants.OPENEDRIGHTHAND;
    final double OPENEDLEFTHAND = AgnesConstants.OPENEDLEFTHAND;
    final double DELTA = AgnesConstants.DELTA;
    final double RECOVER_LEFT = AgnesConstants.RECOVER_LEFT;
    final double RECOVER_RIGHT = AgnesConstants.RECOVER_RIGHT;

    public Grabber(){

    }

    public void initialize(){
        grabberLeftHand = hardwareMap.servo.get("grabberLeftHand");
        grabberRotation = hardwareMap.servo.get("grabberRotation");

        grabberRightHand = hardwareMap.servo.get("grabberRightHand");
        grabberLeftHand = hardwareMap.servo.get("grabberLeftHand");
        grabberRotation = hardwareMap.servo.get("grabberRotation");
    }

    public void setGrabberHandOpen(){

    }

    public void setGrabberHandClosed(){

    }

    public void setGrabberRotation(){

    }

    public void setGrabberAngle(){

    }
}
