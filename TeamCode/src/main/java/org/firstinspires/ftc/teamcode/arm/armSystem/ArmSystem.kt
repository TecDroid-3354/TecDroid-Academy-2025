package org.firstinspires.ftc.teamcode.arm.armSystem

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.Command
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.SequentialCommandGroup
import com.seattlesolvers.solverslib.command.WaitUntilCommand
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.SLIDER
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.JOINT
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.WRIST
import org.firstinspires.ftc.teamcode.arm.armSystem.GripperPosition.CLOSED
import org.firstinspires.ftc.teamcode.arm.armSystem.GripperPosition.OPENED
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.WJS
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.SWJ
import org.firstinspires.ftc.teamcode.arm.gripper.Gripper
import org.firstinspires.ftc.teamcode.arm.slider.Slider
import org.firstinspires.ftc.teamcode.arm.sliderJoint.SliderJoint
import org.firstinspires.ftc.teamcode.arm.wrist.Wrist
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.BasketState
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.IntakeState
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.High


enum class ArmMember {
    SLIDER, JOINT, WRIST;
}

enum class ArmStates {
    BasketState, IntakeState
}

enum class BasketStates {
    High, Low
}

enum class GripperPosition {
    OPENED, CLOSED;
}

data class ArmOrder(
    private val first: ArmMember,
    private val second: ArmMember,
    private val third: ArmMember
) {
    fun getFirst() = first
    fun getSecond() = second
    fun getThird() = third
}

enum class ArmOrders(val order: ArmOrder) {
    SJW(ArmOrder(SLIDER, JOINT, WRIST)),
    JWS(ArmOrder(JOINT, WRIST, SLIDER)),
    WJS(ArmOrder(WRIST, JOINT, SLIDER)),
    SWJ(ArmOrder(SLIDER, WRIST, JOINT)),
    JSW(ArmOrder(JOINT, SLIDER, WRIST)),
    WSJ(ArmOrder(WRIST, SLIDER, JOINT)),
}

data class ArmPose(
    val sliderDisplacement: Double,
    val jointAngle: Double,
    val wristAngle: Double,
    val gripperPosition: GripperPosition,
    val order: ArmOrders
)

enum class ArmPoses(val pose: ArmPose) {
    // Check the actual position of every subsystem in every arm position
    IntakePose(ArmPose(
        sliderDisplacement = 0.0,
        jointAngle = 0.0,
        wristAngle = 0.0,
        gripperPosition = OPENED,
        order = WJS,
    )),

    HighBasket(ArmPose(
        sliderDisplacement = 0.0,
        jointAngle = 0.0,
        wristAngle = 0.0,
        gripperPosition = OPENED,
        order = SWJ,
    )),

    LowBasket(ArmPose(
        sliderDisplacement = 0.0,
        jointAngle = 0.0,
        wristAngle = 0.0,
        gripperPosition = OPENED,
        order = SWJ,
    )),
}

class Arm(config: ArmSystemConfig, hardwareMap: HardwareMap) {

    private val slider = Slider(config.sliderConfig, hardwareMap)
    private val sliderJoint = SliderJoint(config.sliderJointConfig, hardwareMap)
    private val wrist = Wrist(config.wristConfig, hardwareMap)
    private val gripper = Gripper(config.gripperConfig, hardwareMap)

    var currentArmState: ArmStates = IntakeState

    val changeState = { currentArmState = if (currentArmState == IntakeState) BasketState else IntakeState }

    var currentBasketState = High

    private fun Gripper.setGripperPositionCommand(position: GripperPosition): Command = when (position) {
        OPENED -> InstantCommand({ gripper.open() })
        CLOSED -> InstantCommand({ gripper.close() })
    }

    private fun getCommandFor(pose: ArmPoses, member: ArmMember): Command = when (member) {
            SLIDER -> InstantCommand({ slider.setPosition(pose.pose.sliderDisplacement) })
            JOINT -> InstantCommand({ sliderJoint.setPosition(pose.pose.jointAngle) })
            WRIST -> InstantCommand({ wrist.setAngle(pose.pose.wristAngle) })
    }


    fun setPosition(pose: ArmPoses): Command {
        return SequentialCommandGroup(
            getCommandFor(pose, pose.pose.order.order.getFirst()),
            getCommandFor(pose, pose.pose.order.order.getSecond()),
            getCommandFor(pose, pose.pose.order.order.getThird()),
            WaitUntilCommand { slider.getPositionError() < 20 && sliderJoint.getPositionError() < 20})
            .andThen(gripper.setGripperPositionCommand(pose.pose.gripperPosition))
    }
}