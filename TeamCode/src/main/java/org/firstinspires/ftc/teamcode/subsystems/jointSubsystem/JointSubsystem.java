package org.firstinspires.ftc.teamcode.subsystems.jointSubsystem;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.jointSubsystem.misc.JointConstants;
import org.firstinspires.ftc.teamcode.subsystems.jointSubsystem.misc.JointMath;
import org.firstinspires.ftc.teamcode.util.MathUtils;

public class JointSubsystem extends SubsystemBase {
    // Declaring useful variables
    HardwareMap hardwareMap;
    Telemetry telemetry;
    public JointMath jointMath = new JointMath(
            JointConstants.MotorProperties.ticksFor90,
            JointConstants.MotorProperties.gearRatioReduction
    );

    private double targetAngle = 0.0;
    public int currentEncoderTicks = 0;


    // Declaring motors
    public MotorEx rightJointMotor, leftJointMotor;
    MotorGroup jointMotors;


    // Constructor //
    public JointSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        // Setting up motors and motor group
        rightJointMotor = new MotorEx(hardwareMap, JointConstants.Setup.rightJointMotorId);
        leftJointMotor = new MotorEx(hardwareMap, JointConstants.Setup.leftJointMotorId);

        jointMotors = new MotorGroup(rightJointMotor, leftJointMotor);
        motorSetup();
    }


    // Functional code //

    /*
    * The following function takes an angle in degrees and then, after calculating the difference
    * between target and current angles, it reaches for the desired target
    *
    * */
    public void setAngle(double angle) {
        if (targetAngle == angle) return;

        // Clamping the angle to ensure it is within the encoder's limits, preventing it from breaking
        double clampedAngle = MathUtils.clamp(
                angle,
                JointConstants.MeasureLimits.minAngleAllowed,
                JointConstants.MeasureLimits.maxAngleAllowed);

        // Assigning target angle's value; this then allows me to set a tolerance
        this.targetAngle = clampedAngle;
        //telemetry.addData("Joint target angle: ", clampedAngle);

        /*
         * The following lines of code return the difference between clamped angle (target)
         * and the current angle (first two lines). That calculation allows me to determine a
         * setpoint tick number that will end up being the one taken by the PID and, therefore, the
         * one to reach (last line)
         *
         * This translates into: current angle + distance needed to reach the angle = target angle
         *
         */
        double currentAngle = jointMath.toDegrees(rightJointMotor.getCurrentPosition());
        int deltaTicks = jointMath.toTicks(clampedAngle - currentAngle);
        int setpointTicks = rightJointMotor.getCurrentPosition() + deltaTicks;

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

        // todo: tune the PID coefficients
        PIDController pidf = new PIDController(0.003, 0.0, 0.0);
        pidf.setSetPoint(jointMath.toDegrees(currentEncoderTicks)); // Sets a desired setpoint for the motors to reach
        pidf.setTolerance(2.0); // Represents the position tolerance, in ticks

        while (!pidf.atSetPoint()) {
            // Returns the Throughbore's current reading
            currentEncoderTicks = rightJointMotor.getCurrentPosition();

            // The .calculate() method should be called on each iteration of the loop
            // It calculates the difference between two readings, and therefore, the distance
            // to reach
            //double output = pidf.calculate(currentEncoderTicks, setpointTicks);
            double output = pidf.calculate(jointMath.toDegrees(currentEncoderTicks), clampedAngle);


            // Clamp motor power output to a safe, achievable range
            double power = MathUtils.clamp(output, -1.0, 1.0);

            // Sets motor power for the motor group (which is the same doing it individually)
            jointMotors.set(power);

            /* Temporary telemetry to debug effectively
            telemetry.addData("Target angle", clampedAngle);
            telemetry.addData("Total distance to move", setpointTicks);
            telemetry.addData("Current Ticks", rightJointMotor.getCurrentPosition());
            telemetry.addData("Current degree", jointMath.toDegrees(rightJointMotor.getCurrentPosition()));
            telemetry.addData("Output", output);
            telemetry.addData("Power", power);
            telemetry.update();*/
        }

        stopMotors();
    }

    public boolean isAtTarget() {
        // Gets the current position of the motor based on encoder lecture
        double currentAngle = jointMath.toDegrees(rightJointMotor.getCurrentPosition());

        // A small tolerance. It is complementary with setAngle's while loop condition
        return 3 > Math.abs(currentAngle - targetAngle);
    }

    public void stopMotors() {
        rightJointMotor.stopMotor();
        leftJointMotor.stopMotor();
    }


    // Setup //
    private void motorSetup() {
        // Inverted motor
        rightJointMotor.setInverted(true);
        leftJointMotor.setInverted(false);

        // Setting motors dynamically through the jointMotors motorGroup
        jointMotors.stopAndResetEncoder();
        jointMotors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        jointMotors.setRunMode(Motor.RunMode.VelocityControl);
        jointMotors.setVeloCoefficients(1.0, 0.0, 0.0);

        // Setting up encoder direction
        rightJointMotor.encoder.setDirection(Motor.Direction.REVERSE);
    }
}

// Emilio Nájera, May—30th—2025