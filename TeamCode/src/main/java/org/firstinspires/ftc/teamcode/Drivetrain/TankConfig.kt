package org.firstinspires.ftc.teamcode.Drivetrain

import com.qualcomm.robotcore.hardware.DcMotorSimple

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
