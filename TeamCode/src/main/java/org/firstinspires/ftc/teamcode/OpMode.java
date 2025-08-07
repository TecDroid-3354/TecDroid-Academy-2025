package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.ButtonReader;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.ArmJoint.ArmJoint;
import org.firstinspires.ftc.teamcode.Gripper.Gripper;
import org.firstinspires.ftc.teamcode.utils.Reduction;

@TeleOp(name = "NigaTeleop", group = "OpMode")
public class OpMode extends CommandOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    private Gripper gripper;

    private GamepadEx controller;

    private ArmJoint joint;

    @Override
    public void initialize() {
        joint = new ArmJoint(hardwareMap, new Reduction(19.2));
        gripper = new Gripper(hardwareMap);
        controller = new GamepadEx(gamepad1);
    }

    @Override
    public void runOpMode() {
        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        while (opModeIsActive() && !isStopRequested()) {
            // Show the elapsed game time and wheel power.

            /*if (controller.getButton(GamepadKeys.Button.A)) {
                gripper.open();
            }
            if (controller.getButton(GamepadKeys.Button.B)) {
                gripper.close();
            }*/

            if (controller.getButton(GamepadKeys.Button.A)) {
                joint.setTargetPosition(1000, 10);
            }

            if (controller.getButton(GamepadKeys.Button.B)) {
                joint.setTargetPosition(0, 10);
            }

            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.update();
            telemetry.addData("Joint position", joint.getJointPosition());
            telemetry.addData("Joint position error", joint.getJointPositionError());
            telemetry.addData("Is Pressed", controller.getButton(GamepadKeys.Button.A));
            telemetry.addData("Is Pressed", controller.getButton(GamepadKeys.Button.B));
        }
    }
}
