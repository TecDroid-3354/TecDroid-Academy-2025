package org.firstinspires.ftc.teamcode.subsystems.humerusSubsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.SimpleServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.humerusSubsystem.misc.HumerusConstants;

// The name of this subsystem comes from the humerus bone, key in the forearm
public class HumerusSubsystem extends SubsystemBase {
    // Declaring useful variables
    Telemetry telemetry;
    // Declaring servomotors
    SimpleServo rightHumerusServo;

    SimpleServo leftHumerusServo;


    // Constructor //
    public HumerusSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        /* ServoEx is an Interface, meaning it defines certain methods but it cannot be instantiated
         * Because of this, we have to call back the class SimpleServo, but it will have the added
         * functionality of SolversLib' ServoEx
         *
         * The object takes 5 parameters:
         * @hardwareMap — the hardwareMap to register the servo from
         * @servoName — name of the servo
         * minDegree — least allowed angle in degrees
         * maxDegree — max allowed angle in degrees
         * angleUnit — angle unit to be considered. It is degrees by default
         *
         * For more info, refer to this link: https://docs.seattlesolvers.com/features/hardware#servos
        **/

        rightHumerusServo = new SimpleServo(
                hardwareMap, HumerusConstants.humerusRightServoId,
                HumerusConstants.MeasureLimits.rightHumerusServo.minAngle, HumerusConstants.MeasureLimits.rightHumerusServo.maxAngle
        );
        leftHumerusServo = new SimpleServo(
                hardwareMap, HumerusConstants.humerusLeftServoId,
                HumerusConstants.MeasureLimits.leftHumerusServo.minAngle, HumerusConstants.MeasureLimits.leftHumerusServo.maxAngle
        );
        rightHumerusServo.setInverted(true);
        leftHumerusServo.setInverted(false);

    }

    // Functional code //
    public void setAngle(double angle) {
        // The following method turns the servo to a set angle in degrees
        rightHumerusServo.turnToAngle(angle);
        leftHumerusServo.turnToAngle(angle);
    }
}

// Emilio Nájera — June 24th, 2025