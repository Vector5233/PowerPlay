package org.firstinspires.ftc.teamcode.VectorCode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;

public class AgnesConstants {
    public static final double APPROACHSPEED = .3;
    public static final double THRESHOLD = .1;

    public static final double GRABBERINITSERVO = 1;
    public static final double RIGHTGRABBERINITHAND = .2;
    public static final double LEFTGRABBERINITHAND = .5;
    public static final double REST_LEFT = 1;
    public static final double REST_RIGHT = 0;
    public static final double DELIVER_LEFT = .5;
    public static final double DELIVER_RIGHT = .7;
    public static final double RECOVER_LEFT = .7;
    public static final double RECOVER_RIGHT = .5;

    public static final double MAXTICKS = 1000;
    public static final double MINTICKS = 0;
    public static final double CLOSEDRIGHTHAND = 1;
    public static final double CLOSEDLEFTHAND = 0;
    public static final double OPENEDRIGHTHAND = .6;
    public static final double OPENEDLEFTHAND = .2;
    public static final double DELTA = .05;

    public static final double MINARM = -50;
    public static final double MAXARM = 950;
    public static final int ARMDELTA = -15;

    // RoadRunner ConstantsS

    public static double TICKS_PER_REV = 384.5;
    public static double MAX_RPM = 435;
    public static double MAX_VEL = 30;
    public static double MAX_ACCEL = 30;
    public static double MAX_ANG_VEL = Math.toRadians(360);
    public static double MAX_ANG_ACCEL = Math.toRadians(60);

    public static double kV = .011; // rpmToVelocity(MAX_RPM);
    public static double kA = .002;
    public static double kStatic = .115;

    public static double WHEEL_RADIUS = 2*23.5/19; // in
    public static double GEAR_RATIO = .7951; // value from straight test. output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 15.16; // in

    public static double TRANS_KP = 4.0;  // values for SampleMechanumDrive TRANSLATIONAL_PID, line 61
    public static double TRANS_KI = 0.0;
    public static double TRANS_KD = 0.0;
    public static double HEADING_KP = 4.0; // values for SampleMechanumDrive HEADING_PID, line 62
    public static double HEADING_KI = 0.0;
    public static double HEADING_KD = 0.0;

    public static double LATERAL_MULTIPLIER = 1.082; //Strafe test reported y over final y - value for SampleMechanumDrive

}

