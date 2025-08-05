package org.firstinspires.ftc.teamcode.arm.sliderJoint

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.seattlesolvers.solverslib.controller.PIDController
import com.seattlesolvers.solverslib.hardware.motors.Motor
import org.firstinspires.ftc.teamcode.utils.Reduction

data class SliderJointConfig(
    val motorId: String,
    val motorType: Motor.GoBILDA,
    val motorDirection: DcMotorSimple.Direction,
    val gearRatio: Reduction,
    val runMode: Motor.RunMode,
    val zeroPowerBehavior: Motor.ZeroPowerBehavior,
    val bottomJointLimit: Int,
    val upperJointLimit: Int,
    val pidVelocityController: PIDController,
    val pidController: PIDController
)

val sliderJointConfig = SliderJointConfig(
    motorId = "sliderJointMotor",
    motorType = Motor.GoBILDA.RPM_1150,
    motorDirection = DcMotorSimple.Direction.FORWARD,
    gearRatio = Reduction(0.0),
    runMode = Motor.RunMode.PositionControl,
    zeroPowerBehavior = Motor.ZeroPowerBehavior.BRAKE,
    bottomJointLimit = 10,
    upperJointLimit = 90,
    pidVelocityController = PIDController(0.0, 0.0, 0.0),
    pidController = PIDController(0.0, 0.0, 0.0)
)
