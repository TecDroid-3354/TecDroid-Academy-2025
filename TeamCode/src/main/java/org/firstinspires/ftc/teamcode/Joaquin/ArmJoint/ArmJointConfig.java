package org.firstinspires.ftc.teamcode.Joaquin.ArmJoint;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.utils.Reduction;
import com.seattlesolvers.solverslib.controller.PController;


public class ArmJointConfig {

    private final String motorId;
    private final int maximumAngleLimit;
    private final int minimumAngleLimit;
    private final Reduction gearRatio;
    private final int tolerance;
    private final PController pController;



    public ArmJointConfig(String motorId, int maximumAngleLimit, int minimumAngleLimit, Reduction gearRatio, int tolerance, PController pController){
        this.motorId = motorId;
        this.maximumAngleLimit = maximumAngleLimit;
        this.minimumAngleLimit = minimumAngleLimit;
        this.gearRatio = gearRatio;
        this.tolerance = tolerance;
        this.pController = pController;
    }

    public String getMotorId(){
        return motorId;
    }
    public int getMaximumAngleLimit(){
        return maximumAngleLimit;
    }
    public int getMinimumAngleLimit(){
        return minimumAngleLimit;
    }
    public int getTolerance(){
        return tolerance;
    }

    public Reduction getGearRatio(){
        return gearRatio;
    }

    public PController getpController() {
        return pController;
    }
}
