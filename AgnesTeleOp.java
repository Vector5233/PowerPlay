package org.firstinspires.ftc.teamcode.VectorCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="AgnesTeleOp", group="robot")
public class AgnesTeleOp extends OpMode {
    DcMotor leftFront, leftRear, rightFront, rightRear;
    DcMotorEx armWinch, armRotation;
    Servo grabberHand, grabberRotation;

    final double APPROACHSPEED = .3;
    final double THRESHOLD = .1;
    final double GRABBERSERVO = .3;  //find actual values
    final double GRABBERHAND = .1; //find actual values
    final double MAXTICKS = 1850; // find what max ticks should actually be
    final double OPENHAND = .1; //find actual values
    final double CLOSEDHAND = -.1; //find actual values
    final double DELTA = .05;
    final double DELAY = 2000; //get actual wait time
    ElapsedTime timer;



    @Override
    public void init() {
        leftFront=hardwareMap.dcMotor.get("leftFront");
        leftRear=hardwareMap.dcMotor.get("leftRear");
        rightFront=hardwareMap.dcMotor.get("rightFront");
        rightRear=hardwareMap.dcMotor.get("rightRear");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        armWinch = (DcMotorEx) hardwareMap.dcMotor.get("armWinch");
        armWinch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        armRotation = (DcMotorEx) hardwareMap.dcMotor.get("armRotation");
        armRotation.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        grabberHand = hardwareMap.servo.get("grabberHand");
        grabberRotation = hardwareMap.servo.get("grabberRotation");

        timer = new ElapsedTime();

        initGrabberServo();
        //initCameraServo();
        initGrabberRotation();
    }

    @Override
    public void loop() {

       setDrive();
       setGrabberHand();
       setGrabberRotation();
       setArmExtension();
       setArmRotation();
       setSlowApproach();

    }

    //sets direction and power of all drive motors (frontLeft, frontRight, backLeft, backRight)
    public void setDrive(){
        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        telemetry.addData("Right Rear Position: ", rightRear.getCurrentPosition());
        telemetry.addData("Left Rear Position: ", rightRear.getCurrentPosition());
        telemetry.addData("Right Front Position: ", rightFront.getCurrentPosition());
        telemetry.addData("Left Front Position: ", leftFront.getCurrentPosition());
        telemetry.update();
        double leftFrontPower = trimPower(forward + strafe + turn);
        double rightFrontPower = trimPower(forward - strafe - turn);
        double leftRearPower = trimPower(forward - strafe + turn);
        double rightRearPower = trimPower(forward + strafe - turn);
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftRear.setPower(leftRearPower);
        rightRear.setPower(rightRearPower);
    }


    //called to trim power of driving joystick to ensure the robot does not move unintentionally
    public double trimPower(double Power) {
        if (Math.abs(Power) < THRESHOLD) {
            Power = 0;
        }
        return Power;

    }


    //rotates grabber grabber
    public void setGrabberRotation(){
        if ((gamepad1.left_stick_x >0) && (timer.milliseconds()>DELAY)) {
            grabberRotation.setPosition(grabberRotation.getPosition() + DELTA*gamepad1.left_stick_x);
            timer.reset();
        } else if (gamepad1.left_stick_x<0 && (timer.milliseconds()>DELAY)) {
            grabberRotation.setPosition(grabberRotation.getPosition() - DELTA*gamepad1.left_stick_x);
            timer.reset();
        }
       /*double rotation = -gamepad2.left_stick_x;
        grabberRotation.setPosition(rotation);*/
    }

    //opens and closes grabber
    public void setArmRotation(){
        double rotation = -gamepad2.right_stick_y;
        armRotation.setPower(-rotation);
    }


    //sets slow speed for drive motors if dpad is pressed (forwards and backwards)
    public void setSlowApproach() {
        if (gamepad1.dpad_up) {
            leftFront.setPower(APPROACHSPEED);
            rightFront.setPower(APPROACHSPEED);
            leftRear.setPower(APPROACHSPEED);
            rightRear.setPower(APPROACHSPEED);
        } else if (gamepad1.dpad_down) {
            leftFront.setPower(-APPROACHSPEED);
            rightFront.setPower(-APPROACHSPEED);
            leftRear.setPower(-APPROACHSPEED);
            rightRear.setPower(-APPROACHSPEED);
        }
    }


    //sets power to freight lift motor determined from level
    public void setArmExtension() {
        double liftPower = trimPower(-gamepad2.right_stick_y);
        telemetry.addData("Lift Position: ", armWinch.getCurrentPosition());
        if (armWinch.getCurrentPosition() >= MAXTICKS && gamepad2.y) {
            armWinch.setPower(0);
        } else if (armWinch.getCurrentPosition() <= 0 && gamepad2.a) {
            armWinch.setPower(0);
        } else if (gamepad2.y) {
            armWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armWinch.setPower(liftPower);
        } else if (gamepad2.a) {
            armWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armWinch.setPower(-liftPower);
        } else {
            armWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            int targetPosition = armWinch.getCurrentPosition();
            armWinch.setTargetPosition(targetPosition);
        }
    }
    //sets grabber motor speed
    public void setGrabberHand() {
        if (gamepad2.left_bumper) {
            grabberHand.setPosition(OPENHAND);
        } else if (gamepad2.right_bumper) {
            grabberHand.setPosition(CLOSEDHAND);
        }
    }

    //sets grabber servo position
    public void initGrabberRotation() {
        grabberRotation.setPosition(GRABBERSERVO);
    }

    /*sets fixed camera position
    public void initCameraServo() {
        cameraServo.setPosition(CAMERASERVO);
    }*/

    //sets bucket servo to Safety position
    public void initGrabberServo() {
        grabberHand.setPosition(GRABBERHAND);
    }
}
