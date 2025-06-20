package org.firstinspires.ftc.teamcode.arm.ArmSystem

import org.firstinspires.ftc.teamcode.arm.Intake.IntakeConfig
import org.firstinspires.ftc.teamcode.arm.Intake.intakeConfig
import org.firstinspires.ftc.ulateamcode.Arm.ArmJointConfig
import org.firstinspires.ftc.ulateamcode.Arm.bottomJointConfig
import org.firstinspires.ftc.ulateamcode.Arm.upperJointConfig

data class ArmSystemConfig(
    val upperJointConfig: ArmJointConfig,
    val bottomJointConfig: ArmJointConfig,
    val intakeConfig: IntakeConfig
)

val armSystemConfig = ArmSystemConfig(
    upperJointConfig = upperJointConfig,
    bottomJointConfig = bottomJointConfig,
    intakeConfig = intakeConfig
)



