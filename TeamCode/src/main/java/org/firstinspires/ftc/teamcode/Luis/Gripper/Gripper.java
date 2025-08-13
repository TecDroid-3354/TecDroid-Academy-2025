package org.firstinspires.ftc.teamcode.Luis.Gripper;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.SimpleServo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Gripper extends SubsystemBase {

    private final SimpleServo servo;
    private boolean isOpen = false;
    private HardwareMap hardwareMap;

    public Gripper(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        servo = new SimpleServo(hardwareMap, "gogo", 0.0, 180.0);
        close();
    }

    public void open() {
        if (!isOpen) {
            servo.turnToAngle(90.0);
            isOpen = true;
        }
    }

    public void close() {
        if (isOpen) {
            servo.turnToAngle(0.0);
            isOpen = false;
        }
    }

    public double getPosition() {
        return servo.getPosition();
    }
}
