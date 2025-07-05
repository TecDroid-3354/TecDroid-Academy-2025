package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import com.seattlesolvers.solverslib.command.CommandOpMode
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.RunCommand
import com.seattlesolvers.solverslib.command.button.GamepadButton
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import com.seattlesolvers.solverslib.gamepad.GamepadKeys
import org.firstinspires.ftc.teamcode.arm.armSystem.Arm
import org.firstinspires.ftc.teamcode.arm.armSystem.armSystemConfig
import org.firstinspires.ftc.teamcode.drivetrain.TankDrive
import org.firstinspires.ftc.teamcode.drivetrain.tankConfig
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.BasketState
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmStates.IntakeState
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.Low
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates.High
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.IntakePose
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.HighBasket
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.LowBasket
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.WJS
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.JWS
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.WJS
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.SWJ
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.JSW
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmOrders.WSJ
import org.firstinspires.ftc.teamcode.arm.armSystem.BasketStates

@TeleOp(name = "OpMode", group = "OpMode")
open class OpModeTDA: CommandOpMode() {

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

        GamepadButton(gamePad, GamepadKeys.Button.DPAD_RIGHT)
            .whenPressed(InstantCommand(arm.changeState))

        GamepadButton(gamePad, GamepadKeys.Button.DPAD_UP)
            .whenPressed(InstantCommand({ arm.currentBasketState = High }))

        GamepadButton(gamePad, GamepadKeys.Button.DPAD_DOWN)
            .whenPressed(InstantCommand({ arm.currentBasketState = Low }))

        GamepadButton(gamePad, GamepadKeys.Button.LEFT_BUMPER)
            .whenPressed(InstantCommand({
                when (arm.currentArmState) {
                    BasketState -> when (arm.currentBasketState) {
                            High -> arm.setPosition(HighBasket, JSW.order)
                            Low -> arm.setPosition(LowBasket, JSW.order)
                        }
                    IntakeState -> arm.setPosition(IntakePose, WJS.order)
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

            telemetry.update()
        }
    }
}