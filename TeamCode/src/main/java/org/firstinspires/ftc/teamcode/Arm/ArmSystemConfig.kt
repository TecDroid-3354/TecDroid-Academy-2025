package org.firstinspires.ftc.teamcode.Arm

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.teamcode.Intake.Intake
import org.firstinspires.ftc.teamcode.Intake.intakeConfig
import org.firstinspires.ftc.ulateamcode.Arm.JointConfig
import org.firstinspires.ftc.ulateamcode.Arm.bottomJointConfig
import org.firstinspires.ftc.ulateamcode.Arm.upperJointConfig

data class ArmSystemConfig(
    val bottomJointConfig: ArmJoint,
    val upperJointConfig: ArmJoint,
    val intake: Intake
)

val armSystemConfig = ArmSystemConfig(
    bottomJointConfig = ArmJoint(bottomJointConfig, hardwareMap),
    upperJointConfig = ArmJoint(upperJointConfig, hardwareMap),
    intake = Intake(intakeConfig, hardwareMap)
)



