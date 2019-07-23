package com.digitify.identityscanner.components;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OpenCVCameraView extends JavaCameraView implements CameraBridgeViewBase.CvCameraViewListener2, MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener {
    private static final String TAG = "OpenCVCameraView";

    public interface OnCameraSurfaceChangedListener {
        void onCameraSurfaceChanged(int width, int height);

        OnCameraSurfaceChangedListener DEFAULT = (width, height) -> {
        };
    }

    private CvCameraViewListener2 customCameraListener;
    private OnCameraSurfaceChangedListener onCameraSurfaceChangedListener;
    private Mat snapshot;
    private MediaRecorder recorder;
    private int surfaceHeight, surfaceWidth;
    private int frameHeight, frameWidth;
    double frameToSurfaceWidthRatio, frameToSurfaceHeightRatio;

    public OpenCVCameraView(Context context, int cameraId) {
        super(context, cameraId);
    }

    public OpenCVCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Size calculateCameraFrameSize(List<?> supportedSizes, ListItemAccessor accessor, int surfaceWidth, int surfaceHeight) {
        this.surfaceHeight = surfaceHeight;
        this.surfaceWidth = surfaceWidth;
        getOnCameraSurfaceChangedListener().onCameraSurfaceChanged(surfaceWidth, surfaceHeight);

        // invert the width and height
         // return super.calculateCameraFrameSize(supportedSizes, accessor, surfaceHeight, surfaceWidth);
         return getOptimisedPreviewSize(supportedSizes, accessor, surfaceWidth, surfaceHeight);
    }

    public Size getOptimisedPreviewSize(List<?> sizes, ListItemAccessor accessor, int w, int h) {

        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null)
            return null;

        Object optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Object size : sizes) {
            double ratio = (double) accessor.getWidth(size) / accessor.getHeight(size);
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(accessor.getHeight(size) - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(accessor.getHeight(size) - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Object size : sizes) {
                if (Math.abs(accessor.getHeight(size) - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(accessor.getHeight(size) - targetHeight);
                }
            }
        }

        return new Size(accessor.getWidth(optimalSize), accessor.getHeight(optimalSize));
    }

    @Override
    public void setCvCameraViewListener(CvCameraViewListener2 listener) {
        this.customCameraListener = listener;
        super.setCvCameraViewListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        // switching width with height as we are changing orientation to 90 degree
        this.frameWidth = height;
        this.frameHeight = width;

        // calculate ratios
        setFrameToSurfaceHeightRatio((double) getFrameHeight() / (double) getSurfaceHeight());
        setFrameToSurfaceWidthRatio((double) getFrameWidth() / (double) getSurfaceWidth());

        if (customCameraListener != null) {
            customCameraListener.onCameraViewStarted(getFrameWidth(), getFrameHeight());
        }
    }

    @Override
    public void onCameraViewStopped() {
        if (customCameraListener != null) {
            customCameraListener.onCameraViewStopped();
        }
        if (snapshot != null) snapshot.release();
        
    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        snapshot = inputFrame.rgba();
        if (customCameraListener != null) {
            return customCameraListener.onCameraFrame(inputFrame);
        }
        return snapshot;
    }

    public Mat takePicture() {
        return snapshot.clone();
    }

    public MediaRecorder prepareMediaRecorder(String outputFile) throws IOException {

        MediaRecorder recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);

        CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);
        recorder.setOutputFile(outputFile);
        // recorder.setVideoSize(mOpenCvCameraView.mFrameWidth, mOpenCvCameraView.mFrameHeight);

        recorder.setOnInfoListener(this);
        recorder.setOnErrorListener(this);

        return recorder;
    }

    private boolean prepareMediaRecorderWithCamera(String outputFile) {

        recorder = new MediaRecorder();
        mCamera.unlock();
        recorder.setCamera(mCamera);

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);
        recorder.setOutputFile(outputFile);

        recorder.setOnInfoListener(this);
        recorder.setOnErrorListener(this);

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }

        return true;
    }

    private void releaseMediaRecorder() {
        recorder.release();
    }

    public void startRecorder(String outputFile) {
        if (prepareMediaRecorderWithCamera(outputFile)) {
            recorder.start();
        } else {
            releaseMediaRecorder();
        }
    }

    public void stopRecorder() {
        recorder.stop();
        releaseMediaRecorder();
        mCamera.lock();
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        Log.d(TAG, "Error ---- What: " + what + ", extra: " + extra);
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        Log.d(TAG, "Info ---- What: " + what + ", extra: " + extra);
    }

    public OnCameraSurfaceChangedListener getOnCameraSurfaceChangedListener() {
        if (onCameraSurfaceChangedListener == null)
            onCameraSurfaceChangedListener = OnCameraSurfaceChangedListener.DEFAULT;
        return onCameraSurfaceChangedListener;
    }

    public void setOnCameraSurfaceChangedListener(OnCameraSurfaceChangedListener onCameraSurfaceChangedListener) {
        this.onCameraSurfaceChangedListener = onCameraSurfaceChangedListener;
    }

    public int getSurfaceHeight() {
        return surfaceHeight;
    }

    public int getSurfaceWidth() {
        return surfaceWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public double getFrameToSurfaceWidthRatio() {
        return frameToSurfaceWidthRatio;
    }

    public void setFrameToSurfaceWidthRatio(double frameToSurfaceWidthRatio) {
        this.frameToSurfaceWidthRatio = frameToSurfaceWidthRatio;
    }

    public double getFrameToSurfaceHeightRatio() {
        return frameToSurfaceHeightRatio;
    }

    public void setFrameToSurfaceHeightRatio(double frameToSurfaceHeightRatio) {
        this.frameToSurfaceHeightRatio = frameToSurfaceHeightRatio;
    }
}
