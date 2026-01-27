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

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


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

@TeleOp(name="Steering With Vision", group="Pushbot")
public class VisionTesting extends OpMode
{
    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    boolean targetFound     = false;    // Set to true when an AprilTag target is detected
    AprilTagPoseFtc pose;
    final float DESIRED_DISTANCE = 112;
    final double DESIRED_YAW = -6.5;
    final double DESIRED_BEARING = 7.7;
    final double SPEED_GAIN =   0.02 ;   //  Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
    final double TURN_GAIN  =   0.04 ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.50 / 25.0)
    final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
    final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_TURN  = 0.15;  //  Clip the turn speed to this max value (adjust for your robot)
    final double MAX_AUTO_STRAFE= 0.5;   //  Clip the strafing speed to this max value (adjust for your robot)
    double  drive           = 0;        // Desired forward power/speed (-1 to +1) +ve is forward
    double  turn            = 0;        // Desired turning power/speed (-1 to +1) +ve is CounterClockwise
    ElapsedTime runtime = new ElapsedTime();
    double delayTime = 0;

    final double PAUSE_TIME = 5;

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
        runtime.reset();
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
        driveTestPivotCalc();

    }


    private void driveTestPivotCalc()
    {
        cameraCalibration();
 //       if(drivingState == 0)
//        {
            if (gamepad1.bWasPressed()) //When the button on gamepad is pressed stuff below happens
            {
                robot.visionControl.resumeStreaming();
                //telemetry.addData("\n>", "b was pressed");
                drivingState = calculatePivot(24); //Going to this method (code below)v
                delayTime = runtime.seconds() + PAUSE_TIME;

            } else if (gamepad1.xWasPressed()) //When the button on gamepad is pressed stuff below happens
            {
                robot.visionControl.resumeStreaming();
                //telemetry.addData("\n>", "b was pressed");
                drivingState = calculatePivot(20); //Going to this method (code below)v
            } else //If button not pressed, controls on gamepad work normally
            {
                double forward = -gamepad1.left_stick_y; //strafing left and right
                double strafe = -gamepad1.left_stick_x; //
                double turn = gamepad1.right_stick_x; //backwards and forwards

                robot.driveTrain.drive(forward, strafe, turn);
            }

            launch();
      //  }

    }


    private void driveTestContinuous() {
        cameraCalibration();
        if (gamepad1.dpad_right) //When the button on gamepad is pressed stuff below happens
        {
            //robot.visionControl.resumeStreaming();
            //telemetry.addData("\n>", "b was pressed");
            drivingState = fixPivot(24); //Going to this method (code below)v
            robot.driveTrain.drive(0, 0, pivot);
            //telemetry.addData("Driving", "Forward %5.2f, Strafe %5.2f, Pivot %5.2f", forward, strafe, pivot);
            //telemetry.update();

            // which equals pivot from our val ues
        } else if (gamepad1.dpad_left) //When the button on gamepad is pressed stuff below happens
        {
            robot.visionControl.resumeStreaming();
            telemetry.addData("\n>", "x was pressed");
            drivingState = fixPivot(20); //Going to this method (code below)
            //delayTime= runtime.seconds() + PAUSE_TIME;

            robot.driveTrain.drive(forward, strafe, pivot);
            telemetry.addData("\n>", "moveRobot was called");
            //            if(runtime.seconds() >= delayTime)
            //            {
            //                drivingState = 0;
            //            }


        } else //If button not pressed, controls on gamepad work normally
        {
            forward = -gamepad1.left_stick_y; //Moving left and right
            strafe = gamepad1.left_stick_x; // Moving side to side
            pivot = gamepad1.right_stick_x; //backwards and forwards
            robot.driveTrain.drive(forward, strafe, pivot);
        }

    }
    private void driveTestDrivingState()
    {
        //cameraCalibration();
        if(drivingState == 0)
        {
            if (gamepad1.bWasPressed()) //When the button on gamepad is pressed stuff below happens
            {
                //telemetry.addData("\n>", "b was pressed");
                drivingState = fixPivot(24); //Going to this method (code below)v
                //delayTime= runtime.seconds() + PAUSE_TIME;

                //robot.driveTrain.moveRobot(forward, pivot, strafe);
                //telemetry.addData("\n>", "moveRobot was called");

                //            if(runtime.seconds() >= delayTime)
                //            {
                //                drivingState = 0;
                //            }

                //telemetry.addData("Driving", "Forward %5.2f, Strafe %5.2f, Pivot %5.2f", forward, strafe, pivot);
                //telemetry.update();
                // which equals pivot from our val ues
            } else if (gamepad1.xWasPressed()) //When the button on gamepad is pressed stuff below happens
            {
                //telemetry.addData("\n>", "x was pressed");
                drivingState = fixPivot(20); //Going to this method (code below)
                //delayTime= runtime.seconds() + PAUSE_TIME;




            } else //If button not pressed, controls on gamepad work normally
            {
                double forward = -gamepad1.left_stick_y; //strafing left and right
                double strafe = -gamepad1.left_stick_x; //
                double turn = gamepad1.right_stick_x; //backwards and forwards

                robot.driveTrain.drive(forward, strafe, turn);
            }

            launch();
        }
        else {
            if(gamepad1.right_bumper)
            {
                drivingState = 0;
            }
            else {
                robot.driveTrain.drive(0, 0, -pivot);
                drivingState = fixPivot(drivingState);
                //telemetry.addData("Driving", "Forward %5.2f, Strafe %5.2f, Pivot %5.2f", forward, strafe, pivot);
                //telemetry.update();
            }
        }
    }

        //

    private void cameraCalibration() { //Telemetry sends data to the driver hub and displays text on the screen
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

        telemetry.update();

    }   // end method cameraCalibration()

    private int fixPivot(int id){ //This is the method called above to calculate where robot needs to go based on desired distance
        // Tell the driver what we see, and what to do.
        pose = robot.visionControl.getDetectionsVal(id); //Getting values from VisionControl class of the April Tag ID
        int foundtag = 0;
        if (pose!=null) { //If there are values
            targetFound = true; //Then target is found
            //telemetry.addData("\n>","Target found\n"); //Hold down left bumper to activate code below

            foundtag = id;

        } else { //If there aren't any values
            //telemetry.addData("\n>","Drive using joysticks to find valid target\n"); //Then adjust to find values
            targetFound = false;
            //robot.visionControl.stopStreaming();
            telemetry.addData("\n>", "target not found %d", foundtag);
        }

        // If Left Bumper is being pressed, AND we have found the desired target, Drive to target Automatically .
        if (targetFound) {

            //Lots of math stuff
            // Determine heading and range error so we can use them to control the robot automatically.
            //double  rangeError   = (pose.range - DESIRED_DISTANCE);
            double  headingError = (pose.bearing - DESIRED_BEARING);
            //double  yawError     = (pose.yaw - DESIRED_YAW);

            // Use the speed and turn "gains" to calculate how we want the robot to move.  Clip it to the maximum
            //forward = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
            pivot  = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
            //strafe = Range.clip(yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

           //off by ~4.5/5.

            //CHANGE THIS
            if (Math.abs(headingError) <= 1.5) {
                telemetry.addData("\n>", "heading error less than 1.5");
                foundtag = 0;
                //robot.visionControl.stopStreaming();
            }

            //telemetry.addData("Auto","Re %5.2f, He %5.2f, Ye %5.2f", rangeError, headingError, yawError);
            telemetry.addData("Auto","Pose bearing %5.2f, Heading error %5.2f, Pivot %5.2f", pose.bearing, headingError, pivot);
            telemetry.update();
        }


        return foundtag;
    }

    private int calculatePivot(int id){ //This is the method called above to calculate where robot needs to go based on desired distance
        // Tell the driver what we see, and what to do.
        pose = robot.visionControl.getDetectionsVal(id); //Getting values from VisionControl class of the April Tag ID
        double arcLength = 0;
        int foundtag = 0;
        if (pose!=null) { //If there are values
            targetFound = true; //Then target is found
            //telemetry.addData("\n>","Target found\n"); //Hold down left bumper to activate code below
            arcLength = 0;
            foundtag = id;

        } else { //If there aren't any values
            //telemetry.addData("\n>","Drive using joysticks to find valid target\n"); //Then adjust to find values
            targetFound = false;
            //robot.visionControl.stopStreaming();
            telemetry.addData("\n>", "target not found %d", id);
            foundtag = 0;
        }

        // If Left Bumper is being pressed, AND we have found the desired target, Drive to target Automatically .
        if (targetFound) {

            //Lots of math stuff
            // Determine heading and range error so we can use them to control the robot automatically.
            //double  rangeError   = (pose.range - DESIRED_DISTANCE);
            double  headingError = (pose.bearing - DESIRED_BEARING);
            //double  yawError     = (pose.yaw - DESIRED_YAW);
            String direction = "";

            arcLength = 2 * Math.PI * 15 * (Math.abs(headingError) / 360);
            if(headingError > 0)
                robot.driveTrain.encoderDrive(true, MAX_AUTO_TURN, arcLength, PAUSE_TIME, "TURNLEFT");
            else if(headingError < 0)
                robot.driveTrain.encoderDrive(true, MAX_AUTO_TURN, arcLength, PAUSE_TIME, "TURNRIGHT");

            // Use the speed and turn "gains" to calculate how we want the robot to move.  Clip it to the maximum
            //forward = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
            //pivot  = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
            //strafe = Range.clip(yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);





            //telemetry.addData("Auto","Re %5.2f, He %5.2f, Ye %5.2f", rangeError, headingError, yawError);
            telemetry.addData("Auto","Pose bearing %5.2f, Heading error %5.2f, arcLength %5.2f, delayTime %5.2f", pose.bearing, headingError, arcLength, delayTime);
            telemetry.update();
        }


        return foundtag;
    }



    private int aprilTagAlignment(int id){ //This is the method called above to calculate where robot needs to go based on desired distance
        // Tell the driver what we see, and what to do.
        pose = robot.visionControl.getDetectionsVal(id); //Getting values from VisionControl class of the April Tag ID
        int foundtag = 0;
        if (pose!=null) { //If there are values
            targetFound = true; //Then target is found
            //telemetry.addData("\n>","Target found\n"); //Hold down left bumper to activate code below

            foundtag = id;

        } else { //If there aren't any values
            //telemetry.addData("\n>","Drive using joysticks to find valid target\n"); //Then adjust to find values
            targetFound = false;
        }

        // If Left Bumper is being pressed, AND we have found the desired target, Drive to target Automatically .
        if (targetFound) {

            //Lots of math stuff
            // Determine heading and range error so we can use them to control the robot automatically.
            double  rangeError   = (pose.range - DESIRED_DISTANCE);
            double  headingError = (pose.bearing - DESIRED_BEARING);
            double  yawError     = (pose.yaw - DESIRED_YAW);

            // Use the speed and turn "gains" to calculate how we want the robot to move.  Clip it to the maximum
            forward = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
            pivot  = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
            strafe = Range.clip(yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);



            telemetry.addData("Auto","Re %5.2f, He %5.2f, Ye %5.2f", rangeError, headingError, yawError);
            telemetry.addData("Auto","F %5.2f, P %5.2f, S %5.2f", forward, pivot, strafe);
        }
        telemetry.update();

        return foundtag;
    }

    public void launch()
    {
        //press the button to start the launcher code
        if (gamepad2.b) //launch
        {
            if(robot.launcher.isWarmingUp())
                robot.launcher.launching();
        }//end of gamepad 2 right bumper
        else if (gamepad2.right_bumper)
        {
            if(robot.launcher.readyToLaunch())
                robot.launcher.warmingUp();
        }
        else if (gamepad2.left_bumper) //if i press the left bumper
        {
            //turn off the launching motor
            robot.launcher.notLaunching();
        }//end of gamepad 2 left bumper

        if(gamepad2.y)
        {
            robot.launcher.turnOffAgitatorIntake(); //pressing y turns off the agitator and the intake
        }
        if(gamepad2.a)
        {
            robot.launcher.changeAgitatorDirection(); //pressing a makes the agitator rotate in the other direction (for getting balls unstuck)
            robot.launcher.setAgitatorSpeed(1); //speed needs to be reset everytime due to state code
        }

    } //end of launch
    }


