package org.firstinspires.ftc.teamcode.Arm

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.teamcode.Intake.Intake
import org.firstinspires.ftc.teamcode.Intake.IntakeConfig
import org.firstinspires.ftc.teamcode.Intake.intakeConfig
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



