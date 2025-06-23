package org.firstinspires.ftc.teamcode.arm.armJoint

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.motors.Motor
import org.firstinspires.ftc.ulateamcode.Arm.ArmJointConfig

class ArmJoint(private val config: ArmJointConfig, private val hardwareMap: HardwareMap) : SubsystemBase() {

    // Initializes the motor controller for the joint
    private lateinit var jointMotorController: Motor

    // Initializes the motor's encoder
    private val motorEncoder: Motor.Encoder = jointMotorController.encoder

    // Gets the motor's position
    private val motorPosition: Double = motorEncoder.position.toDouble()

    /**
     * Gets the current joint's position by applying the member's reduction
     */
    fun getJointPosition() = config.gearRatio.apply(motorPosition)

    /**
     * Sets the desired position to the joint
     */
    fun setTargetPosition(position: Double) {
        val clampedPosition = MathUtils.clamp(position, config.bottomAngleLimit, config.upperAngleLimit)
        jointMotorController.setTargetPosition(clampedPosition.toInt())
    }

    init {
        motorEncoder.reset()
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