package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BasicBot_Launcher {
    private CRServo agitator = null;
    private DcMotor flywheel = null;
    private DcMotorEx shooterIntake = null;
    private int state = 0; //0 - not launching; 1 - fly wheel is warming up; 2 - launching; 3 - turn off agitator and intake
    private boolean isAgitatorForwards = true;
    private boolean launchingShort = false;

    public BasicBot_Launcher(HardwareMap hwMap) {
        agitator = hwMap.get(CRServo.class, "agitator");
        flywheel = hwMap.get(DcMotor.class, "flywheel");
        shooterIntake = hwMap.get(DcMotorEx.class, "shooterIntake");

        flywheel.setDirection(DcMotor.Direction.REVERSE);
        flywheel.setPower(0);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //sleep(250);


        shooterIntake.setDirection(DcMotor.Direction.REVERSE);
        shooterIntake.setPower(0);
        shooterIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public void setAgitatorSpeed(double speed) {
        if (isAgitatorForwards) {
            agitator.setPower(speed); //forwards
        } else {
            agitator.setPower(-speed); //backwards
        }
    }

    private void setFlywheelSpeed(double speed) {
        flywheel.setPower(speed);
    }

    private void setShooterIntakeSpeed(double speed) {
        shooterIntake.setPower(speed);
    }

    void changeAgitatorDirection() {
        if (isAgitatorForwards) //change forwards to backwards and vice versa
        {
            isAgitatorForwards = false;
        } else {
            isAgitatorForwards = true;
        }
    }

    void notLaunching() {
        state = 0;
        setMotors();
    }

    void warmingUp() {
        state = 1;
        setMotors();
    }

    void launching() {
        if (state == 1) {
            state = 2;
            setMotors();
        }
    }

    void turnOffAgitatorIntake() {
        if (state == 2) {
            state = 1;
            setMotors();
        }
    }

    void launchShort()
    {
        launchingShort = true;
    }
    void launchLong()
    {
        launchingShort = false;
    }

        void setMotors()
        {
            if (state == 0) {
                setAgitatorSpeed(0);
                setFlywheelSpeed(0);
                setShooterIntakeSpeed(0);
            } else if (state == 1) {
                setAgitatorSpeed(0);
                setShooterIntakeSpeed(0);

                if (launchingShort) //if launching short, set flyWheel to slower speed (.6) in order to launch shorter
                {
                    setFlywheelSpeed(0.60);
                } else {
                    setFlywheelSpeed(0.75); //if launching long (as per usual), set flyWheel to regular speed (.75)
                }
            } else if (state == 2) {
                setAgitatorSpeed(1);
                setShooterIntakeSpeed(0.5);
            }
        }

        void getAgitatorSpeed ()
        {

        }

        boolean readyToLaunch ()
        {
            return state == 0;
        }
        boolean isWarmingUp ()
        {
            return state == 1;
        }

        int getState ()
        {
            return state;
        }
    }

