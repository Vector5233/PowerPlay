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
    Servo grabberLeftHand, grabberRotation, grabberRightHand, autoDeliveryLeft, autoDeliveryRight;

    final double APPROACHSPEED = AgnesConstants.APPROACHSPEED;
    final double THRESHOLD = AgnesConstants.THRESHOLD;
    final double GRABBERINITSERVO = AgnesConstants.GRABBERINITSERVO;
    final double RIGHTGRABBERINITHAND = AgnesConstants.RIGHTGRABBERINITHAND;
    final double LEFTGRABBERINITHAND = AgnesConstants.LEFTGRABBERINITHAND;
    final double RECOVER_LEFT = AgnesConstants.RECOVER_LEFT;
    final double RECOVER_RIGHT = AgnesConstants.RECOVER_RIGHT;

    final double MAXTICKS = AgnesConstants.MAXTICKS;
    final double MINTICKS = AgnesConstants.MINTICKS;
    final double CLOSEDRIGHTHAND = AgnesConstants.CLOSEDRIGHTHAND;
    final double CLOSEDLEFTHAND = AgnesConstants.CLOSEDLEFTHAND;
    final double OPENEDRIGHTHAND = AgnesConstants.OPENEDRIGHTHAND;
    final double OPENEDLEFTHAND = AgnesConstants.OPENEDLEFTHAND;
    final double DELTA = AgnesConstants.DELTA;

    final double MINARM = -950;
    final double MAXARM = 200;
    final int ARMDELTA = AgnesConstants.ARMDELTA;
    final int ARMDELTA_EXT = 10;

    int armExtension;

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
        armExtension = 0;

        armRotation = (DcMotorEx) hardwareMap.dcMotor.get("armRotation");
        armRotation.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        autoDeliveryLeft = hardwareMap.servo.get("autoDeliveryLeft");
        autoDeliveryRight = hardwareMap.servo.get("autoDeliveryRight");
        grabberLeftHand = hardwareMap.servo.get("grabberLeftHand");
        grabberRotation = hardwareMap.servo.get("grabberRotation");



        grabberRightHand = hardwareMap.servo.get("grabberRightHand");
        grabberLeftHand = hardwareMap.servo.get("grabberLeftHand");
        grabberRotation = hardwareMap.servo.get("grabberRotation");

        timer = new ElapsedTime();
        initGrabberServo();
        initGrabberRotation();
        initDeliveryServo();
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

    //sets direction and power of all drive motors (frontLeft, frontRightt, backLeft, backRight)
    public void setDrive(){
        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x/2;
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
    //need to test non commented out stuff as of 11.16.22
    public void setArmRotation(){
        /*double rotation = -gamepad2.right_stick_x;
        armRotation.setPower(-rotation/10);*/
        int current = armRotation.getCurrentPosition();
        telemetry.addData("current position:", current);
        if ((gamepad2.right_stick_x > 0)){
            armRotation.setTargetPosition(Math.round(current + ARMDELTA * gamepad2.right_stick_x));
            armRotation.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            armRotation.setPower(.95);
            telemetry.addLine("Right");  // needed for timing!  Do not remove
        } else if ((gamepad2.right_stick_x < 0) ){
            armRotation.setTargetPosition(Math.round(current + ARMDELTA * gamepad2.right_stick_x));
            armRotation.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            armRotation.setPower(.95);
            telemetry.addLine("Left");  // needed for timing!  Do not remove
        }

    }


    //sets slow speed for drive motors if dpad iis pressed (forwards and backwards)
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
        } else if (gamepad1.dpad_right) {
            leftFront.setPower(APPROACHSPEED*3);
            rightFront.setPower(-APPROACHSPEED*3);
            leftRear.setPower(-APPROACHSPEED*3);
            rightRear.setPower(APPROACHSPEED*3);
        } else if (gamepad1.dpad_left) {
            leftFront.setPower(-APPROACHSPEED*3);
            rightFront.setPower(APPROACHSPEED*3);
            leftRear.setPower(APPROACHSPEED*3);
            rightRear.setPower(-APPROACHSPEED*3);
        }
    }


    //sets power to freight lift motor determined from level
    public void setArmExtension() {
        /*double liftPower = trimPower(-gamepad2.right_stick_y/2);
        telemetry.addData("Lift Position: ", armWinch.getCurrentPosition());
        if (armWinch.getCurrentPosition() >= MAXTICKS && liftPower > THRESHOLD) {
            armWinch.setPower(0);
        } else if (armWinch.getCurrentPosition() <= 0 && liftPower < -THRESHOLD) {
            armWinch.setPower(0);
        } else {
            armWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armWinch.setPower(liftPower);
        }*/
        double liftPower = .3;
        if (gamepad2.a && armExtension >MINTICKS){
            armExtension = armExtension - ARMDELTA_EXT;
        } else if (gamepad2.y && armExtension < MAXTICKS){
            armExtension = armExtension + ARMDELTA_EXT;
        }
        armWinch.setTargetPosition(armExtension);
        armWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armWinch.setPower(liftPower);
        telemetry.addData("armExtension: ", armExtension);
        telemetry.addLine("arm is moving up and down");  // needed for timing!  Do not remove

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
        grabberLeftHand.setPosition(OPENEDLEFTHAND);
        grabberRightHand.setPosition(OPENEDRIGHTHAND);
    }

    //sets delivery servos to initial position
    public void initDeliveryServo() {
        autoDeliveryLeft.setPosition(RECOVER_LEFT);
        autoDeliveryRight.setPosition(RECOVER_RIGHT);
    }

}
