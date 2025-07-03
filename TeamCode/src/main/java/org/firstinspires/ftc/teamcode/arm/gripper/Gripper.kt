package org.firstinspires.ftc.teamcode.arm.gripper

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.seattlesolvers.solverslib.command.Command
import com.seattlesolvers.solverslib.command.InstantCommand
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.ServoEx
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.ServoCommands
import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware

class Gripper(private val config: GripperConfig, private val hardwareMap: HardwareMap): SubsystemBase() {

    private lateinit var servo: ServoEx

    private var isOpen = false

    private fun open() {
        if (!isOpen) {
            servo.position = 1.0
        }
        isOpen = true
    }

    private fun close() {
        if (isOpen) {
            servo.position = 0.0
        }
        isOpen = false
    }

    fun getPosition() = servo.position

    init {
        configureServo()
    }
    private fun configureServo() {
        servo = hardwareMap.get(ServoEx::class.java, config.servoId)

        servo.inverted = config.isInverted

        servo.setRange(config.minimumMovementRange, config.maximumMovementRange)
    }
}