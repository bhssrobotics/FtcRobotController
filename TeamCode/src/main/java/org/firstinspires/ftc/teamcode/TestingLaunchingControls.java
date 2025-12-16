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

@TeleOp(name="Testing Launching Controls", group="Pushbot")
public class TestingLaunchingControls extends OpMode
{
    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    ElapsedTime runtime = new ElapsedTime();
    double delayTime = 0;
    final double PAUSE_TIME = 5;
    double speed=0;
    /*
    * Code to run ONCE when the driver hits INIT
    */
    //@Override
    public void init() 
    {
        /* Initialize the hardware variables.
        * The init() method of the hardware class does all the work here
        */
        robot.init(hardwareMap); //hi
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

        double forward = -gamepad1.left_stick_y; //strafing left and right
        double strafe = gamepad1.left_stick_x; //
        double turn = gamepad1.right_stick_x; //backwards and forwards


        robot.driveTrain.drive(forward, strafe, turn);
        //press the button to start the launcher code
        if (gamepad2.right_bumper) //if i press the right bumper
        {
            telemetry.addData("current state: ", robot.launcher.getState());
            if(robot.launcher.readyToLaunch())
            {
                //set to full power
                robot.launcher.warmingUp();
                delayTime = runtime.seconds() + PAUSE_TIME;
            }
        }
        else if (gamepad2.left_bumper) //if i press the left bumper
        {
            //turn off the launching motor
            robot.launcher.notLaunching();
        }

        if(robot.launcher.isWarmingUp() && runtime.seconds() >= delayTime)
        {
            robot.launcher.launching();
        }



    }
  }
