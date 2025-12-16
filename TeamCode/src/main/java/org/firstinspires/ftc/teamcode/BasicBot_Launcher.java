package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

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
    private int state = 0; //0 - not launching; 1 - fly wheel is warming up; 2 - launching

    public BasicBot_Launcher(HardwareMap hwMap)
    {
        agitator  = hwMap.get(CRServo.class, "agitator");
        flywheel  = hwMap.get(DcMotor.class, "flywheel");
        shooterIntake  = hwMap.get(DcMotorEx.class, "shooterIntake");

        flywheel.setDirection(DcMotor.Direction.REVERSE);
        flywheel.setPower(0);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //sleep(250);


        shooterIntake.setDirection(DcMotor.Direction.REVERSE);
        shooterIntake.setPower(0);
        shooterIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);




    }
    private void setAgitatorSpeed(double speed)
    {
        agitator.setPower(speed);
    }

    private void setFlywheelSpeed(double speed)
    {
        flywheel.setPower(speed);
    }

    private void setShooterIntakeSpeed(double speed)
    {
        shooterIntake.setPower(speed);
    }

    void notLaunching()
    {
        state = 0;
        setMotors();
    }
    void warmingUp()
    {
        state = 1;
        setMotors();
    }
    void launching()
    {
       if(state == 1)
       {
           state = 2;
           setMotors();
       }
    }

    void setMotors()
    {
        if(state == 0)
        {
            setAgitatorSpeed(0);
            setFlywheelSpeed(0);
            setShooterIntakeSpeed(0);
        }
        else if(state == 1)
        {
            setFlywheelSpeed(0.75);
        }
        else if(state == 2)
        {
            setAgitatorSpeed(1);
            setShooterIntakeSpeed(0.5);
        }
    }

    boolean readyToLaunch()
    {
        return state == 0;
    }
    boolean isWarmingUp()
    {
        return state == 1;
    }

    int getState()
    {
        return state;
    }
}
