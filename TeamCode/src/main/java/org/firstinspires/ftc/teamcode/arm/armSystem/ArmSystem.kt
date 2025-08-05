package org.firstinspires.ftc.teamcode.arm.armSystem

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.Command
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.SequentialCommandGroup
import com.seattlesolvers.solverslib.command.WaitUntilCommand
import com.seattlesolvers.solverslib.gamepad.TriggerReader
import org.firstinspires.ftc.teamcode.arm.armJoint.ArmJoint
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.UpperJoint
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmMember.BottomJoint
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.UB
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.BU
import org.firstinspires.ftc.teamcode.arm.armSystem.TakeType.OUTTAKE
import org.firstinspires.ftc.teamcode.arm.armSystem.TakeType.INTAKE
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.BasketState
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.IntakeState
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.High
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.Low
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.IntakePose
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.HighBasketPose
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.LowBasketPose
import org.firstinspires.ftc.teamcode.arm.intake.Intake

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
    OUTTAKE
}

data class ArmOrder(
    val first: ArmMember,
    val second: ArmMember,
)

enum class ArmOrders(private val order: ArmOrder) {
    UB(ArmOrder(UpperJoint, BottomJoint)),
    BU(ArmOrder(BottomJoint, UpperJoint)),
}

data class ArmPose(
    val upperJointPosition: Int,
    val bottomJointPosition: Int,
    val takeType : TakeType,
    val order: ArmOrders
)

enum class ArmPoses(var pose: ArmPose) {

    IntakePose(ArmPose(
        upperJointPosition = 0, //In ticks, in need to check the actual position
        bottomJointPosition = 0, //In ticks, in need to check the actual position
        takeType = INTAKE,
        order = UB,
    )),
    HighBasketPose(ArmPose(
        upperJointPosition = 0, //In ticks, in need to check the actual position
        bottomJointPosition = 0, //In ticks, in need to check the actual position
        takeType = OUTTAKE,
        order = BU,
    )),
    LowBasketPose(ArmPose(
        upperJointPosition = 0, //In ticks, in need to check the actual position
        bottomJointPosition = 0, //In ticks, in need to check the actual position
        takeType = OUTTAKE,
        order = BU,
    ))
}

class Arm(config: ArmSystemConfig, hardwareMap: HardwareMap, val triggerReader: TriggerReader) {

    private val intake =  Intake(config.intakeConfig, hardwareMap, triggerReader)
    private val upperJoint = ArmJoint(config.upperJointConfig, hardwareMap)
    private val bottomJoint =  ArmJoint(config.bottomJointConfig, hardwareMap)

    /**
     * Sets a default current state
     */
    private var currentArmState: ArmStates = IntakeState

    /**
     * Gets the current arm state
     */
    val getCurrentArmState: ArmStates = currentArmState

    /**
     * Changes the current arm state to the opposite of it
     */
    val changeState = { currentArmState = if (currentArmState == IntakeState) BasketState else IntakeState }

    /**
     * Sets a default basket state
     */
    var basketState: BasketStates = High

    /**
     * Gets the current basket state
     */
    val getCurrentBasketState: BasketStates = basketState

    /**
     * Gets the current output's value of the intake and avoids creating a new object for it inside other files
     */
    fun getIntakeOutput() {
        intake.getPower()
    }

    /**
     * Sets the intake´s mode according to the function the driver needs to be using at the time, only when
     * both of the arm´s joints have reached their target position
     */
    private fun setIntake(pose: ArmPoses, isTriggered: Boolean): Command {
        return WaitUntilCommand {
            isTriggered &&
                    upperJoint.getJointPositionError() < 15 && bottomJoint.getJointPositionError() < 15
        }.andThen(InstantCommand({
            when (pose.pose.takeType) {
                INTAKE -> intake::runIntake
                OUTTAKE -> intake::runOutTake
            }}
        ))
    }

    /**
     * Sets a desired [ArmPoses]. According to the [ArmOrders]' desired order previously specified within
     * the [ArmPose]. The function automatically sets the arm order according to the one passed within the arm pose.
     * When the motor's encoder reports that the desired position was reached, it sets the intake according to the
     * desired [TakeType] previously specified inside the [ArmPose]
     */
     private fun setTargetPosition(pose: ArmPoses) {
        when (pose.pose.order) {
            UB -> SequentialCommandGroup(
                upperJoint.setTargetPosition(pose.pose.upperJointPosition),
                bottomJoint.setTargetPosition(pose.pose.bottomJointPosition),
                setIntake(pose, triggerReader.wasJustPressed())
            )
            BU -> SequentialCommandGroup(
                bottomJoint.setTargetPosition(pose.pose.bottomJointPosition),
                upperJoint.setTargetPosition(pose.pose.upperJointPosition),
                setIntake(pose, triggerReader.wasJustPressed())
            )
        }
    }

    private fun setTargetPosition(basketState: BasketStates) {
        when (basketState) {
            High -> setTargetPosition(HighBasketPose)
            Low -> setTargetPosition(LowBasketPose)
        }
    }

    fun setTargetPosition(armState: ArmStates, basketState: BasketStates) {
        when (armState) {
            IntakeState -> setTargetPosition(IntakePose)
            BasketState -> setTargetPosition(basketState)
        }
    }
}