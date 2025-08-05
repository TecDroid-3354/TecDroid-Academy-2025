package org.firstinspires.ftc.teamcode.arm.wrist

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.ServoEx
import com.seattlesolvers.solverslib.util.MathUtils


class Wrist (private val config: WristConfig, private val hardwareMap: HardwareMap ): SubsystemBase() {

    private lateinit var servo: ServoEx

    init {
        require(config.maximumMovementRange > config.minimumMovementRange && config.minimumMovementRange > 0) {
            "Maximum angle range must be greater than the minimum angle. This last one must be greater than zero"
        }
        configureServo()
    }

    fun setAngle(degrees: Double) {
        servo.turnToAngle(degrees)
    }

    private fun configureServo () {
        
        servo = hardwareMap.get(ServoEx::class.java, config.servoId)

        servo.inverted = config.isInverted

        servo.setRange(wristConfig.minimumMovementRange, wristConfig.maximumMovementRange)
    }
}