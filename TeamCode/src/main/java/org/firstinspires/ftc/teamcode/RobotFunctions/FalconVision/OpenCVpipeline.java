package org.firstinspires.ftc.teamcode.RobotFunctions.FalconVision;

import android.app.Activity;
import android.content.Context;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;

/**
 * This class provides a base class for interfacing opencv with opencv detectors
 *
 * @see github.com/guineawheek/endercv/blob/master/endercv/src/main/java/org/corningrobotics/enderbots/endercv/OpenCVPipeline.java for the original source of this code
 *
 * TODO: remove the stopped bool function, replace in detectors with checking opmode
 */

public abstract class OpenCVpipeline implements CameraBridgeViewBase.CvCameraViewListener2{
    static {
        System.loadLibrary("opencv_java3"); //idk what this does but without it the rc crashes
    }

    private Context context;
    private ViewDisplay viewDisplay;
    private boolean inited;
    private JavaCameraView cameraView;
    public boolean stopped;

    public void init(Context context, ViewDisplay viewDisplay, final int cameraIndex){
        this.context = context;
        this.viewDisplay = viewDisplay;
        final Context finalContext = context;
        final Activity activity = (Activity) context;
        final CameraBridgeViewBase.CvCameraViewListener2 self = this;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // JCVs must be instantiated on a UI thread
                cameraView = new JavaCameraView(finalContext, cameraIndex);
                cameraView.setCameraIndex(cameraIndex);
                cameraView.setCvCameraViewListener(self);
                inited = true;
                stopped = false;
            }
        });
    }

    public void enable(){
        cameraView.enableView();
        viewDisplay.setCurrentView(context, getCameraView());
    }

    public JavaCameraView getCameraView() {
        return cameraView;
    }

    public void disable() {
        cameraView.disableView();
        viewDisplay.removeCurrentView(context);
        stopped = true;
    }


    public void onCameraViewStarted(int width, int height) { // opencv needs it cause idk
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) { // I think it returns a mat with a camera frame input, idk
        return processFrame(inputFrame.rgba(), inputFrame.gray());
    }

    public abstract Mat processFrame(Mat rgba, Mat gray); // is implemented in detector class and does the processing for the image

    @Override
    public void onCameraViewStopped() {// override this and add code if you need stuff to happen when camera stopped
        stopped = true;
    }


}
