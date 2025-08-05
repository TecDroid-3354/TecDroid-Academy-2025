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
    maximumMovementRange = 180.0, // In degrees // Needs to be changed
    minimumMovementRange = 0.0, // In degrees // Needs to be changed
)
