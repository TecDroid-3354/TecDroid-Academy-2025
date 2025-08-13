package org.firstinspires.ftc.teamcode.ArmUtils;

public enum ArmPoses {
    IntakePosition(new ArmPose(
            100,
            0 ,
            TakeType.Intake,
            ArmOrders.UB.getOrder()
    )),
    LowBasket(new ArmPose(
            100,
            0 ,
            TakeType.Intake,
            ArmOrders.UB.getOrder()
    )),
    HighBasket(new ArmPose(
            100,
            0 ,
            TakeType.Intake,
            ArmOrders.UB.getOrder()
    ));



    private final ArmPose pose;
    ArmPoses(ArmPose pose) {
        this.pose = pose;
    }

    public ArmPose getPose() {
        return pose;
    }

}
