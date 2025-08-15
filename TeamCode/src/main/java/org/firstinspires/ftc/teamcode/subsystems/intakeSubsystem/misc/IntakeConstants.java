package org.firstinspires.ftc.teamcode.subsystems.intakeSubsystem.misc;

public final class IntakeConstants {
    // Declaring setup constants, like IDs
    public static final class Setup {
        public static final String gripperServoId = "gripperServo";
        public static final String wristRotatorServoId = "wristRotatorServo";
    }

    // Declaring servo limits according to each part of the subsystem
    public static final class MeasureLimits {
        // These constraints define the minimum and maximum permitted angle values for the gripper
        public static final double gripperMinAngle = 1.0;
        public static final double gripperMaxAngle = 89.0;

        // These constraints define the minimum and maximum permitted angle values for the wrist rotator
        public static final double wristRotatorMinAngle = 1.0;
        public static final double wristRotatorMaxAngle = 89.0;
    }
}

// Emilio Nájera — June 27th, 2025