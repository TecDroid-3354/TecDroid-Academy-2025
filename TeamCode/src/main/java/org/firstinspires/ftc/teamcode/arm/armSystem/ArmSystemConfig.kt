package org.firstinspires.ftc.teamcode.arm.armSystem

import org.firstinspires.ftc.teamcode.arm.intake.IntakeConfig
import org.firstinspires.ftc.teamcode.arm.intake.intakeConfig
import org.firstinspires.ftc.ulateamcode.Arm.ArmJointConfig
import org.firstinspires.ftc.ulateamcode.Arm.bottomJointConfig
import org.firstinspires.ftc.ulateamcode.Arm.upperJointConfig

data class ArmSystemConfig(
    val upperJointConfig: ArmJointConfig,
    val bottomJointConfig: ArmJointConfig,
    val intakeConfig: IntakeConfig
)

/**
 * Check the every configuration file to corroborate that the information put there is actually correct
 */
val armSystemConfig = ArmSystemConfig(
    upperJointConfig = upperJointConfig,
    bottomJointConfig = bottomJointConfig,
    intakeConfig = intakeConfig
)



