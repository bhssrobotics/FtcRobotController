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

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

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
public class SteeringControls extends OpMode
{
    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    double speed=0;

    final double RED_DESIRED_BEARING = 9; //If on red alliance, use this one to shoot towards your goal. Desired bearing means how much the robot has to turn to align with April Tag so it can launch accurately. Team figure out desired bearing by testing.
    final double BLUE_DESIRED_BEARING = -3.2; //If on blue alliance, use this one to shoot towards your goal. Desired bearing means how much the robot has to turn to align with April Tag so it can launch accurately. Team figure out desired bearing by testing.
    final double MAX_AUTO_TURN  = 0.25;  //Clip the turn speed to this max value (adjust for your robot) which basically means how fast the robot turns when it aligns with robot.
    int drivingState = 0; //Calculates red bearing and blue bearing (see below) so have to initialize it here

    public boolean flywheelOn;
    public boolean intakeOn;


    ElapsedTime runtime = new ElapsedTime();
    double delayTime = 0; //Used later in code so have to initialize it here
    final double PAUSE_TIME = 5; //Used later in code so have to initialize it here

    final int RED_GOAL_ID = 24; //Every April Tag has a certain ID so that the webcam can differentiate between several. This was the ID for our red alliance goal.
    final int BLUE_GOAL_ID = 20; //Every April Tag has a certain ID so that the webcam can differentiate between several. This was the ID for our blue alliance goal.

    final int ROBOT_LENGTH = 15;
    /*
    * Code to run ONCE when the driver hits INIT
    */
    //@Override
    public void init() 
    {
        /* Initialize the hardware variables. So all these values above I'm assuming.
        * The init() method of the hardware class does all the work here
        */
        robot.init(hardwareMap);
    }//end of init

    /*
    * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    */
    //@Override
    public void init_loop() 
    {
    }//end of init loop

    /*
    * Code to run ONCE when the driver hits PLAY
    */
    //@Override
    public void start() 
    {
    }//end of start

    /*
    * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    */
    // @Override
    public void loop()
    {
        drive();
        launch();
    } //end of loop

    public void drive()
    {
        joystickDrive();
    }

    public void joystickDrive() //gamepad controls
    {
        double forward = -gamepad1.left_stick_y; //strafing left and right
        double strafe = -gamepad1.left_stick_x; //?
        double turn = gamepad1.right_stick_x; //backwards and forwards

        robot.driveTrain.drive(forward, strafe, turn);
    }//end of drive

    public void launch()
    {
        if(gamepad2.b) {
            if (intakeOn) {
                intakeOn = false;
                robot.launcher.setShooterIntakeSpeed(0);
            } else {
                intakeOn = true;
                robot.launcher.setShooterIntakeSpeed(0.5);
            }
        }
        if(gamepad2.y) {
            if (flywheelOn) {
                flywheelOn = false;
                robot.launcher.setFlywheelSpeed(0);
            } else {
                flywheelOn = true;
                robot.launcher.setFlywheelSpeed(0.5);
            }
        }
        } //end of launch
            
            /*private int calculatePivot(String id, double bearing){ //This is the method called above to calculate how far robot needs to turn based on desired bearing
        // Tell the driver what we see, and what to do.
        boolean targetFound     = false;    // Set to true when an AprilTag target is detected
        AprilTagPoseFtc pose;

        pose = robot.visionControl.getDetectionsVal(id); //Getting values from Vision Control class of the April Tag ID
        double arcLength = 0;
        int foundtag = 0;
        if (pose!=null) { //If there are values
            targetFound = true; //Then target is found and bearing can be calculated by pressing correct button depending on alliance
            foundtag = id;

        } else { //If there aren't any values
            //telemetry.addData("\n>","Drive using joysticks to find valid target\n"); //Then need to adjust robot to find values (helpful tips: check how webcam is positioned, and check where robot is on field - is it far back enough from April Tag and facing it properly?)
            targetFound = false;
            //robot.visionControl.stopStreaming();
            telemetry.addData("\n>", "target not found %d", id);
            foundtag = 0;
        }

        if (targetFound) {

            //find the error (how much our bearing is off by so desired bearing can be calculated)
            double  headingError = (pose.bearing - bearing);

            //calculate the arc length based on the length of the back wheel to the camera (math)
            arcLength = 2 * Math.PI * ROBOT_LENGTH * (Math.abs(headingError) / 360);

            //turn based on what our error was (don't do anything if 0 because then we are at desired bearing)
            if(headingError > 0)
            {
                robot.driveTrain.encoderDrive(true, MAX_AUTO_TURN, arcLength, PAUSE_TIME, "TURNLEFT"); //if heading error is greater than 0 then too far to the right of desired bearing
            }
            else if(headingError < 0)
            {
                robot.driveTrain.encoderDrive(true, MAX_AUTO_TURN, arcLength, PAUSE_TIME, "TURNRIGHT"); //if heading error is greater than 0 then too far to the left of desired bearing
            }

            telemetry.addData("Auto","Pose bearing %5.2f, Heading error %5.2f, arcLength %5.2f, delayTime %5.2f", pose.bearing, headingError, arcLength, delayTime); //telemetry to get it to work
            telemetry.update(); //So telemetry keeps updating if things change I assume
        }


        return foundtag; //I believe display whether ID is detected or not
    }
*/
           




} //end of steering controls
  
