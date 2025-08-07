package org.firstinspires.ftc.teamcode.ArmJoint;

import androidx.core.math.MathUtils;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.utils.Reduction;

public class ArmJoint extends SubsystemBase {
    private final HardwareMap hardwareMap;

    // Initializes the motor controller for the joint
    private Motor jointMotorController;

    // Initializes the motor's encoder
    private Motor.Encoder motorEncoder;

    // Gets the motor's position
    private int motorPosition;

    private int targetPosition = 0;

    private final Reduction gearRatio;

    public ArmJoint(HardwareMap hardwareMap, Reduction gearRatio) {
        this.gearRatio = gearRatio;
        this.hardwareMap = hardwareMap;
        configureMotor();
    }

    /**
     * Gets the current joint's position by applying the member's reduction
     */
    public double getJointPosition() {
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
    public void setTargetPosition(int ticks, int tolerance) {
        jointMotorController.setPositionTolerance(tolerance);
        int clampedPosition = MathUtils.clamp(ticks, 0, 2000);
        targetPosition = clampedPosition;
        jointMotorController.setTargetPosition(targetPosition);
    }

    /**
     * Configures the joint's motor by assigning it an Id, a position coefficient, a direction, a zero power behavior
     * and the desired run mode
     */
    private void configureMotor() {
        jointMotorController = new Motor(hardwareMap, "Motor", Motor.GoBILDA.RPM_312);
        jointMotorController.resetEncoder();

        jointMotorController.setPositionCoefficient(0.1);
        jointMotorController.motor.setDirection(DcMotorSimple.Direction.FORWARD);
        jointMotorController.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        jointMotorController.setRunMode(Motor.RunMode.PositionControl);

        motorEncoder = jointMotorController.encoder;
        motorPosition = motorEncoder.getPosition();
    }
}