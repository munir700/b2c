package com.digitify.identityscanner.modules.docscanner.fragments;

import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.components.OpenCVCameraView;
import com.digitify.identityscanner.components.TransparentCardView;
import com.digitify.identityscanner.core.arch.MainHandler;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;
import com.digitify.identityscanner.databinding.FragmentCameraBinding;
import com.digitify.identityscanner.fragments.BaseFragment;
import com.digitify.identityscanner.fragments.OpenCVCameraFragment;
import com.digitify.identityscanner.modules.docscanner.components.Overlay;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType;
import com.digitify.identityscanner.modules.docscanner.interfaces.Cropper;
import com.digitify.identityscanner.modules.docscanner.interfaces.ICamera;
import com.digitify.identityscanner.modules.docscanner.viewmodels.CameraViewModel;
import com.digitify.identityscanner.modules.docscanner.viewmodels.IdentityScannerViewModel;
import com.digitify.identityscanner.utils.ImageUtils;
import com.digitify.identityscanner.utils.NumberUtils;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProviders;

public class CameraFragment extends OpenCVCameraFragment implements ICamera.View, CameraBridgeViewBase.CvCameraViewListener2 {
    private static String TAG = CameraFragment.class.getName();

    public static BaseFragment get(String title) {
        CameraFragment fragment = new CameraFragment();
        fragment.setTitle(title);
        return fragment;
    }

    private Overlay overlay;
    private TransparentCardView cardOverlay;
    private CameraViewModel vm;
    private IdentityScannerViewModel parentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = ViewModelProviders.of(this).get(CameraViewModel.class);
        parentViewModel = ViewModelProviders.of(getActivity()).get(IdentityScannerViewModel.class);
        FragmentCameraBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);
        View view = binding.getRoot();
        binding.setModel(vm);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        getViewModel().onStart();
    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.activity_scan_title);
    }

    @Override
    protected OpenCVCameraView getCameraView() {
        return getView().findViewById(R.id.opencvCam);
    }

    private void init() {
        overlay = getView().findViewById(R.id.drawView);
        cardOverlay = getView().findViewById(R.id.cardOverlay);
        cardOverlay.setCardRatio(getParentViewModel().getDocumentType() == DocumentType.PASSPORT ? TransparentCardView.PASSPORT_RATIO : TransparentCardView.ID_CARD_RATIO);

        getView().findViewById(R.id.camFab).setOnClickListener((v) -> getViewModel().handleOnPressCapture(takeSnapshot()));
        getView().findViewById(R.id.quick_doc_preview).setOnClickListener((v) -> getViewModel().handleOnPressQuickCapture(takeSnapshot()));
        setTitle(getTitle(getParentViewModel().getState().getScanMode()));

        getViewModel().setDocumentType(getParentViewModel().getDocumentType());

    }

    Observable.OnPropertyChangedCallback propertyChangedCallback = new Observable.OnPropertyChangedCallback() {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == BR.scanMode) {
                getViewModel().reset();
                setTitle(getTitle(getParentViewModel().getState().getScanMode()));
            } else if (propertyId == BR.cardRect) {
                updateDrawView(getViewModel().getState().getCardRect());
            }
        }
    };


    private String getTitle(DocumentPageType mode) {
        if (getParentViewModel().getDocumentType() == DocumentType.PASSPORT) {
            return getString(R.string.scan_passport);
        } else if (getParentViewModel().getDocumentType() == DocumentType.EID) {
            return (mode == DocumentPageType.FRONT) ?
                    getString(R.string.scan_front_eid) :
                    getString(R.string.scan_back_eid);
        }

        return getString(R.string.scan);

    }

    private void updateDrawView(Rect roi) {
        MainHandler.get().post(() -> {
            if (getViewModel().getState().isCapturing()) return;
            int tolerance = 70;
            if (overlay != null) {
                if (roi == null || roi.size().width <= 0) {
                    overlay.clear();
                } else {
                    /**
                     * We may have frame size different than surface size so
                     * to map the dimensions of roi (adjusted according to frame size) to Surface size,
                     * get the frame/surface ratio and adjust roi
                     */
                    double hr = getFrameToSurfaceHeightRatio();
                    double wr = getFrameToSurfaceWidthRatio();

                    int x = (int) Math.ceil(roi.x / wr);
                    int y = (int) Math.ceil(roi.y / hr);
                    int w = (int) Math.ceil(roi.width / wr);
                    int h = (int) Math.ceil(roi.height / hr);
                    // we only need to draw it if it is inside CardOverlay

                    android.graphics.Rect or = new android.graphics.Rect(x, y, x + w, y + h);
                    // overlay.drawRect(or.left, or.top, or.right, or.bottom);
                    if (NumberUtils.isInsideCardOverlay(or, cardOverlay.getCardRect(), tolerance)) {
                        cardOverlay.setCardBorderColor(getResources().getColor(R.color.green));
                        // capture this
                        getViewModel().handleOnPressQuickCapture(takeSnapshot());
                    } else {
                        cardOverlay.setCardBorderColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });
    }

    @Override
    public void onCameraSurfaceChanged(int width, int height) {
        if (overlay != null) {
            ViewGroup.LayoutParams params = overlay.getLayoutParams();
            params.width = width;
            params.height = height;
            overlay.setLayoutParams(params);
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat rgba = inputFrame.rgba();
        vm.processFrame(OpenCVUtils.rotateImage(rgba));
        return rgba;
    }

    @Override
    public void openCropper(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            cropper.crop(getActivity(), this, filename);
        } else {
            setInstructions(getString(R.string.error_saving_file));
        }
    }

    private Cropper cropper = new Cropper() {

        @Override
        public void onCropped(Uri uri) {
            onCaptureProcessCompleted(uri.getPath());
        }

        @Override
        public void onCropFailed(Exception e) {
            setInstructions(e.getLocalizedMessage());
        }
    };

    @Override
    public void onCaptureProcessCompleted(String filename) {
        setInstructions(getString(R.string.capture_process_complete));
        getParentViewModel().onPictureTaken(filename);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().getCapturedImage().observe(this, this::openCropper);
        getViewModel().getCapturedDocument().observe(this, this::onCaptureProcessCompleted);
        getViewModel().getState().addOnPropertyChangedCallback(propertyChangedCallback);
        getParentViewModel().getState().addOnPropertyChangedCallback(propertyChangedCallback);
    }

    @Override
    public void onPause() {
        getViewModel().onStop();
        getViewModel().getState().removeOnPropertyChangedCallback(propertyChangedCallback);
        getParentViewModel().getState().removeOnPropertyChangedCallback(propertyChangedCallback);
        super.onPause();
    }

    private CameraViewModel getViewModel() {
        return vm;
    }

    private IdentityScannerViewModel getParentViewModel() {
        return parentViewModel;
    }

    @Override
    public void setTitle(String title) {
        if (getViewModel() != null) getViewModel().setTitle(title);
    }

    @Override
    public void setInstructions(String inst) {
        if (getViewModel() != null) getViewModel().setInstructions(inst);
    }

}
