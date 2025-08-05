package org.firstinspires.ftc.teamcode.arm.gripper

data class GripperConfig(
    val servoId: String,
    val isInverted: Boolean,
    val minimumAngleRange: Double,
    val maximumAngleRange: Double
)

val gripperConfig = GripperConfig(
    servoId = "gripperServo",
    isInverted = true, // Check if needs inversion
    minimumAngleRange = 0.0, // In degrees
    maximumAngleRange = 180.0 // In degrees
)
