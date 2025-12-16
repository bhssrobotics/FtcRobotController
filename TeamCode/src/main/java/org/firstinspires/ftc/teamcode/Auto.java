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

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
* This OpMode illustrates the basics of TensorFlow Object Detection,
* including Java Builder structures for specifying Vision parameters.
*
* Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
* Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
*/
@Autonomous(name = "Auto", group = "Concept")
//USING THIS ONE. ARM

public class Auto extends LinearOpMode {
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);
        robot.driveTrain.runWithEncoders(); //switch to running with encoders

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();
        
        while (opModeIsActive())
        {
            //put in auto path of choice here
            redInnerForwardClose();

          
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
    
    public void test() //used to be moveToBasket, old code reflects that, kept it as an example
          {
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 65, 30, "REVERSE"); //starts backwards so we gotta
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 35, 30, "TURNRIGHT"); //turn it around
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 85, 30, "FORWARD");
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,30,30,"RIGHT");
            sleep(250);   // optional pause after each move.
          }

        public void blueInnerSidewaysLaunchClose()
        {
            //launch (vaguely to the left - maybe turn?), strafe left from start, get in the way
            //of red alliance's launch zone (see Emily's diagrams), then straighten out

            //launch stuff here #figure out later - sleep commented out also for testing purposes
            //sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"LEFT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
            sleep(100000000);   // sleep until teleOp mode
        }
          public void redInnerSidewaysLaunchClose()
          {
              //launch (vaguely to the right - maybe turn? strafe right from start, get in the way
              //of blue alliance's launch zone (see Emily's diagrams), then straighten out

              //launch stuff here #figure out later - sleep commented out also for testing purposes
              //sleep(250);   // optional pause after each move.
              robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"RIGHT");
              sleep(250);   // optional pause after each move.
              robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
              sleep(10000000);   // optional pause after each move.
          }

         public void blueInnerForwardLaunchClose()
         {
            //launch (vaguely to the left - maybe turn?, move forward from start

            //launch stuff here #figure out later - sleep commented out also for testing purposes
             //sleep(250);   // optional pause after each move.
             robot.driveTrain. encoderDrive(opModeIsActive(),60,21,30,"FORWARD");
             sleep(10000000);   // optional pause after each move.
         }

        public void redInnerForwardLaunchClose()
        {
            //launch (vaguely to the right - maybe turn?, move forward from start

            //launch stuff here #figure out later - sleep commented out also for testing purposes
            //sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,21,30,"FORWARD");
            sleep(1000000000);   // optional pause after each move.
        }

        public void blueInnerSidewaysClose()
        {
            //strafe left from start, get in the way of other alliance's launch zone
            //(see Emily's diagrams), then straighten out

            robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"LEFT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }

        public void redInnerSidewaysClose()
        {
            //strafe right from start, get in the way of other alliance's launch zone
            //(see Emily's diagrams), then straighten out

            robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"RIGHT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }

        public void blueInnerForwardClose()
        {
            //move forward from start - combine with red version?

            robot.driveTrain. encoderDrive(opModeIsActive(),60,21,30,"FORWARD");
            sleep(10000000);   // optional pause after each move.
        }

        public void redInnerForwardClose()
        {
            //move forward from start - combine with blue version?

            robot.driveTrain. encoderDrive(opModeIsActive(),60,21,30,"FORWARD");
            sleep(100000000);   // optional pause after each move.
        }

        public void blueOuterSidewaysLaunchClose()
        {
            //launch (vaguely to the left - maybe turn?, strafe left from start, avoid red
            //(veer slightly right) alliance's base (see Emily's diagrams), then straighten out

            //launch stuff here #figure out later - sleep commented out also for testing purposes
            //sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"LEFT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1,30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }

        public void redOuterSidewaysLaunchClose()
        {
            //launch (vaguely to the right - maybe turn?, strafe right from start, avoid blue
            //(veer slightly left) alliance's base (see Emily's diagrams), then straighten out

            //launch stuff here #figure out later - sleep commented out also for testing purposes
            //sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"RIGHT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }

        public void blueOuterForwardLaunchClose()
        {
            //launch (vaguely to the left - maybe turn?, move forward from start, and avoid red
            //(veer slightly right) alliance's base (see Emily's diagrams)

            //launch stuff here #figure out later - sleep commented out also for testing purposes
            //sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,3,30,"RIGHT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,24,30,"FORWARD");
            sleep(10000000);   // optional pause after each move.
        }

        public void redOuterForwardLaunchClose()
        {
            //launch (vaguely to the right - maybe turn?, move forward from start, and avoid blue
            //(veer slightly left) alliance's base (see Emily's diagrams)

            //launch stuff here #figure out later - sleep commented out also for testing purposes
            //sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,3,30,"LEFT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,24,30,"FORWARD");
            sleep(10000000);   // optional pause after each move.
        }

        public void blueOuterSidewaysClose()
        {
            //strafe left from start, avoid red (veer slightly right) alliance's base (see Emily's diagrams), then
            //straighten out

            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"LEFT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }

        public void redOuterSidewaysClose()
        {
            //strafe right from start, avoid (veer slightly left) blue alliance's base (see Emily's diagrams), then
            //straighten out

            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"RIGHT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }

        public void blueOuterForwardClose()
        {
            //move forward from start and avoid (veer slightly right) red alliance's base (see Emily's diagrams)

            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"RIGHT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }

        public void redOuterForwardClose()
        {
            //move forward from start and avoid (veer slightly left) blue alliance's base (see Emily's diagrams)

            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"LEFT");
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1,30, "REVERSE");
            sleep(10000000);   // optional pause after each move.
        }
  }
// end class
