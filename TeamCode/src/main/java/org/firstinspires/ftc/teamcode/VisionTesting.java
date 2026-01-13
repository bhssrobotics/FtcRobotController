/* Copyright (c) 2017 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;


/**
* This file provides basic Telop driving for a Pushbot robot.
* The code is structured as an Iterative OpMode
*
* This OpMode uses the common Pushbot hardware class to define the devices on the robot.
* All device access is managed through the HardwarePushbot class.
*
* This particular OpMode executes a basic Tank Drive Teleop for a PushBot
* It raises and lowers the claw using the Gampad Y and A buttons respectively.
* It also opens and closes the claws slowly using the left and right Bumper buttons.
*
* Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
* Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
*/

@TeleOp(name="Steering Controls", group="Pushbot")
public class VisionTesting extends OpMode
{
    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    boolean targetFound     = false;    // Set to true when an AprilTag target is detected
    AprilTagPoseFtc pose;
    final float DESIRED_DISTANCE = 116;
    final double SPEED_GAIN =   0.02 ;   //  Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
    final double TURN_GAIN  =   0.01 ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
    final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
    final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_TURN  = 0.25;  //  Clip the turn speed to this max value (adjust for your robot)
    final double MAX_AUTO_STRAFE= 0.5;   //  Clip the strafing speed to this max value (adjust for your robot)
    double  drive           = 0;        // Desired forward power/speed (-1 to +1) +ve is forward
    double  turn            = 0;        // Desired turning power/speed (-1 to +1) +ve is CounterClockwise
    ElapsedTime runtime = new ElapsedTime();
    double delayTime = 0;
    final double PAUSE_TIME = 10;

    int drivingState= 0; //0 is normal driving, 1 is launching

    double forward; //strafing left and right
    double strafe=0; //
    double pivot;


    /*
    * Code to run ONCE when the driver hits INIT
    */
    //@Override
    public void init() 
    {
        /* Initialize the hardware variables.
        * The init() method of the hardware class does all the work here
        */
        robot.init(hardwareMap);
    }

    /*
    * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    */
    //@Override
    public void init_loop() 
    {
    }

    /*
    * Code to run ONCE when the driver hits PLAY
    */
    //@Override
    public void start() 
    {
    }

    /*
    * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    */
    // @Override
    public void loop() {

        if(drivingState==0)
        {
            if (gamepad1.bWasPressed()) //When the button on gamepad is pressed stuff below happens
            {
                telemetry.addData("\n>", "b was pressed");
                aprilTagAlignment(24); //Going to this method (code below)v
                drivingState = 1;
                delayTime= runtime.seconds() + PAUSE_TIME;
                // which equals pivot from our values
            } else if (gamepad1.xWasPressed()) //When the button on gamepad is pressed stuff below happens
            {
                telemetry.addData("\n>", "x was pressed");
                aprilTagAlignment(25); //Going to this method (code below)
                drivingState = 1;
                delayTime= runtime.seconds() + PAUSE_TIME;

            } else //If button not pressed, controls on gamepad work normally
            {
                forward = -gamepad1.left_stick_y; //Moving left and right
                strafe = gamepad1.left_stick_x; // Moving side to side
                pivot = gamepad1.right_stick_x; //backwards and forwards
            }
        }
        else
        {
            robot.driveTrain.drive(forward, strafe, pivot);
            robot.driveTrain.moveRobot(forward, strafe, pivot);
            if(runtime.seconds() >= delayTime)
            {
                drivingState = 0;
            }
        }

        //telemetry.addData("Driving","Forward %5.2f, Strafe %5.2f, Pivot %5.2f", forward, strafe, pivot);
        //telemetry.update();
    }
    /*
    private void telemetryAprilTag() { //Telemetry sends data to the driver hub and displays text on the screen
        List<AprilTagDetection> currentDetections = robot.visionControl.getCurrentDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method telemetryAprilTag()

     */

    private void aprilTagAlignment(int id){ //This is the method called above to calculate where robot needs to go based on desired distance
        // Tell the driver what we see, and what to do.
        pose = robot.visionControl.getDetectionsVal(id); //Getting values from VisionControl class of the April Tag ID
        if (pose!=null) { //If there are values
            targetFound = true; //Then target is found
            telemetry.addData("\n>","Target found\n"); //Hold down left bumper to activate code below

        } else { //If there aren't any values
            telemetry.addData("\n>","Drive using joysticks to find valid target\n"); //Then adjust to find values
            targetFound = false;
        }

        // If Left Bumper is being pressed, AND we have found the desired target, Drive to target Automatically .
        if (targetFound) {

            //Lots of math stuff
            // Determine heading and range error so we can use them to control the robot automatically.
            double  rangeError   = (pose.range - DESIRED_DISTANCE);
            double  headingError = pose.bearing;
            double  yawError     = pose.yaw;

            // Use the speed and turn "gains" to calculate how we want the robot to move.  Clip it to the maximum
            forward = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
            pivot  = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
            strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

            telemetry.addData("Auto","Drive %5.2f, Turn %5.2f", drive, turn);
        } else { //If left bumper not pressed and it doesn't find target then it will go back to normal driver controls but go slower

            // drive using manual POV Joystick mode.
            forward = -gamepad1.left_stick_y  / 2.0;  // Reduce drive rate to 50%.
            strafe = -gamepad1.left_stick_x  / 2.0;  // Reduce strafe rate to 50%.
            pivot  = -gamepad1.right_stick_x / 4.0;  // Reduce turn rate to 25%.
            telemetry.addData("Manual","Drive %5.2f, Turn %5.2f", forward, pivot);
        }
        telemetry.update();


    }
  }
