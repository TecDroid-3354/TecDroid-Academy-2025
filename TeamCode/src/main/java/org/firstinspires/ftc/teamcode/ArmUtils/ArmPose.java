package org.firstinspires.ftc.teamcode.ArmUtils;


public class ArmPose {
    private final int upperJointPosition;

    private final int bottomJointPosition;
    private final TakeType takeType;

    private final ArmOrder order;

    ArmPose(int upperJointPosition, int bottomJointPosition, TakeType takeType,  ArmOrder order) {
        this.upperJointPosition = upperJointPosition;
        this.bottomJointPosition = bottomJointPosition;
        this.takeType = takeType;
        this.order = order;
    }

    public int getUpperJointPosition() {
        return upperJointPosition;
    }

    public int getBottomJointPosition() {
        return bottomJointPosition;
    }
    public TakeType getTakeType() {
        return takeType;
    }

    public ArmOrder getOrder() {
        return order;
    }
}
