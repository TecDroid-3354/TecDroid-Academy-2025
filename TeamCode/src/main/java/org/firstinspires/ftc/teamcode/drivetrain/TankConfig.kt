package org.firstinspires.ftc.teamcode.drivetrain

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.controller.PIDController
import com.seattlesolvers.solverslib.hardware.motors.Motor
import org.firstinspires.ftc.teamcode.utils.PIDCoefficients

data class TankConfig(
    val leftDriveDeviceName: String,
    val rightDriveDeviceName: String,
    val leftDirection:  DcMotorSimple.Direction,
    val rightDirection: DcMotorSimple.Direction
)

val tankConfig = TankConfig(
    leftDriveDeviceName = "left Motor",
    rightDriveDeviceName = "right Motor",
    leftDirection = DcMotorSimple.Direction.REVERSE,
    rightDirection = DcMotorSimple.Direction.FORWARD
)
