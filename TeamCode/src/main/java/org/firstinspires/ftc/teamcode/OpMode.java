package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Joaquin.Intake.IntakeConfig;
import org.firstinspires.ftc.teamcode.Joaquin.JArmSystem.JArmSystem;

@TeleOp(name = "TwoJointRobot", group = "OpMode")
public class OpMode extends CommandOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    //private Gripper gripper;

    //private TankDrive tankDrive;

    private JArmSystem arm;
    private GamepadEx controller;

    //private ArmJoint joint;

    //private PositionFlag poseFlag;

    @Override
    public void initialize() {
        //joint = new ArmJoint(hardwareMap, "Motor", new Reduction(19.2), 5, new PController(3.0), 0, 2000);
        //gripper = new Gripper(hardwareMap);
        //poseFlag = new PositionFlag(joint, ArmPoses.IntakePosition);
        //tankDrive = new TankDrive(hardwareMap);
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

        while (opModeIsActive()) {
            // Show the elapsed game time and wheel power.

            //tankDrive.drive(controller.getLeftY(), controller.getRightX());
            /*if (controller.getButton(GamepadKeys.Button.A) && poseFlag.isFlagged(ArmPoses.IntakePosition)) {
                gripper.open();
            }
            if (controller.getButton(GamepadKeys.Button.B)) {
                gripper.close();
            }

            /*if (controller.getButton(GamepadKeys.Button.A)) {
                joint.setPower(0.4);
            }

            if (controller.getButton(GamepadKeys.Button.X)) {
                joint.setTargetPosition(100);
            }

            if (controller.getButton(GamepadKeys.Button.Y) ) {
                joint.setTargetPosition(0);
            }*/


            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.update();
            /*telemetry.addData("Gripper position", gripper.getPosition());
            telemetry.addData("Joint position", joint.getJointPosition());
            telemetry.addData("Joint position error", joint.getJointPositionError());*/
            telemetry.addData("Is Pressed", controller.getButton(GamepadKeys.Button.A));
            telemetry.addData("Is Pressed", controller.getButton(GamepadKeys.Button.B));
        }
    }

    public IntakeConfig getIntakeConfig() {
        return new IntakeConfig("servo", DcMotorSimple.Direction.FORWARD);
    }
}

