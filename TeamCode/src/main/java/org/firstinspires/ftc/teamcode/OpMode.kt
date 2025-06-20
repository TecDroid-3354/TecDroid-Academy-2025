package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import com.seattlesolvers.solverslib.command.CommandOpMode
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.RunCommand
import com.seattlesolvers.solverslib.command.button.GamepadButton
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import com.seattlesolvers.solverslib.gamepad.GamepadKeys
import org.firstinspires.ftc.teamcode.arm.ArmSystem.Arm
import org.firstinspires.ftc.teamcode.arm.ArmSystem.armSystemConfig
import org.firstinspires.ftc.teamcode.drivetrain.TankDrive
import org.firstinspires.ftc.teamcode.drivetrain.tankConfig
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmStates
import org.firstinspires.ftc.teamcode.arm.ArmSystem.BasketStates.Low
import org.firstinspires.ftc.teamcode.arm.ArmSystem.BasketStates.High
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmPoses.IntakePose
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmPoses.LowBasketPose
import org.firstinspires.ftc.teamcode.arm.ArmSystem.ArmPoses.HighBasketPose

@TeleOp(name = "OpMode", group = "OpMode")
open class OpMode: CommandOpMode() {

    // Declare OpMode members.
    private val runtime = ElapsedTime()

    private lateinit var tankDrive: TankDrive

    private lateinit var arm: Arm

    private lateinit var gamePad: GamepadEx

    override fun initialize() {

        tankDrive = TankDrive(tankConfig, hardwareMap)

        arm = Arm(armSystemConfig, hardwareMap)

        gamePad = GamepadEx(gamepad1)

        configureButtonBindings()
    }

    private fun configureButtonBindings() {
        GamepadButton(gamePad, GamepadKeys.Button.DPAD_UP)
            .whenPressed(InstantCommand({ arm.basketState = High }))

        GamepadButton(gamePad, GamepadKeys.Button.DPAD_DOWN)
            .whenPressed(InstantCommand({ arm.basketState = Low }))

        GamepadButton(gamePad, GamepadKeys.Button.LEFT_BUMPER)
            .whenPressed(InstantCommand({ arm::changeState }))

        GamepadButton(gamePad, GamepadKeys.Button.RIGHT_BUMPER)
            .whenPressed(InstantCommand({
                when (arm.getCurrentArmState) {
                    ArmStates.IntakeState -> arm.setPosition(IntakePose)
                    ArmStates.BasketState -> when (arm.getCurrentBasketState) {
                        High -> arm.setPosition(HighBasketPose)
                        Low -> arm.setPosition(LowBasketPose)
                    }
                }
            }))

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

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: $runtime")

            telemetry.addData(
                "Motors",
                "left (%.2f), right (%.2f)",
                tankDrive.getLeftPower(),
                tankDrive.getRightPower()
            )

            telemetry.addData("Intake", "Output: ${arm.intake.getPower()}")

            telemetry.addData("Current arm State: ", arm.getCurrentArmState)
            telemetry.addData("Current basket State: ", arm.getCurrentBasketState)

            telemetry.update()

            println("$")
        }
    }
}