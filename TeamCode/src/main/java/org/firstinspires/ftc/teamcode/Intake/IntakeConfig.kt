package org.firstinspires.ftc.teamcode.Intake

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.seattlesolvers.solverslib.controller.PIDFController

data class IntakeConfig (
    val servoId: String,
    val servoDirection: DcMotorSimple.Direction,
)

val intakeConfig = IntakeConfig (
    servoId = "gripperServo", // Check the set name in the driver station
    servoDirection = DcMotorSimple.Direction.FORWARD, //Check the actual direction
)