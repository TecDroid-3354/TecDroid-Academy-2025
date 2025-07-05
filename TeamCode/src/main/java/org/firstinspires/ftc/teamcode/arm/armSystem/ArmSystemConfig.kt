package org.firstinspires.ftc.teamcode.arm.armSystem

import org.firstinspires.ftc.teamcode.arm.gripper.GripperConfig
import org.firstinspires.ftc.teamcode.arm.gripper.gripperConfig
import org.firstinspires.ftc.teamcode.arm.slider.SliderConfig
import org.firstinspires.ftc.teamcode.arm.slider.sliderConfig
import org.firstinspires.ftc.teamcode.arm.sliderJoint.SliderJointConfig
import org.firstinspires.ftc.teamcode.arm.sliderJoint.sliderJointConfig
import org.firstinspires.ftc.teamcode.arm.wrist.WristConfig
import org.firstinspires.ftc.teamcode.arm.wrist.wristConfig

data class ArmSystemConfig(
    val gripperConfig: GripperConfig,
    val sliderConfig: SliderConfig,
    val sliderJointConfig: SliderJointConfig,
    val wristConfig: WristConfig
)

/**
 * Check the every configuration file to corroborate that the information put there is actually correct
 */
val armSystemConfig = ArmSystemConfig(
    gripperConfig = gripperConfig,
    sliderConfig = sliderConfig,
    sliderJointConfig = sliderJointConfig,
    wristConfig = wristConfig
)



