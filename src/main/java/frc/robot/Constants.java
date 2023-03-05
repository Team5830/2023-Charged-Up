// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {
   
     public static final class ValueConstants {
       public static final int kLeftMotor1Port = 2;
       public static final int kLeftMotor2Port = 4;
       public static final int kRightMotor1Port = 3;
       public static final int kRightMotor2Port = 5;
       public static final int karmoter = 1;
       public static final int kwristmoter = 6;
       public static final int extemoroller = 7;
     }
    

    public static final class LevelPID {
        public static final double P = 0.08;
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double f = 0.0;
        public static final double LevelTolerance = 0.5; // Measured in degrees
        public static final double Level = 1;
    }

    public static final class TurnPID {
        public static final double P = 0.08;
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double f = 0.0;
        public static final double Tolerance = 2.0; // Measured in degrees
        public static final double TurnRateTolerance = 10; //Degrees per second
    }


    public static final class MovePID {
        public static final double lP = 1.0;
        public static final double lI = 0.0;
        public static final double lD = 0.0;
        public static final double lf = 0.0;
        public static final double lMaxAlignSpeed = 2; // Inches per second
        public static final double lAlignTolerance = 0.01; // Meters
        public static final double rP = 1.0;
        public static final double rI = 0.0;
        public static final double rD = 0.0;
        public static final double rf = 0.0;
        public static final double rMaxAlignSpeed = 2;
        public static final double rAlignTolerance = 0.01;
    }

    public static final class ArmPID {
        public static final double P = 0.01;
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double FF = 0.0;
        public static final double MaxOutput = .4;
        public static final double MinOutput = -.4;
        public static final float ForwardLimit = 300;
        public static final float ReverseLimit = 0;
        public static final double Tolerance = 2.0;
    }

    public static final class WristPID {
        public static final double P = 0.01; //.009;
        public static final double I = 0.0;    //.000003; 
        public static final double D = 0.0;    //.03;
        public static final double FF = 0;
        public static final double MaxOutput = .7;
        public static final double MinOutput = -.7;
        public static final float ForwardLimit = 220;
        public static final float ReverseLimit = 0;
        public static final double Tolerance = 2.0;
    }
    public static final class ExtendPID {
        public static final double P = 0.05;
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double FF =0.0;
        public static final double MaxOutput = .7;
        public static final double MinOutput = -.7;
        public static final float ForwardLimit = 5;
        public static final float ReverseLimit = -25;
        public static final double Tolerance = 2.0;
    }
    public static final class Position1{ // Retracted
        public static final double armAngle = 0.0;
        public static final double wristAngle = 0.0;
        public static final double extensionDistance = -5.0;
    }
    public static final class Position2{ // Pickup Position
        public static final double armAngle = 49.1;
        public static final double wristAngle = 101.0;
        public static final double extensionDistance = 2.1;
    }
    public static final class Position3{ // Middle Node
        public static final double armAngle = 230.0;
        public static final double wristAngle = 164.0;
        public static final double extensionDistance = 2.0;
    }
    public static final class Position4{ // High node
        public static final double armAngle = 278.0;
        public static final double wristAngle = 194.0;
        public static final double extensionDistance = 21.0;
    }
    public static final class Position5{ // Human player station pickup
        public static final double armAngle = 195.4;
        public static final double wristAngle = 164.0;
        public static final double extensionDistance = -5.4;
    }

    public static final class Position6{ // cone on the ground
        public static final double armAngle = 2.0;
        public static final double wristAngle = 220.0;
        public static final double extensionDistance = -74.0;
    }
}

