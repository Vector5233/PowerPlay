package org.firstinspires.ftc.teamcode.VectorCode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;

public class AgnesConstants {
    public static final double APPROACHSPEED = .3;
    public static final double THRESHOLD = .1;

    public static final double GRABBERINITSERVO = 1;
    public static final double RIGHTGRABBERINITHAND = .2;
    public static final double LEFTGRABBERINITHAND = .5;


    public static final double MAXTICKS = 1850;
    public static final double CLOSEDRIGHTHAND = 1;
    public static final double CLOSEDLEFTHAND = 0;
    public static final double OPENEDRIGHTHAND = .5;
    public static final double OPENEDLEFTHAND = .3;
    public static final double DELTA = .05;

    public static final double MINARM = -950;
    public static final double MAXARM = 200;
    public static final int ARMDELTA = -30;

    // RoadRunner Constants

    public static double MAX_VEL = 30;
    public static double MAX_ACCEL = 30;
    public static double MAX_ANG_VEL = Math.toRadians(360);
    public static double MAX_ANG_ACCEL = Math.toRadians(60);

    public static double kV = .011; // rpmToVelocity(MAX_RPM);
    public static double kA = .002;
    public static double kStatic = .115;

    public static double WHEEL_RADIUS = 2*23.5/19; // in
    public static double GEAR_RATIO = 1*1.0780; // value from straight test. output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 15.16; // in




    public static final double TICKS_PER_REV = 383.6;
    public static final double MAX_RPM = 435;

    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(4, 0, 0);
    public static PIDCoefficients HEADING_PID = new PIDCoefficients(4, 0, 0);

    public static double LATERAL_MULTIPLIER = 1*0.9022; //Strafe test reported y over final y

}

