package org.firstinspires.ftc.teamcode.arm.gripper

data class GripperConfig(
    val servoId: String,
    val isInverted: Boolean,
    val minimumMovementRange: Double,
    val maximumMovementRange: Double
)

val gripperConfig = GripperConfig(
    servoId = "gripperServo",
    isInverted = true, // Check if needs inversion
    minimumMovementRange = 30.0, // In degrees
    maximumMovementRange = 100.0 // In degrees
)
