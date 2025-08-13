package org.firstinspires.ftc.teamcode.ArmUtils;

public enum ArmOrders {
    UB(new ArmOrder(ArmMember.UpperJoint, ArmMember.BottomJoint)),
    BU(new ArmOrder(ArmMember.BottomJoint, ArmMember.UpperJoint));

    private final ArmOrder order;
    ArmOrders(ArmOrder order) {

        this.order = order;
    }
    public ArmOrder getOrder() {
        return order;
    }
}
