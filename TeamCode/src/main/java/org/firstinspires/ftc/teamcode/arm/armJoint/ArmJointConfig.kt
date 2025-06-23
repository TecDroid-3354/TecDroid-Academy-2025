package org.firstinspires.ftc.ulateamcode.Arm

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.seattlesolvers.solverslib.controller.PIDController
import org.firstinspires.ftc.teamcode.utils.Reduction
import com.seattlesolvers.solverslib.hardware.motors.Motor

data class ArmJointConfig(
    val motorId: String,
    val motorType: Motor.GoBILDA,
    val motorDirection: DcMotorSimple.Direction,
    val gearRatio: Reduction,
    val bottomAngleLimit : Double,
    val upperAngleLimit : Double,
    val pController : PIDController,
    val zeroPowerBehavior : Motor.ZeroPowerBehavior,
    val runMode: Motor.RunMode
)

/**
 * Still need to configure joint with the correct motor type, direction, reduction, bottom and superior angle
 * limits and the P controller.
 */
val bottomJointConfig = ArmJointConfig(
    motorId = "bottomLinkMotor",
    motorType = Motor.GoBILDA.RPM_1150,
    motorDirection = DcMotorSimple.Direction.FORWARD,
    gearRatio = Reduction(0.0),
    bottomAngleLimit = 0.0,
    upperAngleLimit = 0.0,
    pController = PIDController(0.0, 0.0, 0.0),
    zeroPowerBehavior = Motor.ZeroPowerBehavior.BRAKE,
    runMode = Motor.RunMode.PositionControl
)

/**
 * Still need to configure joint with the correct motor type, direction, reduction, bottom and superior angle
 * limits and the P controller.
 */
val upperJointConfig = ArmJointConfig(
    motorId = "upperLinkMotor",
    motorType = Motor.GoBILDA.RPM_1150,
    motorDirection = DcMotorSimple.Direction.REVERSE,
    gearRatio = Reduction(0.0),
    bottomAngleLimit = 0.0,
    upperAngleLimit = 0.0,
    pController = PIDController(0.0, 0.0, 0.0),
    zeroPowerBehavior = Motor.ZeroPowerBehavior.BRAKE,
    runMode = Motor.RunMode.PositionControl
)
