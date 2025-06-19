package org.firstinspires.ftc.teamcode.Arm

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.motors.Motor
import org.firstinspires.ftc.ulateamcode.Arm.ArmJointConfig

class ArmJoint(private val config: ArmJointConfig, private val hardwareMap: HardwareMap) : SubsystemBase() {

    private lateinit var jointMotorController: Motor

    private val motorEncoder: Motor.Encoder = jointMotorController.encoder

    private val motorPosition: Double = motorEncoder.position.toDouble()

    fun getJointPosition() = config.gearRatio.apply(motorPosition)

    fun setTargetPosition(position: Double) {
        val clampedPosition = MathUtils.clamp(position, config.bottomAngleLimit, config.upperAngleLimit)
        jointMotorController.setTargetPosition(clampedPosition.toInt())
    }

    init {
        motorEncoder.reset()
        configureMotor()
    }

    private fun configureMotor() {

        jointMotorController.resetEncoder()

        jointMotorController = Motor(hardwareMap, config.motorId, config.motorType)

        jointMotorController.positionCoefficient = config.pController.p

        jointMotorController.motor.direction = config.motorDirection

        jointMotorController.setZeroPowerBehavior(config.zeroPowerBehavior)

        jointMotorController.setRunMode(config.runMode)
    }
}