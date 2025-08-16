package org.firstinspires.ftc.teamcode.subsystems.jointSubsystem.misc;

public final class JointConstants {

    // Declaring Setup constants, like IDs
    public static final class Setup {
        public static final String rightJointMotorId = "jointRightMotor";
        public static final String leftJointMotorId = "jointLeftMotor";
    }

    public static final class MotorProperties {
        // This number returns how many ticks are necessary to reach 180 degrees in a motor encoder
        // Having this number allows me to make several calculations in order to reach a concrete
        // angle (JointMath.java)
        public static final int ticksPerRevolution = 28;

        // The following number defines the amount of ticks needed to reach 90 degrees as per
        // Throughbore encoder lectures
        public static final int ticksFor90 = 2160;

        // This number represents the velocity ratio. It comes from a result of multiplying
        // all of the gearboxes' factors, including 5, 3 & 3 = 5 * 3 * 3 = 15
        public static final double gearRatioReduction = 15.0;
    }

    // Declaring limits to avoid breaking and cooking the motor
    public static final class MeasureLimits {
        // minAngleAllowed defines the minimum ticks the encoder can take at the lowest angle permitted.
        // This is be the equivalent to the joint being at 0 degrees
        public static final double minAngleAllowed = 0.0;

        // maxAngleAllowed defines the maximum ticks the encoder can take at the greatest angle permitted.
        // This is be the equivalent to the joint being at 90 degrees
        public static final double maxAngleAllowed = 90.0;
    }
}

// Emilio Nájera — May-30th-2025