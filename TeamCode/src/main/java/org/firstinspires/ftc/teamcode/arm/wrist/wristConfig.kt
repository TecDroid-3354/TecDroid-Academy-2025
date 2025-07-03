package org.firstinspires.ftc.teamcode.arm.wrist


data class WristConfig(

    val servoId : String,
    val isInverted : Boolean,
    val maximumMovementRange : Double,
    val minimumMovementRange :Double,
)

val wristConfig = WristConfig(
    servoId = "wristServo",
    isInverted = true,
    maximumMovementRange = 90.0,
    minimumMovementRange = 0.0,
)
