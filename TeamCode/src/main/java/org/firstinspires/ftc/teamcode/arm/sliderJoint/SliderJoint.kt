package org.firstinspires.ftc.teamcode.arm.sliderJoint

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.controller.PIDController
import com.seattlesolvers.solverslib.hardware.motors.Motor

class SliderJoint(private val config: SliderJointConfig, private val hardwareMap: HardwareMap): SubsystemBase() {

    private lateinit var sliderJointMotorController: Motor

    private val pidController = PIDController(config.pidController.p, config.pidController.i, config.pidController.d)

    init {
        require(config.upperJointLimit > config.bottomJointLimit && config.bottomJointLimit > 0) {
            "Upper limit must be greater than the bottom one, the last one must be greater than zero"
        }
        configureMotor()
    }

    fun getPosition() = config.gearRatio.apply(sliderJointMotorController.currentPosition.toDouble())

    /**
     * Returns a slider Joint position by calculating an output with a PID controller. The position must be given
     * in ticks
     */
    fun setPosition(position: Double) {

        val clampedPosition = MathUtils.clamp(position.toInt(), config.bottomJointLimit, config.upperJointLimit)
        sliderJointMotorController.setTargetPosition(clampedPosition)

        while (sliderJointMotorController.atTargetPosition().not()) {
            val output = pidController.calculate(
                sliderJointMotorController.currentPosition.toDouble()
            )

            sliderJointMotorController.set(output)
        }
        sliderJointMotorController.stopMotor()
    }

    private fun configureMotor() {

        pidController.setTolerance(10.0)

        sliderJointMotorController = Motor(hardwareMap, config.motorId, config.motorType)

        sliderJointMotorController.motor.direction = config.motorDirection

        sliderJointMotorController.setRunMode(config.runMode)

        sliderJointMotorController.setZeroPowerBehavior(config.zeroPowerBehavior)

        sliderJointMotorController.setVeloCoefficients(config.pidVelocityController.p, config.pidVelocityController.i, config.pidVelocityController.d)

        sliderJointMotorController.stopAndResetEncoder()

    }
}