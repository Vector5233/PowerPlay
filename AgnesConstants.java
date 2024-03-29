package org.firstinspires.ftc.teamcode.VectorCode;

public class AgnesConstants {
    // teleop driving constants
    public static final double APPROACHSPEED = .3;
    public static final double THRESHOLD = .1;

    // preloaded cone delivery constants
    public static final double INIT_LEFT = 1;
    public static final double INIT_RIGHT = 0;
    public static final double DELIVER_LEFT = .5;
    public static final double DELIVER_RIGHT = .7;
    public static final double RECOVER_LEFT = .85;
    public static final double RECOVER_RIGHT = .4;

    //arm constants
    //public static final double MIN_ARM_ANG_TICKS = -50;
    //public static final double MAX_ARM_ANG_TICKS = 950;
    public static final double ARMDELTA = -.3;
    public static final int ARMDELTA_EXT = 10;
    public static final int MAX_EXT_TICKS = 1000;
    public static final int MIN_EXT_TICKS = 0;
    public static final double MINARMLENGTH = 12; //inches. measured from base of arm slide
    public static final double MAXARMLENGTH = 31; //inches. measured from base of arm slide
    public static final double MAXANGLE = 153;
    public static final double MINANGLE = 0;
    public static final double ARMROTATIONTICKSPERREV = 2786.2; //according to internet, for 60 rpm motor, 537.6 ticks per rev
    public static final int ARMEXTENSION = 495;
    public static final int ARMEXTENSIONPOLE =415;
    public static final double[] CONEDEGREE = {25,23,21,19,16};
    public static final int FIRSTPOLEDEGREE = 103;
    public static final int SECONDPOLEDEGREE = 107;
    public static final double MAXAUTOTICKS = 70;
    public static final double MINAUTOTICKS = -993;
    public static final double MAXTELEOPTICKS = 515;
    public static final double MINTELEOPTICKS = -691;
    public static final double MAXARMANGLEDEGREE = 140;
    public static final double MINARMANGLEDEGREE = 0;
    public static final double p = .004;
    public static final double i = .2;
    public static final double d = .0008;
    public static final double f = .04;
    public static final double TOL = .5;
    public static boolean AUTO = true; // used to initialize Arm
    public static boolean TELEOP = false;

    //grabber constants
    public static int GRABBERCLOSETIME = 570;
    public static int GRABBEROPENTIME = 100;
    public static double INITGRABBERHAND = .4;
    public static double OPENEDGRABBERHAND = .2;
    public static double CLOSEDGRABBERHAND = 0.02;
    public static double AUTOCLOSEDGRABBERHAND = 0.055;
    public static final double GRABBERINITSERVO = 1;
    // public static final double CLOSEDRIGHTHAND = 1;
   // public static final double CLOSEDLEFTHAND = 0;
   // public static final double OPENEDRIGHTHAND = .6;
    // public static final double OPENEDLEFTHAND = .2;
    public static final double DELTA = .05;



    // RoadRunner Constants

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

