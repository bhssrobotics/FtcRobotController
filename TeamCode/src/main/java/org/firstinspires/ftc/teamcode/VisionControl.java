package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.apriltag.AprilTagDetection;

import java.util.List;
import java.util.concurrent.TimeUnit;
public class VisionControl
{
    AprilTagProcessor myAprilTagProcessor;
    VisionPortal myVisionPortal;
    List<AprilTagDetection> myAprilTagDetections;  // list of all detections
    int myAprilTagIdCode;                           // ID code of current detection, in for() loop

    public VisionControl(HardwareMap hwmap)
    {
        // Create a VisionPortal, with the specified camera and AprilTag processor, and assign it to a variable.
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), myAprilTagProcessor);
        // Create the AprilTag processor and assign it to a variable.
        myAprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();


    }

    void getAprilTagId()
    {

        // Get a list of AprilTag detections.
        myAprilTagDetections = myAprilTagProcessor.getDetections();

        for (AprilTagDetection myAprilTagDetection : myAprilTagDetections)
        {
            if (myAprilTagDetection.metadata != null) {  // This check for non-null Metadata is not needed for reading only ID code.
                myAprilTagIdCode = myAprilTagDetection.id;

                // Now take action based on this tag's ID code, or store info for later action.

            }
        }
    }
}
