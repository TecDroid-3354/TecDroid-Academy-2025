package org.firstinspires.ftc.teamcode.subsystems.sliderSubsystem.misc;

public final class SliderConstants {

    // Declaring Setup constants, like IDs
    public static final class Setup {
        public static final String rightSliderMotorId = "sliderRightMotor";
        public static final String leftSliderMotorId = "sliderLeftMotor";
    }

    public static final class MotorProperties {
        // This first number returns how many ticks are there in a motor revolution.
        // The second number represents how much distance the elevator travels per revolution of the motor
        // Both of these numbers allow me to convert from ticks to linear velocity (centimeters)

        // The following value was gotten from https://www.gobilda.com/modern-robotics-12vdc-motor/
        public static final int ticksPerRevolution = 28;

        // This value is gotten from the circumference of the pulley attached to the motor
        // For example, if we had a pulley with a diameter of 5cm, then the circumference would be 5 * π,
        // which is about 15.7. This means that, for each revolution of the pulley, the belt moves 15.7 cm
        public static final double linearTravelPerRevolution = Math.PI * 3.82;

        // Represents the velocity ratio, that's why it is not represented in the form 1 / 5.2
        public static final double gearRatio = 5.2;
    }

    // Declaring limits to avoid breaking and cooking the motor
    public static final class MeasureLimits {
        // minCmAllowed defines the maximum ticks the encoder can take at the smallest distance
        // allowed. This is be the equivalent to the slider having travelled 0 cm, which is equal
        // to the elevator being at 0 centimeters from the bottom (starting position)
        public static final double minCmAllowed = 0.0;

        // maxCmAllowed defines the maximum ticks the encoder can take at the greatest distance
        // allowed. This is be the equivalent to the slider having travelled 100 cm from the bottom,
        // which is the max distance it can travel without breaking (top limit)
        public static final double maxCmAllowed = 300.0;
    }
}

// Emilio Nájera — May-30th-2025