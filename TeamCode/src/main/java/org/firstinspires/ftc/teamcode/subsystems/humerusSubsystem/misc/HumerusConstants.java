package org.firstinspires.ftc.teamcode.subsystems.humerusSubsystem.misc;

public final class HumerusConstants {
    // Declaring setup constants, like IDs
    public static final String humerusRightServoId = "rightServo";
    public static final String humerusLeftServoId = "leftServo";


    // Declaring servo limits according to each subsystem
    public static final class MeasureLimits {
        // These constraints define the minimum and maximum permitted angle values
        public static final class rightHumerusServo{
            public static final double minAngle = 0.0;
            public static final double maxAngle = 180.0;
        }
        public static final class leftHumerusServo{
            public static final double minAngle = 0.0;
            public static final double maxAngle = 180.0;
        }
    }
}

// Emilio Nájera — June 25th, 2025