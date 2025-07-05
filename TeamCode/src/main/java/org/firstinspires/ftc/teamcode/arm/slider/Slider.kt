package org.firstinspires.ftc.teamcode.arm.slider

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.controller.PIDController
import com.seattlesolvers.solverslib.hardware.motors.Motor

class Slider (private val config: SliderConfig, private val hardwareMap: HardwareMap): SubsystemBase() {

    private lateinit var sliderMotorController: Motor

    private var pidController = PIDController(config.pidController.p, config.pidController.i, config.pidController.d)

    init {
        require(config.upperSliderLimit > config.bottomSliderLimit && config.bottomSliderLimit > 0) {
            "Upper limit must be greater than the bottom one, the last one must be greater than zero"
        }
        configureMotor()
    }

    fun getPosition() = config.gearRatio.apply(sliderMotorController.currentPosition.toDouble())

    fun getPositionError() = pidController.positionError

    fun setPosition(position: Double) {
        val clampedPosition = MathUtils.clamp(position.toInt(), config.bottomSliderLimit, config.upperSliderLimit)
        sliderMotorController.setTargetPosition(clampedPosition)

        while (sliderMotorController.atTargetPosition().not()) {
            val output = pidController.calculate(
                sliderMotorController.currentPosition.toDouble()
            )
            sliderMotorController.set(output)
        }
        sliderMotorController.stopMotor()
    }

    private fun configureMotor() {

        pidController.setTolerance(10.0)

        sliderMotorController = Motor(hardwareMap, config.motorId, config.motorType)

        sliderMotorController.motor.direction = config.motorDirection

        sliderMotorController.setZeroPowerBehavior(config.zeroPowerBehavior)

        sliderMotorController.setRunMode(config.runMode)

        sliderMotorController.setVeloCoefficients(config.pidVelocityController.p, config.pidVelocityController.i, config.pidVelocityController.d)

        sliderMotorController.stopAndResetEncoder()
    }
}