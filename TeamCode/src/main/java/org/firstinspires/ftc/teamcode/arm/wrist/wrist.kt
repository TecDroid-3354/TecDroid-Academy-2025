package org.firstinspires.ftc.teamcode.arm.wrist

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.ServoEx


class wrist (private val config: WristConfig, private val hardwareMap: HardwareMap ): SubsystemBase() {

    private lateinit var servo: ServoEx

    private fun setAngle(degrees: Double) {
       servo.rotateByAngle(degrees)
    }

    init {
        configureServo()
    }
    private fun configureServo () {
        servo = hardwareMap.get(ServoEx::class.java, config.servoId)

        servo.inverted = config.isInverted

        servo.setRange(wristConfig.minimumMovementRange, wristConfig.maximumMovementRange)
    }
}