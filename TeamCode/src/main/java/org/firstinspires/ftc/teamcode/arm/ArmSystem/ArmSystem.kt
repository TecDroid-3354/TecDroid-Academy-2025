package org.firstinspires.ftc.teamcode.arm.ArmSystem

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.WaitUntilCommand
import org.firstinspires.ftc.teamcode.arm.ArmJoint.ArmJoint
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmMember.UpperJoint
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmMember.BottomJoint
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmOrders.UB
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmOrders.BU
import org.firstinspires.ftc.teamcode.arm.ArmSystem.TakeType.OUTTAKE
import org.firstinspires.ftc.teamcode.arm.ArmSystem.TakeType.NEUTRAL
import org.firstinspires.ftc.teamcode.arm.ArmSystem.TakeType.INTAKE
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmStates.BasketState
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmStates.IntakeState
import org.firstinspires.ftc.teamcode.arm.ArmSystem.BasketStates.High
import org.firstinspires.ftc.teamcode.arm.Intake.Intake

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
    IntakePose(
        ArmPose(
        upperJointPosition = 0.0, //In ticks
        bottomJointPosition = 0.0, //In ticks
        takeType = INTAKE,
        order = UB,
    )
    ),
    HighBasketPose(
        ArmPose(
        upperJointPosition = 0.0, //In ticks
        bottomJointPosition = 0.0, //In ticks
        takeType = OUTTAKE,
        order = BU,
    )
    ),
    LowBasketPose(
        ArmPose(
        upperJointPosition = 0.0, //In ticks
        bottomJointPosition = 0.0, //In ticks
        takeType = OUTTAKE,
        order = BU,
    )
    )
}

class Arm(config: ArmSystemConfig, hardwareMap: HardwareMap) {

    val intake =  Intake(config.intakeConfig, hardwareMap)
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
     * Sets a desired [ArmPoses]. According to the [ArmPoses]'s desired order previously specified within
     * the [ArmPose]. The function automatically sets the arm order according to the one passed within the arm pose.
     * When the motor's encoder reports that the desired position was reached, it sets the intake according to the
     * desired [TakeType] previously specified within the [ArmPose]
     */
    fun setPosition(pose: ArmPoses) {
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

    /**
     * Sets the intake´s mode according to the function the driver needs to be using at the time
     */
    private fun setIntake(takeType: TakeType) {
        when (takeType) {
            INTAKE -> intake::runIntake
            OUTTAKE -> intake::runOutTake
            NEUTRAL -> intake::stopIntake
        }
    }

    /**
     * It receives an [ArmPoses] and sets the intake when the motor's encoder has reached the desired arm position
     */
    private fun setIntakeWhenArmIsAtDesiredPosition(pose: ArmPoses) {
        WaitUntilCommand {
            upperJoint.getJointPosition() == pose.pose.upperJointPosition &&
                    bottomJoint.getJointPosition() == pose.pose.bottomJointPosition
        }.andThen(InstantCommand({ setIntake(pose.pose.takeType) }))
    }

}