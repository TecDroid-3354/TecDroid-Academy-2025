package org.firstinspires.ftc.teamcode.driveTrain;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class TankDrive extends SubsystemBase {

    private Motor rightDrive;
    private Motor leftDrive;
    private final HardwareMap hardwareMap;

    public TankDrive(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        configureDcMotors();
    }

    public void drive(double drive, double turn) {
        double leftPower = MathUtils.clamp(drive + turn, -1.0, 1.0);
        double rightPower = MathUtils.clamp(drive - turn, -1.0, 1.0);

        leftDrive.set(leftPower);
        rightDrive.set(rightPower);
    }

    public Double[] getMotorsPower() {
        return new Double[]{leftDrive.motor.getPower(), rightDrive.motor.getPower()};
    }

    private void configureDcMotors() {
        rightDrive = new Motor(hardwareMap, "RMotor", Motor.GoBILDA.RPM_312);
        leftDrive = new Motor(hardwareMap, "LMotor", Motor.GoBILDA.RPM_312);

        rightDrive.motor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive.motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
