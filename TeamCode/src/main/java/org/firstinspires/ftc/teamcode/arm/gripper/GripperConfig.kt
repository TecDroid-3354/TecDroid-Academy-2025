package org.firstinspires.ftc.teamcode.arm.gripper

import com.qualcomm.robotcore.hardware.Servo
import com.seattlesolvers.solverslib.hardware.ServoEx

data class GripperConfig(
    val servoId: String,
    val minimumMovementRange: Double,
    val maximumMovementRange: Double
)

val gripperConfig = GripperConfig(
    servoId = "gripperServo",
    minimumMovementRange = 0.2,
    maximumMovementRange = 0.6
)
