package org.firstinspires.ftc.teamcode.Drivetrain

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.configuration.annotations.MotorType
import com.seattlesolvers.solverslib.hardware.motors.Motor

data class TankConfig(
    val leftDriveDeviceName: String,
    val rightDriveDeviceName: String,
    val leftDirection:  DcMotorSimple.Direction,
    val rightDirection: DcMotorSimple.Direction,
    val motorType: Motor.GoBILDA
)

val tankConfig = TankConfig(
    leftDriveDeviceName = "left Motor",
    rightDriveDeviceName = "right Motor",
    leftDirection = DcMotorSimple.Direction.REVERSE,
    rightDirection = DcMotorSimple.Direction.FORWARD,
    motorType = Motor.GoBILDA.RPM_1150
)
