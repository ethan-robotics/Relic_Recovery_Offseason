package org.firstinspires.ftc.teamcode.RobotFunctions.FalconVision.Detectors;

import org.firstinspires.ftc.teamcode.RobotFunctions.FalconVision.OpenCVpipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * This is an example class on how to make a detector for opencv.
 * This class takes the input image and outputs a grayscale image filtered for blue
 *
 * @author ethan
 */

public class TestDetector extends OpenCVpipeline {

    private Mat hsv = new Mat();
    private Mat filtered = new Mat();
    private Mat filtered_rgba = new Mat();


    public Mat processFrame(Mat rgba, Mat gray) {

        Imgproc.cvtColor(rgba, hsv, Imgproc.COLOR_RGB2HSV, 3); //converts rgb image to hsv color space
        Core.inRange(hsv, new Scalar(18, 73, 73), new Scalar(61, 31, 255), filtered); //filters the image, opencv uses 0 - 180 for hue, 0 - 255 for saturation and value
        Imgproc.cvtColor(filtered, filtered_rgba, Imgproc.COLOR_GRAY2RGBA); //converts grayscale image back to rgb, don't think this is needed
        return filtered_rgba;
    }
}
