/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import org.firstinspires.ftc.vision.VisionPortal;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Disabled
@Autonomous(name ="Auto Right", group = "Concept")
//USING THIS ONE. PARK


public class AutoRight extends LinearOpMode {

    private DcMotor         leftFrontDriveWheel   = null;
    private DcMotor         rightFrontDriveWheel  = null;
    private DcMotor         leftBackDriveWheel = null;
    private DcMotor         rightBackDriveWheel = null;

    private ElapsedTime     runtime = new ElapsedTime();
    
    static final double     COUNTS_PER_MOTOR_REV    = 28;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 13.1;     // No External Gearing. TEST AS WE GO
    static final double     WHEEL_DIAMETER_INCHES   = 2.3622;     // For figuring circumference:
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = -8;
    static final double     TURN_SPEED              = 0.5;

    static final double MID_SERVO = 0.0;
    
    static public double          clawOffset = 0.2;

    @Override
    public void runOpMode() {
        
         // Initialize the drive system variables.
        leftFrontDriveWheel  = hardwareMap.get(DcMotor.class, "leftFrontDriveWheel");
        rightFrontDriveWheel = hardwareMap.get(DcMotor.class, "rightFrontDriveWheel");
        leftBackDriveWheel = hardwareMap.get(DcMotor.class, "leftBackDriveWheel");
        rightBackDriveWheel = hardwareMap.get(DcMotor.class, "rightBackDriveWheel");
        
        //TEST
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDriveWheel.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDriveWheel.setDirection(DcMotor.Direction.REVERSE);
        leftBackDriveWheel.setDirection(DcMotor.Direction.FORWARD);
        rightBackDriveWheel.setDirection(DcMotor.Direction.REVERSE);

        leftFrontDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDriveWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDriveWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();
        
        while (opModeIsActive()) 
        { 
          park();
          sleep(10000000);
          
        telemetry.update();
     }
    }
  // end runOpMode()
    //  *  Method to perform a relative move, based on encoder counts.
    //  *  Encoders are not reset as the move is based on the current position.
    //  *  Move will stop if any of three conditions occur:
    //  *  1) Move gets to the desired position
    //  *  2) Move runs out of time
    //  *  3) Driver stops the OpMode running.
    //  */
    
    public void park()
    {
      encoderDrive(60, 10, 10, 30, "REVERSE"); //go forward to hang
          encoderDrive(60, 150, 150, 30, "LEFT"); //go to park position
          encoderDrive(60, 10, 10, 30, "FORWARD");
    }
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS, String direction)
    {
      //target for the encoder
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;
        
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
      if (opModeIsActive()) 
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
            while (opModeIsActive() &&
                  (runtime.seconds() < timeoutS) &&
                  (leftFrontDriveWheel.isBusy() && rightFrontDriveWheel.isBusy()) && (leftBackDriveWheel.isBusy() && rightBackDriveWheel.isBusy())){

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftFrontTarget,  newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                                            leftFrontDriveWheel.getCurrentPosition(), rightFrontDriveWheel.getCurrentPosition() ,leftBackDriveWheel.getCurrentPosition(), rightBackDriveWheel.getCurrentPosition());
                telemetry.update();
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

            sleep(250);   // optional pause after each move.
        }
    }
  }
