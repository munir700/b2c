package com.digitify.identityscanner.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.digitify.identityscanner.components.OpenCVCameraView;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import androidx.annotation.Nullable;

public abstract class OpenCVCameraFragment extends BaseFragment implements CameraBridgeViewBase.CvCameraViewListener2, OpenCVCameraView.OnCameraSurfaceChangedListener {
    private static String TAG = OpenCVCameraFragment.class.getName();

    static {
        System.loadLibrary("opencv_java4");
    }

    private OpenCVCameraView mOpenCvCameraView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mOpenCvCameraView = getCameraView();
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setOnCameraSurfaceChangedListener(this);
        mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_ANY);
    }

    public Mat takeSnapshot() {
        return OpenCVUtils.rotateImage(getCameraView().takePicture());
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getContext()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public void onCameraSurfaceChanged(int width, int height) {

    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, getContext(), mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    protected final void adjustRotation(Mat src, int cameraIndex) {
        if (cameraIndex == CameraBridgeViewBase.CAMERA_ID_FRONT) {
            OpenCVUtils.rotateImage(src, OpenCVUtils.Rotation.ROTATE_90_COUNTERCLOCKWISE);
            OpenCVUtils.flip(src, OpenCVUtils.Rotation.ROTATE_180);
        } else {
            OpenCVUtils.rotateImage(src, OpenCVUtils.Rotation.ROTATE_90_CLOCKWISE);
        }
    }

    public double getFrameToSurfaceWidthRatio() {
        return mOpenCvCameraView.getFrameToSurfaceWidthRatio();
    }

    public double getFrameToSurfaceHeightRatio() {
        return mOpenCvCameraView.getFrameToSurfaceHeightRatio();
    }

    protected abstract OpenCVCameraView getCameraView();
}
