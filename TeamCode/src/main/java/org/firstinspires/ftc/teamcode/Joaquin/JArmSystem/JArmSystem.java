package org.firstinspires.ftc.teamcode.Joaquin.JArmSystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.ButtonReader;

import org.firstinspires.ftc.teamcode.ArmUtils.ArmMember;
import org.firstinspires.ftc.teamcode.ArmUtils.ArmPose;
import org.firstinspires.ftc.teamcode.ArmUtils.ArmPoses;
import org.firstinspires.ftc.teamcode.ArmUtils.ArmStates;
import org.firstinspires.ftc.teamcode.Joaquin.ArmJoint.ArmJoint;
import org.firstinspires.ftc.teamcode.Joaquin.ArmJoint.ArmJointConfig;
import org.firstinspires.ftc.teamcode.Joaquin.Intake.Intake;
import org.firstinspires.ftc.teamcode.Joaquin.Intake.IntakeConfig;

public class JArmSystem {
    private ArmStates currentArmState = ArmStates.IntakeState;
    private final ButtonReader keyReader;

    private final Intake intake;
    private final ArmJoint upperJoint;
    private final ArmJoint bottomJoint;
    public JArmSystem(HardwareMap hardwareMap, IntakeConfig IConfig, ArmJointConfig bottomJConfig, ArmJointConfig upperJConfig,ButtonReader keyReader) {
        this.upperJoint = new ArmJoint(hardwareMap, upperJConfig);
        this.bottomJoint = new ArmJoint(hardwareMap, bottomJConfig);
        this.intake = new Intake(hardwareMap, IConfig, keyReader);
        this.keyReader = keyReader;
    }

    public ArmStates getCurrentArmState() {
        return currentArmState;
    }

    public void changeState(Boolean isATriggered, Boolean isBTriggered, Boolean isCTriggered) {
        if (isATriggered) {
            currentArmState = ArmStates.IntakeState;
        } else if (isBTriggered) {
            currentArmState = ArmStates.HighBasketState;
        } else {
            currentArmState = ArmStates.LowBasketState;
        }
    }


    public void getIntakeOutput() {
        intake.getPower();
    }


    private void setIntake(ArmPoses pose, boolean isTriggered) {
        if (isTriggered &&
                upperJoint.getJointPositionError() < 15 && bottomJoint.getJointPositionError() < 15) {
            switch (pose.getPose().getTakeType()) {
                case Intake:
                    intake.runIntake();
                    break;
                case Outtake:
                    intake.runOutTake();
                    break;
            }
        }
    }
    private void getActionFor(ArmPoses pose, ArmMember member) {
        switch (member) {
            case UpperJoint:
                upperJoint.setTargetPosition(pose.getPose().getUpperJointPosition());
                break;
            case BottomJoint:
                bottomJoint.setTargetPosition(pose.getPose().getBottomJointPosition());
                break;
        }

    }
    private void setTargetPosition(ArmPoses pose) {
        getActionFor(pose, pose.getPose().getOrder().getFirst());
        getActionFor(pose, pose.getPose().getOrder().getSecond());
        setIntake(pose, keyReader.wasJustPressed());
    }

    private void setTargetPosition(ArmStates armState, ArmPoses pose) {
        switch (armState) {
            case IntakeState:
                setTargetPosition(ArmPoses.IntakePosition);
                break;
            case HighBasketState:
                setTargetPosition(ArmPoses.HighBasket);
                break;
            case LowBasketState:
                setTargetPosition(ArmPoses.LowBasket);
                break;
            default:
                setTargetPosition(pose);
                break;

        }
    }

    public void setActionFor(GamepadButton button, ArmPoses pose) {
        if (button.get()) {
            setTargetPosition(getCurrentArmState(), pose);
        }
    }
}
