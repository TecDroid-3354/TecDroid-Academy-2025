package org.firstinspires.ftc.teamcode.arm.slider

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.seattlesolvers.solverslib.controller.PIDController
import com.seattlesolvers.solverslib.hardware.motors.Motor
import org.firstinspires.ftc.teamcode.utils.Reduction
import org.firstinspires.ftc.ulateamcode.Arm.upperJointConfig

data class SliderConfig (
    val motorId: String,
    val motorType: Motor.GoBILDA,
    val motorDirection: DcMotorSimple.Direction,
    val runMode: Motor.RunMode,
    val zeroPowerBehavior: Motor.ZeroPowerBehavior,
    val gearRatio: Reduction,
    val bottomSliderLimit: Int,
    val upperSliderLimit: Int,
    val pidVelocityController: PIDController,
    val pidController: PIDController
)

val sliderConfig = SliderConfig(
    motorId = "sliderMotor",
    motorType = Motor.GoBILDA.RPM_30,
    motorDirection = DcMotorSimple.Direction.FORWARD,
    runMode = Motor.RunMode.PositionControl,
    zeroPowerBehavior = Motor.ZeroPowerBehavior.BRAKE,
    gearRatio = Reduction(10.0),
    bottomSliderLimit = 10,
    upperSliderLimit = 90,
    pidVelocityController = PIDController(1.0, 0.0, 0.0),
    pidController = PIDController(0.0, 0.0, 0.0)
)
