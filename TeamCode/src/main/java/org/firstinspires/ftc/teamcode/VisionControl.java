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
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class VisionControl
{
    private VisionPortal visionPortal;
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private AprilTagProcessor aprilTag;
    private int myAprilTagIdCode; // ID code of current detection, in for() loop

    public VisionControl(HardwareMap hwMap)
    {
        initAprilTag(hwMap);
    }
    

    private void initAprilTag(HardwareMap hwMap) {

        // Create the AprilTag processor the easy way. Maybe calibrate in the future.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hwMap.get(WebcamName.class, "Webcam 1"), aprilTag);

        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }

        //visionPortal.stopStreaming();

    }   // end method initAprilTag(                                                                                                            )

    // accessor to stop streaming
    public void stopStreaming()
    {
        visionPortal.stopStreaming();
    }

    // accessor to resume streaming
    public void resumeStreaming()
    {
        visionPortal.resumeStreaming();
    }

    // accessor to close
    public void close()
    {
        visionPortal.close();
    }

    // give back all the AprilTags that we see in the list.
    public List<AprilTagDetection> getCurrentDetections() //Find the right ID from the list
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections(); //That ID is the April Tag
        return currentDetections; //Return that ID
    }

    public AprilTagPoseFtc getDetectionsVal(int id) //Once the April Tag is found, get the values of the April Tag
    {
        List<AprilTagDetection> currentDetections = getCurrentDetections();
        for (AprilTagDetection detection : currentDetections)
        {
            if (detection.metadata != null) { //If values are detected and from right ID then return values
                if (detection.id==id){
                    return detection.ftcPose;
                }
            }
        }
        return null; //Otherwise return nothing
    }

    //public Object AprilTagPoseFtc() {
    //}

    // Method from sample
//    private void telemetryAprilTag() {
//
//        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
//        telemetry.addData("# AprilTags Detected", currentDetections.size());
//
//        // Step through the list of detections and display info for each one.
//        for (AprilTagDetection detection : currentDetections) {
//            if (detection.metadata != null) {
//                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
//                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
//                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
//            } else {
//                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
//                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
//            }
//        }   // end for() loop
//
//        // Add "key" information to telemetry
//        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
//        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
//        telemetry.addLine("RBE = Range, Bearing & Elevation");
//
//    }   // end method telemetryAprilTag()
}
