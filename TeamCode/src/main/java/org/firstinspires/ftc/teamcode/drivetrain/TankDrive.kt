package org.firstinspires.ftc.teamcode.drivetrain

import android.util.Range
import androidx.core.math.MathUtils
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.drivebase.DifferentialDrive
import com.seattlesolvers.solverslib.hardware.motors.Motor

class TankDrive (private val config: TankConfig, private val hardwareMap: HardwareMap) : SubsystemBase() {

    private lateinit var rightDrive: DcMotor
    private lateinit var leftDrive: DcMotor

    init {
        configureDcMotors()
    }


    fun drive(drive: Double, turn: Double) {


        // Setup a variable for each drive wheel to save power level for telemetry
        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        val leftPower = MathUtils.clamp(drive + turn, -1.0, 1.0 )
        val rightPower = MathUtils.clamp(drive - turn, -1.0, 1.0 )

        leftDrive.power = leftPower
        rightDrive.power = rightPower


    }
    fun getLeftPower() = leftDrive.power
    fun getRightPower() = rightDrive.power

    private fun configureDcMotors() {

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        rightDrive = hardwareMap.get(DcMotor::class.java, config.rightDriveDeviceName)
        leftDrive = hardwareMap.get(DcMotor::class.java, config.leftDriveDeviceName)

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        rightDrive.direction = config.rightDirection
        leftDrive.direction = config.leftDirection

    }

}