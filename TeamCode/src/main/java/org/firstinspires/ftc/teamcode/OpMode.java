package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Drivetrain.TankConfigKt;
import org.firstinspires.ftc.teamcode.Drivetrain.TankDrive;

import org.firstinspires.ftc.teamcode.Intake.Intake;
import org.firstinspires.ftc.teamcode.Intake.IntakeConfigKt;

@TeleOp(name="TankDrive OpMode", group="Linear OpMode")
@Disabled
public class OpMode extends CommandOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    private TankDrive tankDrive;

    private GamepadEx gamePad;

    @Override
    public void initialize() {
        tankDrive = new TankDrive(TankConfigKt.getTankConfig(), hardwareMap);

        gamePad = new GamepadEx(gamepad1);

        configureButtonBindings();
    }

    public void configureButtonBindings() {

        new GamepadButton(gamePad, GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(
                        
                ));

        new GamepadButton(gamePad, GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(
                       
                        ));

    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        tankDrive.setDefaultCommand(new RunCommand(
                //Set power to the motors through the gamepad left y stick for the drive and
                () -> tankDrive.drive(-gamepad1.left_stick_y, gamepad1.right_stick_x),
                tankDrive
        ));

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", tankDrive.getLeftPower(), tankDrive.getRightPower());
            telemetry.addData("Intake", "Output: ");
            telemetry.update();
        }
    }
}
