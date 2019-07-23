package com.digitify.identityscanner.modules.docvalidator.interfaces;

import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.interfaces.IBase;
import com.digitify.identityscanner.interfaces.ILIfeCycle;
import com.digitify.identityscanner.modules.docvalidator.states.SelfieVideoState;

import org.opencv.core.Mat;

import androidx.lifecycle.LifecycleOwner;

public interface ISelfieVideo {
    interface View {
    }

    interface ViewModel extends IBase.ViewModel {
        void attach(LifecycleOwner lifecycleOwner);

        void onCameraStarted(int width, int height);
        void processFrame(Mat frame);

        void onVideoRecorded(String file);

        void onVideoRecordingFailed();

        void requestFaceMatch(Face docFace, Face frameFace);

        SelfieVideoState getState();

        void onLiveValidationsComplete();

        String getDocumentPath();

        void setDocumentPath(String documentPath);

    }
}
