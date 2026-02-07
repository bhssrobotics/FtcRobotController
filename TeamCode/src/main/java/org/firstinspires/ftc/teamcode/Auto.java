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

//Important note for those who need help understanding different parts of game, this code is for the autonomous 'auto' period which means at the beginning of each match, the robot operates just from programming rather than being driven by a driver
//The OpMode for driving the robot is called TeleOp and the code for that can be found primarily in Steering Controls
//Emily did SUCH a fantastic job with this code!!!!!!!!!

@Autonomous(name = "Auto", group = "Concept")
//USING THIS ONE. ARM

public class Auto extends LinearOpMode {
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware

    ElapsedTime runtime = new ElapsedTime();
    double delayTime = 0; //Used later in code so have to initialize it here
    final double PAUSE_TIME = 8; //Used later in code so have to initialize it here

    @Override
    public void runOpMode() //Display OpMode on driver hub
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
            //put in auto path of choice here depending on alliance colour, team, and discussed strategy during auto
            //keep in mind there are about 16 auto paths so make sure you pay attention to what each path is doing (ie. do not confuse outer paths with inner paths or do not choose path that launches if you determined with alliance that you shouldn't launch)
            blueOuterSidewaysLaunchClose(); //current selected auto path

          
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

    public void launch()
    {
        robot.launcher.warmingUp(); //fly wheel starts first so that it has time to get to correct speed

        sleep((int)PAUSE_TIME*500); //sleep for 5s (multiplies by 1000 as sleep is in milliseconds)

        robot.launcher.launching();

        sleep((int)PAUSE_TIME*1000); //sleep for 5s (multiplies by 1000 as sleep is in milliseconds)
        //coded out is a while loop version
        //int forceQuitMan = 0;
        //delayTime = runtime.seconds() + PAUSE_TIME;

        /*while(!(runtime.seconds() >= delayTime) || forceQuitMan < 1500)
        {
            //NO
            forceQuitMan++;
        }//end of if is warming up
        */
    }

    public void stopLaunch() //Basically what it says, method to stop launching
    {
        robot.launcher.notLaunching();
    }
        public void blueInnerSidewaysLaunchClose()
        {
            //launch (vaguely to the left - maybe turn?), strafe left from start, get in the way of red alliance's loading zone (see Emily's diagrams), then straighten out

            //this version contains rotation that hasn't been tested, please test first before adding rotations to the rest.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 6, 30, "TURNLEFT"); //rotate slightly for aim to the left
            sleep(250);   // optional pause after each move.
            launch(); //launch stuff
            stopLaunch(); //stop launching (launcher turns off)
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 6, 30, "TURNRIGHT"); //rotate back to be in position after launching by turning right
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"LEFT"); //Strafe to the left
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "REVERSE"); //I'm assuming it means move backwards
            sleep(100000000);   // sleep until auto is over
        }
          public void redInnerSidewaysLaunchClose()
          {
              //launch (vaguely to the right - maybe turn? strafe right from start, get in the way of blue alliance's loading zone (see Emily's diagrams), then straighten out

              robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
              sleep(250);   // optional pause after each move.
              robot.driveTrain.encoderDrive(opModeIsActive(),60, 6, 30, "TURNRIGHT"); //rotate slightly for aim to the right
              sleep(250);   // optional pause after each move.
              launch(); //launch stuff
              stopLaunch(); //stop launching (launcher turns off)
              sleep(250);   // optional pause after each move.
              robot.driveTrain.encoderDrive(opModeIsActive(),60, 6, 30, "TURNLEFT"); //rotate back to be in position after launching by turning left
              sleep(250);   // optional pause after each move.
              robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"RIGHT"); //Strafe to the right
              sleep(250);   // optional pause after each move.
              robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "REVERSE"); //I'm assuming it means move backwards
              sleep(10000000);   // sleep until auto is over
          }

         public void blueInnerForwardLaunchClose()
         {
            //launch (vaguely to the left - maybe turn?, move forward from start

             robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
             sleep(250);   // optional pause after each move.
             robot.driveTrain.encoderDrive(opModeIsActive(),60, 4, 30, "TURNLEFT"); //rotate slightly for aim to the left
             sleep(250);   // optional pause after each move.
             launch(); //launch stuff
             stopLaunch(); //stop launching (launcher turns off)
             sleep(250);   // optional pause after each move.
             robot.driveTrain.encoderDrive(opModeIsActive(),60, 4, 30, "TURNRIGHT"); //rotate back to be in position after launching by turning right
             sleep(250);   // optional pause after each move.
             robot.driveTrain. encoderDrive(opModeIsActive(),60,21,30,"FORWARD"); //Move forward
             sleep(10000000);   // sleep until auto is over
         }

        public void redInnerForwardLaunchClose()
        {
            //launch (vaguely to the right - maybe turn?, move forward from start

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 6, 30, "TURNRIGHT"); //rotate slightly for aim to the right
            sleep(250);   // optional pause after each move.
            launch(); //launch stuff
            stopLaunch(); //stop launching (launcher turns off)
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 6, 30, "TURNLEFT"); //rotate back to be in position after launching by turning to the left
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,21,30,"FORWARD"); //Move forward
            sleep(1000000000);   // sleep until auto is over
        }

        public void blueInnerSidewaysClose()
        {
            //strafe left from start, get in the way of other alliance's loading zone (see Emily's diagrams), then straighten out

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"LEFT"); //Strafe left
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "REVERSE"); //I'm assuming move backwards
            sleep(10000000);   // sleep until auto is over
        }

        public void redInnerSidewaysClose()
        {
            //strafe right from start, get in the way of other alliance's loading zone
            //(see Emily's diagrams), then straighten out

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,25,30,"RIGHT"); //Strafe right
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "REVERSE"); //I'm assuming move backwards
            sleep(10000000);   // sleep until auto is over
        }

        public void forwardClose()
        {
            //move forward from start

            robot.driveTrain. encoderDrive(opModeIsActive(),60,24,30,"FORWARD"); //Move forward
            sleep(10000000);   // sleep until auto is over
        }

        public void blueOuterSidewaysLaunchClose()
        {
            //launch (vaguely to the left - maybe turn?, strafe left from start, avoid red (veer slightly right) alliance's base (see Emily's diagrams), then straighten out

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNLEFT"); //rotate slightly for aim to the left
            sleep(250);   // optional pause after each move.
            launch(); //launch stuff
            stopLaunch(); //stop launching (launcher turns off)
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNRIGHT"); //rotate back to be in position to the right after launching
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"LEFT"); //Strafe left
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3,30, "REVERSE"); //I'm assuming move backwards
            sleep(10000000);   // sleep until auto is over
        }

        public void redOuterSidewaysLaunchClose()
        {
            //launch (vaguely to the right - maybe turn?, strafe right from start, avoid blue (veer slightly left) alliance's base (see Emily's diagrams), then straighten out

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNRIGHT"); //rotate slightly for aim to the right
            sleep(250);   // optional pause after each move.
            launch(); //launch stuff
            stopLaunch(); //stop launching (launcher turns off)
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNLEFT"); //rotate back to be in position to the left after launching
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"RIGHT"); //Strafe right
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "REVERSE"); //I'm assuming move backwards
            sleep(10000000);   // sleep until auto is over
        }

        public void blueOuterForwardLaunchClose()
        {
            //launch (vaguely to the left - maybe turn?, move forward from start, and avoid red (veer slightly right) alliance's base (see Emily's diagrams)

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forwards
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNLEFT"); //rotate slightly for aim to the left
            sleep(250);   // optional pause after each move.
            launch(); //launch stuff
            stopLaunch(); //stop launching (launcher turns off)
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNRIGHT"); //rotate back to be in position to the right after launching
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,3,30,"RIGHT"); //Strafe right
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"FORWARD"); //Move forward
            sleep(10000000);   // sleep until auto is over
        }

        public void redOuterForwardLaunchClose()
        {
            //launch (vaguely to the right - maybe turn?, move forward from start, and avoid blue (veer slightly left) alliance's base (see Emily's diagrams)

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNRIGHT"); //rotate slightly for aim to the right
            sleep(250);   // optional pause after each move.
            launch(); //launch stuff
            stopLaunch(); //stop launching (launcher turns off)
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(),60, 5, 30, "TURNLEFT"); //rotate back to be in position to the left after launching
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,3,30,"LEFT"); //Strafe left
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"FORWARD"); //Move forward
            sleep(10000000);   // sleep until auto is over
        }

        public void blueOuterSidewaysClose()
        {
            //strafe left from start, avoid red (veer slightly right) alliance's base (see Emily's diagrams), then
            //straighten out

            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"LEFT"); //Strafe left
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE"); //I'm assuming move backwards
            sleep(10000000);   // sleep until auto is over
        }

        public void redOuterSidewaysClose()
        {
            //strafe right from start, avoid (veer slightly left) blue alliance's base (see Emily's diagrams), then
            //straighten out

            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"RIGHT"); //Strafe right
            sleep(250);   // optional pause after each move.
            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 1, 30, "REVERSE"); //I'm assuming move backwards
            sleep(10000000);   // sleep until auto is over
        }

        public void blueOuterForwardClose()
        {
            //move forward from start and avoid (veer slightly right) red alliance's base (see Emily's diagrams)

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,3,30,"RIGHT"); //Strafe right
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"FORWARD"); //Move forward
            sleep(10000000);   // sleep until auto is over
        }

        public void redOuterForwardClose()
        {
            //move forward from start and avoid (veer slightly left) blue alliance's base (see Emily's diagrams)

            robot.driveTrain.encoderDrive(opModeIsActive(), 60, 3, 30, "FORWARD"); //Move forward
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,3,30,"LEFT"); //Strafe left
            sleep(250);   // optional pause after each move.
            robot.driveTrain. encoderDrive(opModeIsActive(),60,18,30,"FORWARD"); //Move forward
            sleep(10000000);   // sleep until auto is over
        }
  }
// end class
