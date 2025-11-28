package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveTrain
{


    /* Public OpMode members. */
    private DcMotor  leftFrontDriveWheel   = null; //motor for left front wheel
    private DcMotor  leftBackDriveWheel   = null; //motor for left back wheel
    private DcMotor  rightFrontDriveWheel  = null; //motor for right front wheel
    private DcMotor  rightBackDriveWheel  = null; //motor for right back wheel

    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 28;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 54.8;     // No External Gearing. YES external: 5:1/4:1/3:1
    static final double     WHEEL_DIAMETER_INCHES   = 2.95276;     // For figuring circumference: - original 2.3622
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = -8;
    static final double     TURN_SPEED              = 0.5;

    public DriveTrain(HardwareMap hwMap) {
        // Define and Initialize Motors
        leftFrontDriveWheel  = hwMap.get(DcMotor.class, "leftFrontDriveWheel");
        leftBackDriveWheel  = hwMap.get(DcMotor.class, "leftBackDriveWheel");
        rightFrontDriveWheel = hwMap.get(DcMotor.class, "rightFrontDriveWheel");
        rightBackDriveWheel = hwMap.get(DcMotor.class, "rightBackDriveWheel");

        //left wheels are counterclockwise and because of 90 degree gearboxes, they all are forward
        leftFrontDriveWheel.setDirection(DcMotor.Direction.FORWARD);// Set to REVERSE if using AndyMark motors
        leftBackDriveWheel.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDriveWheel.setDirection(DcMotor.Direction.FORWARD);
        rightBackDriveWheel.setDirection(DcMotor.Direction.FORWARD);//set to FORWARDS if using AndyMark motors

        // Set all motors to zero power
        leftFrontDriveWheel.setPower(0);
        leftBackDriveWheel.setPower(0);
        rightFrontDriveWheel.setPower(0);
        rightBackDriveWheel.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFrontDriveWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDriveWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDriveWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDriveWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
    //Drives the robot
    //Forward = how fast to move forward
    //Right = how fast to move right
    //Clockwise = how fast to move clockwise
    public void drive(double forward, double right, double clockwise)
    {

        //Controls the speed for going across the field; can be changed if going too slow
        //strafe is lateral movement!!! Looking forward while moving sideways
        //if(gamepad1.right_bumper)
        //{
        //  forward /= 4;
        //   strafe /= 4;
        //   turn /= 4;
        //}

        double denominator = Math.max(Math.abs(forward) + Math.abs(right) + Math.abs(clockwise), 1);

        rightFrontDriveWheel.setPower((forward - right - clockwise) / denominator);
        leftFrontDriveWheel.setPower((forward + right + clockwise) / denominator);
        leftBackDriveWheel.setPower((forward - right +  clockwise) / denominator);
        rightBackDriveWheel.setPower((forward +  right - clockwise) / denominator);
    }

    public void runWithEncoders()
    {
        leftFrontDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(boolean isActive, double speed, double leftInches, double rightInches, double timeoutS, String direction) //seems super important so I will not be deleting
    {
        //target for the encoder (wheels turn yippee)
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        //direction variables for each wheel given the String direction
        int lbDirection;
        int rbDirection;
        int lfDirection;
        int rfDirection;


        if (direction.equals("FORWARD"))
        {
            lbDirection = 1;
            rbDirection = 1;
            lfDirection = 1;
            rfDirection = 1; //-1
        }
        else if (direction.equals("REVERSE"))
        {
            lbDirection = -1;
            rbDirection = -1;
            lfDirection = -1;
            rfDirection = -1; //1
        }
        else if (direction.equals("LEFT"))
        {
            lbDirection = 1; //1
            rbDirection =  -1; //-1
            lfDirection =  -1; //-1
            rfDirection = 1;
        }
        else if (direction.equals("RIGHT"))
        {
            lbDirection = -1;
            rbDirection = 1;
            lfDirection = 1;
            rfDirection = -1;
        }
        else if (direction.equals("TURNRIGHT"))
        {
            lbDirection = 1;
            rbDirection = -1;
            lfDirection = 1;
            rfDirection = -1;
        }
        else // (direction.equals("TURNLEFT"))
        {
            lbDirection = -1;
            rbDirection = 1;
            lfDirection = -1;
            rfDirection = 1;
        }

        // Ensure that the OpMode is still active
        if (isActive)
        {
            // Determine new target position, and pass to motor controller
            newLeftFrontTarget = leftFrontDriveWheel.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH)* lfDirection;
            newRightFrontTarget = rightFrontDriveWheel.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH)* rfDirection;
            newLeftBackTarget = leftBackDriveWheel.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH)* lbDirection;
            newRightBackTarget = rightBackDriveWheel.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH)* rbDirection;

            leftFrontDriveWheel.setTargetPosition(newLeftFrontTarget);
            rightFrontDriveWheel.setTargetPosition(newRightFrontTarget);
            leftBackDriveWheel.setTargetPosition(newLeftBackTarget);
            rightBackDriveWheel.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            leftFrontDriveWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDriveWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDriveWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDriveWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftFrontDriveWheel.setPower(Math.abs(speed));
            rightFrontDriveWheel.setPower(Math.abs(speed));
            leftBackDriveWheel.setPower(Math.abs(speed));
            rightBackDriveWheel.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (isActive &&
                    (runtime.seconds() < timeoutS) &&
                    (leftFrontDriveWheel.isBusy() && rightFrontDriveWheel.isBusy()) && (leftBackDriveWheel.isBusy() && rightBackDriveWheel.isBusy())){
            }

            // Stop all motion;
            leftFrontDriveWheel.setPower(0);
            rightFrontDriveWheel.setPower(0);
            leftBackDriveWheel.setPower(0);
            rightBackDriveWheel.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFrontDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBackDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
}
