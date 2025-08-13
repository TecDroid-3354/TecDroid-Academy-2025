package org.firstinspires.ftc.teamcode.Joaquin.Intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.gamepad.ButtonReader;

public class Intake extends SubsystemBase {
    private final IntakeConfig config;
    private final HardwareMap hardwareMap;
    private final ButtonReader keyReader;
    private CRServo servo;

    public Intake(HardwareMap hardwareMap, IntakeConfig config, ButtonReader keyReader) {
        this.config = config;
        this.hardwareMap = hardwareMap;
        this.keyReader = keyReader;
        configureServo();
    }

    public String getPower() {
        return String.valueOf(servo.getPower());
    }

    public void setPowerAction(double power) {
        servo.setPower(power);
    }

    public void stopIntakeAction() {
        setPowerAction(0.0);
    }

    public void runIntake() {
        setPowerAction(1.0);
        if (keyReader.wasJustReleased()) {
            stopIntakeAction();
        }

    }

    public void runOutTake() {
        setPowerAction(-1.0);
        if (keyReader.wasJustReleased()) {
            stopIntakeAction();
        }
    }

    private void configureServo() {
        servo = hardwareMap.get(CRServo.class, "servo");
        servo.setDirection(DcMotorSimple.Direction.FORWARD);
    }
}