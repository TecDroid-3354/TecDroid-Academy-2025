package org.firstinspires.ftc.teamcode.arm.gripper

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.ServoEx
import com.seattlesolvers.solverslib.hardware.SimpleServo
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit

class Gripper(private val config: GripperConfig, private val hardwareMap: HardwareMap): SubsystemBase() {

    private lateinit var servo: ServoEx

    private var isOpen: Boolean = false

    fun open() {
        servo.takeUnless { isOpen }?.position = 1.0
        isOpen = true
    }

    fun close() {
        servo.takeIf { isOpen }?.position = 0.0
        isOpen = false
    }

    fun getPosition() = servo.position

    init {
        require(config.maximumAngleRange > config.minimumAngleRange && config.minimumAngleRange > 0) {
            "The maximum movement range must be greater than the minimum one, this one must be also greater than zero"
        }
        configureServo()
    }

    private fun configureServo() {

        servo = SimpleServo(hardwareMap, config.servoId, config.minimumAngleRange, config.maximumAngleRange,
            AngleUnit.DEGREES)

        servo.inverted = config.isInverted

    }
}