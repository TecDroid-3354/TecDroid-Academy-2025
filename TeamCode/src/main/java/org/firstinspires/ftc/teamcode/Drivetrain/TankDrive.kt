package org.firstinspires.ftc.teamcode.Drivetrain

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase

/**
 * Creates a tank drive class which holds the necessary functions that may be needed in this subsystem
 * while binding a button to this
 */
class TankDrive (private val config: TankConfig, private val hardwareMap: HardwareMap) : SubsystemBase() {


    private lateinit var rightDrive: DcMotor
    private lateinit var leftDrive: DcMotor

    /**
     * Creates a function named drive that does the tank drive logic by applying the same linear power to
     * both but adding an angular power to one side and subtracting it from the other
     */
    fun drive(drive: Double, turn: Double) {

        // Setup a variable for each drive wheel to save power level for telemetry
        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        val leftPower = MathUtils.clamp(drive + turn, -1.0, 1.0 )
        val rightPower = MathUtils.clamp(drive - turn, -1.0, 1.0 )

        leftDrive.power = leftPower
        rightDrive.power = rightPower
    }

    /**
     * Gets the power that is being passed to the left motor
     */
    fun getLeftPower() = leftDrive.power

    /**
     * Gets the power that is being passed to the right motor
     */
    fun getRightPower() = rightDrive.power

    init {
        configureDcMotors()
    }

    /**
     * Configures both motors with their respective Id, class and direction.
     */
    private fun configureDcMotors() {

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        rightDrive = hardwareMap.get(DcMotor::class.java, config.rightDriveDeviceName)
        leftDrive = hardwareMap.get(DcMotor::class.java, config.leftDriveDeviceName)

        rightDrive.direction = config.rightDirection
        leftDrive.direction = config.leftDirection

    }

}