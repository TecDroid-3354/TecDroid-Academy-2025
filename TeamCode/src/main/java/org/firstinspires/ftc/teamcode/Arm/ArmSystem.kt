package org.firstinspires.ftc.teamcode.Arm

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Intake.Intake
import org.firstinspires.ftc.teamcode.Intake.IntakeConfig
import org.firstinspires.ftc.ulateamcode.Arm.JointConfig
import org.firstinspires.ftc.teamcode.Arm.ArmMember.UpperJoint
import org.firstinspires.ftc.teamcode.Arm.ArmMember.BottomJoint
import org.firstinspires.ftc.teamcode.Arm.ArmOrders.UB
import org.firstinspires.ftc.teamcode.Arm.ArmOrders.BU

enum class ArmMember {
    UpperJoint, BottomJoint;
}

data class ArmPose(
    val upperJointPosition: Double,
    val bottomJointPosition: Double,
    val takeType : TakeType,
    val order: ArmOrders
)

data class ArmOrder(
    val first: ArmMember,
    val second: ArmMember,
)

enum class TakeType {
    Intake,
    Outtake,
    Neutral,
}

enum class ArmPoses(var pose: ArmPose) {
    Intake(ArmPose(
        upperJointPosition = 0.0, //In ticks
        bottomJointPosition = 0.0, //In ticks
        takeType = TakeType.Intake,
        order = UB,
    )),
    HighBasket(ArmPose(
        upperJointPosition = 0.0,
        bottomJointPosition = 0.0,
        takeType = TakeType.Outtake,
        order = BU,
    )),
    LowBasket(ArmPose(
        upperJointPosition = 0.0,
        bottomJointPosition = 0.0,
        takeType = TakeType.Outtake,
        order = BU,
    ))
}

enum class ArmOrders(val order: ArmOrder) {
    UB(ArmOrder(UpperJoint, BottomJoint)),
    BU(ArmOrder(BottomJoint, UpperJoint)),
}

class Arm(private val intakeConfig: IntakeConfig, private val upperJointConfig: JointConfig,
          bottomJointConfig: JointConfig, private val hardwareMap: HardwareMap) {

    private val intake =  Intake(intakeConfig, hardwareMap)
    private val upperJoint = ArmJoint(upperJointConfig, hardwareMap)
    private val bottomJoint =  ArmJoint(bottomJointConfig, hardwareMap)

    fun setArmPosition(pose: ArmPoses, order: ArmOrders) {
        when (order) {
            UB -> {
                upperJoint.setTargetPosition(pose.pose.upperJointPosition)
                bottomJoint.setTargetPosition(pose.pose.bottomJointPosition)
                setIntake(pose.pose.takeType)
            }
            BU -> {
                bottomJoint.setTargetPosition(pose.pose.bottomJointPosition)
                upperJoint.setTargetPosition(pose.pose.upperJointPosition)
                setIntake(pose.pose.takeType)
            }
        }
    }
    private fun setIntake(takeType: TakeType) {
        when (takeType) {
            TakeType.Intake -> intake::runIntake
            TakeType.Outtake -> intake::runOutTake
            TakeType.Neutral -> intake::stopIntake
        }
    }
}