package org.firstinspires.ftc.teamcode.Arm

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.motors.Motor
import org.firstinspires.ftc.ulateamcode.Arm.JointConfig

class ArmJoint(private val config: JointConfig, private val hardwareMap: HardwareMap) : SubsystemBase() {

    private lateinit var jointMotorController: Motor

    private val motorPosition: Double = jointMotorController.currentPosition.toDouble()

    fun getJointPosition() = config.gearRatio.apply(motorPosition)

    fun setTargetPosition(position: Double) {
        val clampedPosition = MathUtils.clamp(position, config.bottomAngleLimit, config.upperAngleLimit)
        jointMotorController.setTargetPosition(clampedPosition.toInt())
    }

    init {
        configureMotors()
    }

    private fun configureMotors() {

        jointMotorController = Motor(hardwareMap, config.motorId, config.motorType)

        jointMotorController.positionCoefficient = config.pController.p

        jointMotorController.motor.direction = config.motorDirection

        jointMotorController.setZeroPowerBehavior(config.zeroPowerBehavior)

        jointMotorController.setRunMode(config.runMode)
    }
}