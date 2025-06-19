package org.firstinspires.ftc.teamcode.Arm

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.WaitUntilCommand
import org.firstinspires.ftc.teamcode.Intake.Intake
import org.firstinspires.ftc.teamcode.Arm.ArmMember.UpperJoint
import org.firstinspires.ftc.teamcode.Arm.ArmMember.BottomJoint
import org.firstinspires.ftc.teamcode.Arm.ArmOrders.UB
import org.firstinspires.ftc.teamcode.Arm.ArmOrders.BU
import org.firstinspires.ftc.teamcode.Arm.TakeType.OUTTAKE
import org.firstinspires.ftc.teamcode.Arm.TakeType.NEUTRAL
import org.firstinspires.ftc.teamcode.Arm.TakeType.INTAKE
import org.firstinspires.ftc.teamcode.Arm.ArmStates.BasketState
import org.firstinspires.ftc.teamcode.Arm.ArmStates.IntakeState
import org.firstinspires.ftc.teamcode.Arm.BasketStates.High

enum class ArmMember {
    UpperJoint, BottomJoint;
}

enum class ArmStates {
    BasketState, IntakeState
}

enum class BasketStates {
    High, Low
}

enum class TakeType {
    INTAKE,
    OUTTAKE,
    NEUTRAL,
}

data class ArmOrder(
    val first: ArmMember,
    val second: ArmMember,
)

enum class ArmOrders(val order: ArmOrder) {
    UB(ArmOrder(UpperJoint, BottomJoint)),
    BU(ArmOrder(BottomJoint, UpperJoint)),
}

data class ArmPose(
    val upperJointPosition: Double,
    val bottomJointPosition: Double,
    val takeType : TakeType,
    val order: ArmOrders
)

enum class ArmPoses(var pose: ArmPose) {
    IntakePose(ArmPose(
        upperJointPosition = 0.0, //In ticks
        bottomJointPosition = 0.0, //In ticks
        takeType = INTAKE,
        order = UB,
    )),
    HighBasketPose(ArmPose(
        upperJointPosition = 0.0, //In ticks
        bottomJointPosition = 0.0, //In ticks
        takeType = OUTTAKE,
        order = BU,
    )),
    LowBasketPose(ArmPose(
        upperJointPosition = 0.0, //In ticks
        bottomJointPosition = 0.0, //In ticks
        takeType = OUTTAKE,
        order = BU,
    ))
}

class Arm(config: ArmSystemConfig, hardwareMap: HardwareMap) {

    private val intake =  Intake(config.intakeConfig, hardwareMap)
    private val upperJoint = ArmJoint(config.upperJointConfig, hardwareMap)
    private val bottomJoint =  ArmJoint(config.bottomJointConfig, hardwareMap)
    private var currenArmState: ArmStates = IntakeState

    val getCurrentArmState: ArmStates = currenArmState

    val changeState = { currenArmState = if (currenArmState == IntakeState) BasketState else IntakeState }

    var basketState: BasketStates = High

    val getCurrentBasketState: BasketStates = basketState

    fun setArmPosition(pose: ArmPoses) {
        when (pose.pose.order) {
            UB -> {
                upperJoint.setTargetPosition(pose.pose.upperJointPosition)
                bottomJoint.setTargetPosition(pose.pose.bottomJointPosition)

                setIntakeWhenArmIsAtDesiredPosition(pose)
            }
            BU -> {
                bottomJoint.setTargetPosition(pose.pose.bottomJointPosition)
                upperJoint.setTargetPosition(pose.pose.upperJointPosition)

                setIntakeWhenArmIsAtDesiredPosition(pose)

            }
        }
    }

    private fun setIntake(takeType: TakeType) {
        when (takeType) {
            INTAKE -> intake::runIntake
            OUTTAKE -> intake::runOutTake
            NEUTRAL -> intake::stopIntake
        }
    }

    private fun setIntakeWhenArmIsAtDesiredPosition(pose: ArmPoses) {
        WaitUntilCommand {
            upperJoint.getJointPosition() == pose.pose.upperJointPosition &&
                    bottomJoint.getJointPosition() == pose.pose.bottomJointPosition
        }.andThen(InstantCommand({ setIntake(pose.pose.takeType) }))
    }

}