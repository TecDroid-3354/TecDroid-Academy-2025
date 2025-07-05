package org.firstinspires.ftc.teamcode.arm.armSystem

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.Command
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.SequentialCommandGroup
import com.seattlesolvers.solverslib.command.WaitUntilCommand
import com.sun.tools.javac.comp.Resolve
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.SLIDER
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.JOINT
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.WRIST
import org.firstinspires.ftc.teamcode.arm.armSystem.GripperPosition.CLOSED
import org.firstinspires.ftc.teamcode.arm.armSystem.GripperPosition.OPENED
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.WJS
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.JWS
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.WJS
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.SWJ
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.JSW
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.WSJ
import org.firstinspires.ftc.teamcode.arm.gripper.Gripper
import org.firstinspires.ftc.teamcode.arm.slider.Slider
import org.firstinspires.ftc.teamcode.arm.sliderJoint.SliderJoint
import org.firstinspires.ftc.teamcode.arm.wrist.Wrist
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.BasketState
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.IntakeState
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.High
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.Low



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
    val first: ArmMember,
    val second: ArmMember,
    val third: ArmMember
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
    val gripperState: GripperPosition,
    val order: ArmOrders
)

enum class ArmPoses(val pose: ArmPose) {

    IntakePose(ArmPose(
        sliderDisplacement = 0.0,
        jointAngle = 0.0,
        wristAngle = 0.0,
        gripperState = OPENED,
        order = WJS,
    )),

    HighBasket(ArmPose(
        sliderDisplacement = 0.0,
        jointAngle = 0.0,
        wristAngle = 0.0,
        gripperState = OPENED,
        order = SWJ,
    )),

    LowBasket(ArmPose(
        sliderDisplacement = 0.0,
        jointAngle = 0.0,
        wristAngle = 0.0,
        gripperState = OPENED,
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

    private fun Gripper.setGripperPosition(position: GripperPosition): Command = when (position) {
        OPENED -> InstantCommand({ gripper.open() })
        CLOSED -> InstantCommand({ gripper.close() })
    }

    private fun getCommandFor(pose: ArmPoses, member: ArmMember): Command = when (member) {
            SLIDER -> InstantCommand({ slider.setPosition(pose.pose.sliderDisplacement) })
            JOINT -> InstantCommand({ sliderJoint.setPosition(pose.pose.jointAngle) })
            WRIST -> InstantCommand({ wrist.setAngle(pose.pose.wristAngle) })
        }


    fun setPosition(pose: ArmPoses, order: ArmOrder): Command {
        return SequentialCommandGroup(
            getCommandFor(pose, order.getFirst()),
            getCommandFor(pose, order.getSecond()),
            getCommandFor(pose, order.getThird())
        ).andThen(WaitUntilCommand { slider.getPositionError() < 20 })
            .andThen(gripper.setGripperPosition(pose.pose.gripperState))
    }
}