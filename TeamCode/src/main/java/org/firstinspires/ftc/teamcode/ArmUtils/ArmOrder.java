package org.firstinspires.ftc.teamcode.ArmUtils;

public class ArmOrder {

    private final ArmMember first;
    private final ArmMember second;

    ArmOrder(ArmMember first, ArmMember second) {
        this.first =  first;
        this.second = second;
    }

    public ArmMember getFirst() {
        return first;
    }

    public ArmMember getSecond() {
        return second;
    }
}
