package org.firstinspires.ftc.teamcode.Joaquin.ArmJoint;

import androidx.core.math.MathUtils;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.utils.Reduction;

public class ArmJoint extends SubsystemBase {

    // Initializes the motor controller for the joint
    private Motor jointMotorController;
    // Initializes the motor's encoder
    private Motor.Encoder motorEncoder;
    private final HardwareMap hardwareMap;
    private final String motorId;
    private final Reduction gearRatio;
    private final int tolerance;
    private final PController pController;
    private final int minimumAngleLimit;
    private final int maximumAngleLimit;
    private int targetPosition = 0;

    public ArmJoint(HardwareMap hardwareMap, ArmJointConfig config) {
        this.hardwareMap = hardwareMap;
        this.motorId = config.getMotorId();
        this.gearRatio = config.getGearRatio();
        this.tolerance = config.getTolerance();
        this.pController = config.getpController();
        this.minimumAngleLimit = config.getMinimumAngleLimit();
        this.maximumAngleLimit = config.getMaximumAngleLimit();
        configureMotor();
    }

    /**
     * Gets the current joint's position by applying the member's reduction
     */
    public double getJointPosition() {
        // Gets the motor's position
        int motorPosition = motorEncoder.getPosition();
        return gearRatio.apply(motorPosition);
    }

    /**
     * Returns the current position error of the motor in ticks
     */
    public double getJointPositionError() {
        return targetPosition > getJointPosition() ? targetPosition - getJointPosition() : getJointPosition() - targetPosition;
    }

    /**
     * Sets the desired position to the joint
     */
    public void setTargetPosition(int ticks) {
        int clampedPosition = MathUtils.clamp(Math.abs(ticks), minimumAngleLimit, maximumAngleLimit);
        targetPosition = clampedPosition;
        pController.setSetPoint(clampedPosition);

        while (!pController.atSetPoint()) {
            int output = (int) pController.calculate(getJointPosition());
            jointMotorController.motor.setPower(output);
        }
        jointMotorController.motor.setPower(0.0);

    }

    /**
     * Configures the joint's motor by assigning it an Id, a position coefficient, a direction, a zero power behavior
     * and the desired run mode
     */
    private void configureMotor() {

        jointMotorController = new Motor(hardwareMap, motorId, Motor.GoBILDA.RPM_312);
        jointMotorController.motor.setDirection(DcMotorSimple.Direction.FORWARD);
        jointMotorController.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        jointMotorController.setRunMode(Motor.RunMode.PositionControl);
        jointMotorController.setPositionTolerance(tolerance);
        jointMotorController.resetEncoder();

        motorEncoder = jointMotorController.encoder;
    }
}