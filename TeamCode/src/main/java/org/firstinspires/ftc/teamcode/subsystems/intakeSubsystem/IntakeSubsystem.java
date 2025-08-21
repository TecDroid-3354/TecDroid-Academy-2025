package org.firstinspires.ftc.teamcode.subsystems.intakeSubsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.SimpleServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.intakeSubsystem.misc.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    // Declaring useful variables
    Telemetry telemetry;

    // Declaring servomotors to be used in this subsystem
    ServoEx wristRotatorServo;
    ServoEx gripperServo;


    // Constructor //
    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        // Assigning values to useful variables based on the passed arguments
        this.telemetry = telemetry;

        // Assigning values to servos
        gripperServo = new SimpleServo(
                hardwareMap, IntakeConstants.Setup.gripperServoId,
                IntakeConstants.MeasureLimits.gripperMinAngle,
                IntakeConstants.MeasureLimits.gripperMaxAngle
        );

        wristRotatorServo = new SimpleServo(
                hardwareMap, IntakeConstants.Setup.wristRotatorServoId,
                IntakeConstants.MeasureLimits.wristRotatorMinAngle,
                IntakeConstants.MeasureLimits.wristRotatorMaxAngle
        );
    }


    // Functional code //
    public void openGripper() {
        // The setPosition method sets the position of the servo to a specified, normalized location
        gripperServo.setPosition(0.7);
    }

    public void closeGripper() {
        gripperServo.setPosition(0.35);
    }

    public void turnWristRotator(double angle) {
        wristRotatorServo.turnToAngle(angle);
    }
}

// Emilio Nájera — June 27th, 2025