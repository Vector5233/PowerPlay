package org.firstinspires.ftc.teamcode.VectorCode;

import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.AUTO;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.INIT_LEFT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.INIT_RIGHT;
import static org.firstinspires.ftc.teamcode.VectorCode.AgnesConstants.TELEOP;

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
    Servo autoDeliveryLeft, autoDeliveryRight;

    final double APPROACHSPEED = AgnesConstants.APPROACHSPEED;
    final double THRESHOLD = AgnesConstants.THRESHOLD;
    final double RECOVER_LEFT = AgnesConstants.RECOVER_LEFT;
    final double RECOVER_RIGHT = AgnesConstants.RECOVER_RIGHT;
    double rotationTarget;


    final double ARMDELTA = AgnesConstants.ARMDELTA;
    final int ARMDELTA_EXT = AgnesConstants.ARMDELTA_EXT;


    int armExtension = 0;
    Grabber grabber;
    Arm arm;


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






        autoDeliveryLeft = hardwareMap.servo.get("autoDeliveryLeft");
        autoDeliveryRight = hardwareMap.servo.get("autoDeliveryRight");

        grabber = new Grabber();
        grabber.initialize(hardwareMap);
        grabber.setGrabberHandOpen();;

        timer = new ElapsedTime();
        initDeliveryServo();

        arm = new Arm();
        arm.initialize(hardwareMap, TELEOP); // initialize for teleop
        rotationTarget = 90;
        arm.setTarget(90);
    }

    @Override
    public void loop() {

       setDrive();

       setGrabberHand();

       setArmExtension();
       setArmRotation();
       setSlowApproach();

    }

    //@Override
    /*
    public void stop() {
        arm.updatePIDFController(.1, AgnesConstants.i, AgnesConstants.d, AgnesConstants.f);
        arm.setTarget(90);
        while (arm.isRotationBusy()){
            telemetry.addLine("shutting down");
            arm.setPower();
        }

    }*/

    //sets direction and power of all drive motors (frontLeft, frontRightt, backLeft, backRight)
    public void setDrive(){
        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x/2;
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




    public void setArmRotation(){

        rotationTarget =  (arm.getTarget() + ARMDELTA* gamepad2.right_stick_x);
        arm.setTarget(rotationTarget);
        arm.setPower();
        telemetry.addData("angle",arm.getAngle());
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


        armExtension = (int) (-gamepad2.left_stick_y*ARMDELTA_EXT + armExtension);

        armExtension = arm.setArmWinch(armExtension);
        telemetry.addData("armExtension: ", armExtension);
        telemetry.addLine("arm is moving up and down");  // needed for timing!  Do not remove

    }

    //sets grabber motor speed
   public void setGrabberHand() {
        if (gamepad2.left_bumper) {
            grabber.setGrabberHandOpen();
        } else if (gamepad2.right_bumper) {
            grabber.setGrabberHandClosed();
        }
    }


    //sets delivery servos to initial position
    public void initDeliveryServo() {
        autoDeliveryLeft.setPosition(RECOVER_LEFT);
        autoDeliveryRight.setPosition(RECOVER_RIGHT);
    }

}
