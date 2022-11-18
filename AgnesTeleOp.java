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
    Servo grabberLeftHand, grabberRotation, grabberRightHand;

    final double APPROACHSPEED = .3;
    final double THRESHOLD = .1;
    final double GRABBERINITSERVO = 1;
    final double RIGHTGRABBERINITHAND = .3;
    final double LEFTGRABBERINITHAND = .5;

    final double MAXTICKS = 1850;
    final double CLOSEDRIGHTHAND = 1;
    final double CLOSEDLEFTHAND = 0;
    final double OPENEDRIGHTHAND = .3;
    final double OPENEDLEFTHAND = .5;
    final double DELTA = .05;


    ElapsedTime timer;



    @Override
    public void init() {
        leftFront=hardwareMap.dcMotor.get("leftFront");
        leftRear=hardwareMap.dcMotor.get("leftRear");
        rightFront=hardwareMap.dcMotor.get("rightFront");
        rightRear=hardwareMap.dcMotor.get("rightRear");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        armWinch = (DcMotorEx) hardwareMap.dcMotor.get("armWinch");
        armWinch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armWinch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        armRotation = (DcMotorEx) hardwareMap.dcMotor.get("armRotation");
        armRotation.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        grabberRightHand = hardwareMap.servo.get("grabberRightHand");
        grabberLeftHand = hardwareMap.servo.get("grabberLeftHand");
        grabberRotation = hardwareMap.servo.get("grabberRotation");

        timer = new ElapsedTime();
        initGrabberServo();
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
        telemetry.addData("forward:", forward);
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


    //rotates grabber
    public void setGrabberRotation(){
        double current = grabberRotation.getPosition();
        telemetry.addData("current position:", current);
        if ((gamepad2.left_stick_x > 0)){
            grabberRotation.setPosition(current + DELTA * gamepad2.left_stick_x);
            telemetry.addLine("Right");  // needed for timing!  Do not remove
        } else if ((gamepad2.left_stick_x < 0) && (current > .33)){
            grabberRotation.setPosition(current + DELTA * gamepad2.left_stick_x);
            telemetry.addLine("Left");  // needed for timing!  Do not remove
        }
    }

    //rotates arm 180 - ONLY problem - if position is needed, user has to hold joystick down
    public void setArmRotation(){
        double rotation = -gamepad2.right_stick_x;
        armRotation.setPower(-rotation/10);
        //armRotation.setVelocity(rotation*50);
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
        if (armWinch.getCurrentPosition() >= MAXTICKS && liftPower > THRESHOLD) {
            armWinch.setPower(0);
        } else if (armWinch.getCurrentPosition() <= 0 && liftPower < -THRESHOLD) {
            armWinch.setPower(0);
        } else {
            armWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armWinch.setPower(liftPower);
        }
    }

    //sets grabber motor speed
    public void setGrabberHand() {
        if (gamepad2.left_bumper) {
            grabberRightHand.setPosition(OPENEDRIGHTHAND);
            grabberLeftHand.setPosition(OPENEDLEFTHAND);
        } else if (gamepad2.right_bumper) {
            grabberRightHand.setPosition(CLOSEDRIGHTHAND);
            grabberLeftHand.setPosition(CLOSEDLEFTHAND);
        }
    }
    // comment

    //sets grabber servo position
    public void initGrabberRotation() {
        grabberRotation.setPosition(GRABBERINITSERVO);
    }


    //sets grabber servo to initial position
    public void initGrabberServo() {
        grabberLeftHand.setPosition(LEFTGRABBERINITHAND);
        grabberRightHand.setPosition(RIGHTGRABBERINITHAND);
    }
}
