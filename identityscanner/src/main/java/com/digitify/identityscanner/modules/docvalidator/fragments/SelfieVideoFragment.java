package com.digitify.identityscanner.modules.docvalidator.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.components.OpenCVCameraView;
import com.digitify.identityscanner.components.RecordButton;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;
import com.digitify.identityscanner.databinding.FragmentSelfieVideoBinding;
import com.digitify.identityscanner.fragments.OpenCVCameraFragment;
import com.digitify.identityscanner.modules.docvalidator.activities.IdentityValidatorActivity;
import com.digitify.identityscanner.modules.docvalidator.interfaces.IIdentityValidator;
import com.digitify.identityscanner.modules.docvalidator.interfaces.ISelfieVideo;
import com.digitify.identityscanner.modules.docvalidator.viewmodels.SelfieVideoViewModel;
import com.digitify.identityscanner.utils.FileUtils;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SelfieVideoFragment extends OpenCVCameraFragment implements ISelfieVideo.View {
    private static final String ARG_DOCUMENT_PATH = "documentPath";

    public static Fragment getInstance(String documentPath) {
        Fragment fragment = new SelfieVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DOCUMENT_PATH, documentPath);
        fragment.setArguments(args);
        return fragment;
    }

    private final int cameraFacing = CameraBridgeViewBase.CAMERA_ID_FRONT;
    private SelfieVideoViewModel vm;
    private String documentPath;

    @Override
    protected String getScreenTitle() {
        return getString(R.string.title_selfie_video);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSelfieVideoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selfie_video, container, false);
        View view = binding.getRoot();
        binding.setModel(getViewModel());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            documentPath = b.getString(ARG_DOCUMENT_PATH);
        }

        getParentView().validateDocument(documentPath);
        getCameraView().setCameraIndex(cameraFacing);
        init();
        getViewModel().setDocumentPath(documentPath);
        getViewModel().attach(this);
    }

    private void init() {
        RecordButton recordingBtn = getView().findViewById(R.id.record);
        recordingBtn.setButtonState(RecordButton.ButtonState.START_RECORDING);
        recordingBtn.setRecordingListener(getViewModel());
        recordingBtn.setMaxRecordTime(5 * 1000);
        getViewModel().getState().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.recordingButtonState) {
                    recordingBtn.setButtonState(getViewModel().getState().getRecordingButtonState());
                }
            }
        });
        getViewModel().getState().getStartRecordingEvent().observe(this, aBoolean -> recordingBtn.startRecordAnimation());
        getViewModel().getState().getStopRecordingEvent().observe(this, aBoolean -> recordingBtn.stopRecordAnimation());

        getViewModel().getState().getIsValidDocumentFace().observe(this, isValid -> {
            // check if it is a valid document that has face in it.
            if (!isValid) {
                getParentView().finishWithInvalidDocumentResult();
            }
        });

        getViewModel().getState().getValidatorResultCompleteTrigger().observe(this, aBoolean -> {
            // results are received
            getParentView().finishWithResult(getViewModel().getValidatorResult());
        });

    }

    @Override
    protected OpenCVCameraView getCameraView() {
        return getView().findViewById(R.id.opencvCam);
    }

    public SelfieVideoViewModel getViewModel() {
        if (vm == null) vm = ViewModelProviders.of(this).get(SelfieVideoViewModel.class);
        return vm;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        super.onCameraViewStarted(width, height);
        getViewModel().onCameraStarted(width, height);
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat src = inputFrame.rgba().clone();
        adjustRotation(src, cameraFacing);
        vm.processFrame(src);
        src.release();
        return super.onCameraFrame(inputFrame);
    }

    public IIdentityValidator.View getParentView() {
        return ((IIdentityValidator.View) getActivity());
    }

}
