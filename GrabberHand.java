package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class GrabberHand {

    Servo left_claw, right_claw;

    final double LEFTOPEN = .5;
    final double RIGHTOPEN = -.5;
    final double LEFTCLOSED = -.5;
    final double RIGHTCLOSED = .5;
    final double RIGHTINIT = 0;
    final double LEFTINIT = 0;
    public GrabberHand(HardwareMap h) {

        HardwareMap hardwareMap = h;
        left_claw = h.servo.get("leftClaw");
        right_claw = h.servo.get("right_claw");


    }

        public void open() {
            left_claw.setPosition(LEFTOPEN);
            right_claw.setPosition(RIGHTOPEN);
        }
        public void closed() {
            left_claw.setPosition(LEFTCLOSED);
            right_claw.setPosition(RIGHTCLOSED);
        }
        public void init() {
            left_claw.setPosition(LEFTINIT);
            right_claw.setPosition(RIGHTINIT);
    }
}

