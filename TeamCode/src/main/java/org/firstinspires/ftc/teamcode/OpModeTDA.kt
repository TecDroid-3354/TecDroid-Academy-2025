package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import com.seattlesolvers.solverslib.command.CommandOpMode
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.RunCommand
import com.seattlesolvers.solverslib.command.button.Button
import com.seattlesolvers.solverslib.command.button.GamepadButton
import com.seattlesolvers.solverslib.gamepad.ButtonReader
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import com.seattlesolvers.solverslib.gamepad.GamepadKeys
import com.seattlesolvers.solverslib.gamepad.TriggerReader
import org.firstinspires.ftc.teamcode.arm.armSystem.Arm
import org.firstinspires.ftc.teamcode.arm.armSystem.armSystemConfig
import org.firstinspires.ftc.teamcode.drivetrain.TankDrive
import org.firstinspires.ftc.teamcode.drivetrain.tankConfig
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.Low
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.High
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.IntakePose
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.LowBasketPose
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.HighBasketPose

@TeleOp(name = "OpMode", group = "OpMode")
open class OpModeTDA: CommandOpMode() {

    // Declare OpMode members.
    private val runtime = ElapsedTime()

    private lateinit var tankDrive: TankDrive

    private lateinit var arm: Arm

    private lateinit var controller: GamepadEx

    private lateinit var leftTriggerReader: TriggerReader

    private lateinit var armPositionButtonReader: ButtonReader



    override fun initialize() {

        tankDrive = TankDrive(tankConfig, hardwareMap)

        arm = Arm(armSystemConfig, hardwareMap, leftTriggerReader)

        controller = GamepadEx(gamepad1)

        leftTriggerReader = TriggerReader(controller, GamepadKeys.Trigger.LEFT_TRIGGER)

        armPositionButtonReader = ButtonReader(controller, GamepadKeys.Button.Y)

        configureButtonBindings()
    }

    private fun setArmPosition(): InstantCommand {
        return InstantCommand({
            if (armPositionButtonReader.wasJustPressed()) {
                arm.setTargetPosition(arm.getCurrentArmState, arm.getCurrentBasketState)
            }
        })
    }

    private fun configureButtonBindings() {

        GamepadButton(controller, GamepadKeys.Button.DPAD_UP)
            .whenPressed(InstantCommand({ arm.basketState = High }))

        GamepadButton(controller, GamepadKeys.Button.DPAD_DOWN)
            .whenPressed(InstantCommand({ arm.basketState = Low }))

        GamepadButton(controller, GamepadKeys.Button.LEFT_BUMPER)
            .whenPressed(InstantCommand({ arm::changeState }))

    }

    override fun runOpMode() {
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        // Wait for the game to start (driver presses START)
        waitForStart()
        runtime.reset()

        tankDrive.defaultCommand = RunCommand(
            { tankDrive.drive(-gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) },
            tankDrive
        )

        while (opModeIsActive()) {

            setArmPosition()
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: $runtime")

            telemetry.addData(
                "Motors",
                "left (%.2f), right (%.2f)",
                tankDrive.getMotorsPower().first,
                tankDrive.getMotorsPower().second
            )

            telemetry.addData("Intake", "Output: ${arm.getIntakeOutput()}")

            telemetry.addData("Current arm State: ", arm.getCurrentArmState)
            telemetry.addData("Current basket State: ", arm.getCurrentBasketState)

            telemetry.update()
        }
    }
}