package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BasicBot_Launcher
{
    private CRServo agitator = null;
    private DcMotor flywheel = null;
    private DcMotorEx shooterIntake = null;

    public BasicBot_Launcher(HardwareMap hwMap, double agitatorSpeed, double flywheelSpeed , double shooterIntakeSpeed)
    {
        agitator  = hwMap.get(CRServo.class, "agitator");
        flywheel  = hwMap.get(DcMotor.class, "flywheel");
        shooterIntake  = hwMap.get(DcMotorEx.class, "shooterIntake");

        flywheel.setDirection(DcMotor.Direction.REVERSE);
        flywheel.setPower(0);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shooterIntake.setDirection(DcMotor.Direction.REVERSE);
        shooterIntake.setPower(0);
        shooterIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setAgitatorSpeed(agitatorSpeed);
        setFlywheelSpeed(flywheelSpeed);
        setShooterIntakeSpeed(shooterIntakeSpeed);


    }
    void setAgitatorSpeed(double speed)
    {
        agitator.setPower(speed);
    }

    void setFlywheelSpeed(double speed)
    {
        flywheel.setPower(speed);
    }

    void setShooterIntakeSpeed(double speed)
    {
        shooterIntake.setPower(speed);
    }
}
