package org.firstinspires.ftc.teamcode.arm.Intake

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase

/**
 * Creates a class which holds the necessary functions that may be needed further in the code
 */
class Intake (private val config: IntakeConfig, val hardwareMap: HardwareMap): SubsystemBase() {
    private lateinit var servo: CRServo

    /**
     * Gets the current power that is being passed to the intake
     */
    fun getPower(): String = servo.power.toString()

    /**
     * Sets the power to 1, the maximum output permitted.
     */
    fun runIntake() {
        servo.power = 1.0
    }

    /**
     * Sets the power to the opposite of intake
     */
    fun runOutTake() {
        servo.power = -1.0
    }

    /**
     * Completely stops the power given to the intake's servo
     */
    fun stopIntake() {
        servo.power = 0.0
    }

    init {
        configureServo()
    }

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