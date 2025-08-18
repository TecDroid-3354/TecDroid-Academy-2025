package org.firstinspires.ftc.teamcode.subsystems.sliderSubsystem;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.sliderSubsystem.misc.SliderConstants;
import org.firstinspires.ftc.teamcode.subsystems.sliderSubsystem.misc.SliderMath;
import org.firstinspires.ftc.teamcode.util.MathUtils;


public class SliderSubsystem extends SubsystemBase {
    // Declaring useful variables
    HardwareMap hardwareMap;
    Telemetry telemetry;
    public SliderMath sliderMath = new SliderMath(
            SliderConstants.MotorProperties.ticksPerRevolution,
            SliderConstants.MotorProperties.linearTravelPerRevolution,
            SliderConstants.MotorProperties.gearRatio
    );

    private double targetCm = 0.0; // This value determines when to stop the motors in isAtTarget()
    public double currentEncoderTicks = 0.0;


    // Declaring motors & sensors
    public MotorEx rightSliderMotor, leftSliderMotor;
    MotorGroup sliderMotors;


    // Constructor //
    public SliderSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        // Assigning values to useful variables
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        // Setting up motors
        // Reduction can be applied directly, with a 5.2:1 planetary box -> Motor.GoBILDA.RPM_1150
        // Or, with a 3.7:1 planetary box -> Motor.GoBILDA.RPM_1620
        // But I decided to apply it later on as part of the constants.
        rightSliderMotor = new MotorEx(hardwareMap, SliderConstants.Setup.rightSliderMotorId, Motor.GoBILDA.BARE);
        leftSliderMotor = new MotorEx(hardwareMap, SliderConstants.Setup.leftSliderMotorId, Motor.GoBILDA.BARE);

        sliderMotors = new MotorGroup(rightSliderMotor, leftSliderMotor);
        motorSetup();
    }


    // Functional code //

    /*
     * This function essentially takes a distance in centimeters and and calls the motor to go for it
     * It clamps the argument not to break the arm
     *
     * The way this method works:
     * A distance is passed as an argument. The slider will then reach the desired distance.
     * Example: if I pass 4, then the method will arrange speed and necessary travel distance in
     * order to reach 4 centimeters, no matter whether it starts at 1 or 10 centimeters; the end
     * goal is 4 centimeters and it will reach that position.
     *
     * It DOES NOT travel 4 centimeters of distance, but rather travels whatever is necessary
     * to reach 4 cm of extension.
     *
     * This is a "go-to-position" command, NOT a "move-by-distance" command.
     * It works with a similar idea to servomotors' turnToAngle method.
     *
     */

    public void setExtension(double distance) {
        // First of all, distance is clamped to ensure it is within safe limits
        double clampedDistance = MathUtils.clamp(
                distance,
                SliderConstants.MeasureLimits.minCmAllowed,
                SliderConstants.MeasureLimits.maxCmAllowed
        );

        // Assigning target centimeters' value
        this.targetCm = clampedDistance;

        /*
         * The following lines of code return the difference between clamped distance (target)
         * and the current centimeters (first two lines). That calculation allows me to determine a
         * setpoint tick number that will end up being the one taken by the PID and, therefore, the
         * one to reach (last line)
         *
         * This translates into: current position + distance needed to reach the target = target
         *
         */
        double currentCm = sliderMath.toCm(rightSliderMotor.getCurrentPosition());
        int deltaTicks = sliderMath.toTicks(clampedDistance - currentCm);
        int setpointTicks = rightSliderMotor.getCurrentPosition() + deltaTicks;

        /* Controller that will determine the difference between target and current motor readings
         *
         * The PID Controller basically responds to the difference between the current velocity
         * and the target velocity, and will add more or less power to the motor based on this
         * difference, which is also known as error
         *
         * The PIDFController Object takes 4 parameters:
         * @Proportional
         * @Integrative
         * @Derivative
         * @Feedforward: An additional gain to create a small offset
         *
         * For more information regarding PID control in SolversLib, refer to the following link:
         * https://docs.seattlesolvers.com/features/controllers#pid-control
         *
         */
        PIDFController pidf = new PIDFController(0.05, 0.0, 0.0, 0.0);
        pidf.setSetPoint(targetCm); // Sets a desired setpoint for the motors to reach
        pidf.setTolerance(3); // Represents the position tolerance, in cm


        while (!pidf.atSetPoint()) {
            // The number by which we divide the current encoder position is equal to the
            // velocity factor that will determine how fast the motors will reach the
            // desired position
            // TODO: ask why over 10?
            //double currentTicks = rightSliderMotor.getCurrentPosition() / 10.0;
            currentEncoderTicks = rightSliderMotor.getCurrentPosition();

            // The .calculate() method should be called on each iteration of the loop
            // It calculates the difference between two readings, and therefore, the distance
            // to reach. Output is negated since it was giving the opposite power direction.
            double output = -1 * pidf.calculate(sliderMath.toCm(currentEncoderTicks), clampedDistance);

            // Clamp motor power output to a safe, achievable range
            // There is a * 7 conversion factor to ensure motor output is readable by the motors
            // todo: try divinding by max ticks per output shaft
            double power = MathUtils.clamp(output, -1.0, 1.0);

            // Sets motor power for the motor group (which is the same doing it individually)
            sliderMotors.set(power);

            // Temporary telemetry to debug effectively
            telemetry.addData("Target", clampedDistance);
            //telemetry.addData("Total distance to move", setpointTicks);
            //telemetry.addData("Current Ticks", rightSliderMotor.getCurrentPosition());
            telemetry.addData("Position", sliderMath.toCm(rightSliderMotor.getCurrentPosition()));
            telemetry.addData("Error", Math.abs(sliderMath.toCm(currentEncoderTicks) - targetCm));
            telemetry.addData("Power", power);
            telemetry.update();
        }

        stopMotors();
    }

    public boolean isAtTarget() {
        // Gets the current position of the motor based on encoder lecture
        double currentCm = sliderMath.toCm(rightSliderMotor.getCurrentPosition());

        // A small tolerance. It is complementary with setExpansion's while loop condition
        return 5 > Math.abs(currentCm - targetCm);
    }

    public void stopMotors() {
        leftSliderMotor.stopMotor();
        rightSliderMotor.stopMotor();
    }


    // Setup code //
    private void motorSetup() {
        // Inverted motor
        rightSliderMotor.setInverted(true);
        leftSliderMotor.setInverted(false);

        // Setting motors dynamically through the sliderMotors motorGroup
        sliderMotors.stopAndResetEncoder();
        sliderMotors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        sliderMotors.setRunMode(Motor.RunMode.VelocityControl);
        sliderMotors.setVeloCoefficients(1.0, 0.0, 0.0);

        // Setting up encoder direction
        rightSliderMotor.encoder.setDirection(Motor.Direction.REVERSE);
    }
}

// Emilio Nájera — May-30th-2025
