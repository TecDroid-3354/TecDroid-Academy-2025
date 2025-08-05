package org.firstinspires.ftc.teamcode.arm.armJoint

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.Command
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.motors.Motor

class ArmJoint(private val config: ArmJointConfig, private val hardwareMap: HardwareMap) : SubsystemBase() {

    // Initializes the motor controller for the joint
    private lateinit var jointMotorController: Motor

    // Initializes the motor's encoder
    private val motorEncoder: Motor.Encoder = jointMotorController.encoder

    // Gets the motor's position
    private val motorPosition = motorEncoder.position

    private var targetPosition: Int = 0

    /**
     * Gets the current joint's position by applying the member's reduction
     */
    fun getJointPosition() = config.gearRatio.apply(motorPosition)

    /**
     * Returns the current position error of the motor in ticks
     */
    fun getJointPositionError() = if (targetPosition > getJointPosition()) targetPosition - getJointPosition() else getJointPosition() - targetPosition

    /**
     * Sets the desired position to the joint
     */
    fun setTargetPosition(ticks: Int): Command {
        val clampedPosition = MathUtils.clamp(ticks, config.bottomAngleLimit, config.upperAngleLimit)
        targetPosition = clampedPosition
        return InstantCommand({ jointMotorController.setTargetPosition(targetPosition) })
    }

    init {
        configureMotor()
    }

    /**
     * Configures the joint's motor by assigning it an Id, a position coefficient, a direction, a zero power behavior
     * and the desired run mode
     */
    private fun configureMotor() {

        jointMotorController.resetEncoder()

        jointMotorController = Motor(hardwareMap, config.motorId, config.motorType)

        jointMotorController.positionCoefficient = config.pController.p

        jointMotorController.motor.direction = config.motorDirection

        jointMotorController.setZeroPowerBehavior(config.zeroPowerBehavior)

        jointMotorController.setRunMode(config.runMode)
    }
}