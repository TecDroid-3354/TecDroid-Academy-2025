package org.firstinspires.ftc.teamcode.subsystems.jointSubsystem.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.jointSubsystem.JointSubsystem;

public class JointPosition extends CommandBase {
    // Declaring useful variables
    JointSubsystem jointSubsystem;
    double angle;

    public JointPosition(JointSubsystem jointSubsystem, double angle) {
        // Assigning values to class variables w/ parameters
        this.jointSubsystem = jointSubsystem;
        this.angle = angle;

        // addRequirements() tells the Scheduler that the Joint Subsystem is being used in this command
        addRequirements(this.jointSubsystem);
    }

    @Override
    public void initialize() {
        jointSubsystem.setTargetAngle(angle);
    }

    @Override
    public boolean isFinished() {
        return jointSubsystem.isAtTarget();
    }

    @Override
    public void end(boolean interrupted) {
        jointSubsystem.stopMotors();
    }
}

// Emilio Nájera, May—30th—2025