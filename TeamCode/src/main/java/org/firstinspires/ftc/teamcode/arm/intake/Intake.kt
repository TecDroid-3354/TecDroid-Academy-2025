package org.firstinspires.ftc.teamcode.arm.intake

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.Command
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.RunCommand
import com.seattlesolvers.solverslib.command.SequentialCommandGroup
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.command.WaitUntilCommand
import com.seattlesolvers.solverslib.gamepad.TriggerReader
import fi.iki.elonen.NanoHTTPD

/**
 * Creates a class which holds the necessary functions that may be needed further in the code
 */
class Intake(private val config: IntakeConfig, private val hardwareMap: HardwareMap, private val triggerReader: TriggerReader): SubsystemBase() {

    /**
     * Creates a simple continuous rotation servo.
     */
    private lateinit var servo: CRServo

    /**
     * Gets the current power that is being passed to the intake
     */
    fun getPower(): String = servo.power.toString()


    fun setPowerCommand(power: Double): InstantCommand {
        return InstantCommand({ servo.power = power }, this)
    }
    /**
     * Completely stops the power given to the intake's servo
     */
    fun stopIntakeCommand(): Command {
        return setPowerCommand(0.0)
    }

    /**
     * Sets the power to 1, the maximum output permitted.
     */
    fun runIntake(): Command {
        return SequentialCommandGroup(
            setPowerCommand(1.0),
            WaitUntilCommand { triggerReader.wasJustReleased() }.andThen(stopIntakeCommand())
        )
    }

    /**
     * Inverts the motor's power by setting a negative power, therefore, it will go in the opposite direction
     */
    fun runOutTake(): Command {
        return SequentialCommandGroup(
            setPowerCommand(-1.0),
            WaitUntilCommand { triggerReader.wasJustReleased() }.andThen(stopIntakeCommand())
        )
    }

    init {
        configureServo()
    }
    val isMid = true
    /**
     * Configures the servo's Id, class and default direction
     */
    private fun configureServo() {

        // Gets the servo's Id and the class it belongs to
        servo = hardwareMap.get(CRServo::class.java, config.servoId)

        // Sets the servo's direction to the desired one
        servo.direction = config.servoDirection

    }
}