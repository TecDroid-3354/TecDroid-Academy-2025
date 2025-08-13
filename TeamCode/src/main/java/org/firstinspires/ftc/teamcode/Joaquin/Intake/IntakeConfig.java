package org.firstinspires.ftc.teamcode.Joaquin.Intake;

import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class IntakeConfig{

    private final String servoId;
    
    private final DcMotorSimple.Direction servoDirection;

    public IntakeConfig(String servoId, DcMotorSimple.Direction servoDirection) {
        this.servoId = servoId;
        this.servoDirection = servoDirection;
    }

    public String getServoId() {
        return servoId;
    }
    
    public DcMotorSimple.Direction getServoDirection() {
        return servoDirection;
    }
}



