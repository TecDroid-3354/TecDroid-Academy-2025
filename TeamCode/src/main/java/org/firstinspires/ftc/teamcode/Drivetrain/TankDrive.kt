package org.firstinspires.ftc.teamcode.Drivetrain

import androidx.core.math.MathUtils
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.hardware.motors.Motor

/**
 * Creates a tank drive class which holds the necessary functions that may be needed in this subsystem
 * while binding a button to this
 */
class TankDrive (private val config: TankConfig, private val hardwareMap: HardwareMap) : SubsystemBase() {


    private lateinit var rightDrive: Motor
    private lateinit var leftDrive: Motor

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

        leftDrive.set(leftPower)
        rightDrive.set(rightPower)
    }

    /**
     * Gets the power that is being passed to the left motor
     */
    fun getLeftPower() = leftDrive.get()

    /**
     * Gets the power that is being passed to the right motor
     */
    fun getRightPower() = rightDrive.get()

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
        rightDrive = Motor(hardwareMap, config.rightDriveDeviceName, config.motorType)
        leftDrive = Motor(hardwareMap, config.leftDriveDeviceName, config.motorType)
        
        rightDrive.motor.direction = config.rightDirection
        leftDrive.motor.direction = config.leftDirection

    }

}