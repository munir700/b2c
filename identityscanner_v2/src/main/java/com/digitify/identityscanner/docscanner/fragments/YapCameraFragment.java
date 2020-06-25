package com.digitify.identityscanner.docscanner.fragments;

import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProviders;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.base.BaseFragment;
import com.digitify.identityscanner.camera.CameraException;
import com.digitify.identityscanner.camera.CameraListener;
import com.digitify.identityscanner.camera.CameraOptions;
import com.digitify.identityscanner.camera.PictureResult;
import com.digitify.identityscanner.components.Overlay;
import com.digitify.identityscanner.components.TransparentCardView;
import com.digitify.identityscanner.databinding.FragmentCameraBinding;
import com.digitify.identityscanner.docscanner.enums.DocumentType;
import com.digitify.identityscanner.docscanner.interfaces.Cropper;
import com.digitify.identityscanner.docscanner.interfaces.ICamera;
import com.digitify.identityscanner.docscanner.viewmodels.CameraViewModel;
import com.digitify.identityscanner.docscanner.viewmodels.IdentityScannerViewModel;
import com.digitify.identityscanner.utils.ImageUtils;

import org.jetbrains.annotations.NotNull;

import co.yap.translation.Strings;
import co.yap.translation.Translator;

import static com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity.imageFiles;

public class YapCameraFragment extends BaseFragment implements ICamera.View, CameraListener {

    private Overlay overlay;
    private TransparentCardView cardOverlay;
    private CameraViewModel vm;
    private IdentityScannerViewModel parentViewModel;
    private FragmentCameraBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = ViewModelProviders.of(this).get(CameraViewModel.class);
        parentViewModel = ViewModelProviders.of(getActivity()).get(IdentityScannerViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.camera.setLifecycleOwner(this);
        binding.camera.addCameraListener(this);

    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.activity_scan_title);
    }


    private void init() {
        overlay = getView().findViewById(R.id.drawView);
        cardOverlay = getView().findViewById(R.id.cardOverlay);
        cardOverlay.setCardRatio(getParentViewModel().getDocumentType() == DocumentType.PASSPORT ? TransparentCardView.PASSPORT_RATIO : TransparentCardView.ID_CARD_RATIO);


        getViewModel().setDocumentType(getParentViewModel().getDocumentType());
        getViewModel().setScanMode(getParentViewModel().getState().getScanMode());
    }

    private void capturePicture() {

        if (binding.camera.isTakingPicture()) return;
        getViewModel().getState().setCapturing(false);
        binding.camera.takePicture();
    }

    private Observable.OnPropertyChangedCallback propertyChangedCallback = new Observable.OnPropertyChangedCallback() {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == com.digitify.identityscanner.BR.scanMode) {
                getViewModel().setScanMode(getParentViewModel().getState().getScanMode());
            }

//            else if (propertyId == com.digitify.identityscanner.BR.cardRect) {
////                updateDrawView(getViewModel().getState().getCardRect());
//            }
        }
    };


    @Override
    public void openCropper(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            cropper.crop(getActivity(), this, filename);
        } else {
            setInstructions(Translator.INSTANCE.getString(requireContext(), Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_saving_file));
        }
    }

    private Cropper cropper = new Cropper() {

        @Override
        public void onCropped(Uri uri) {
            imageFiles.add(uri.getPath());
            onCaptureProcessCompleted(uri.getPath());
        }

        @Override
        public void onCropFailed(Exception e) {
            setInstructions(e.getLocalizedMessage());
        }
    };

    @Override
    public void onCaptureProcessCompleted(String filename) {
        setInstructions(Translator.INSTANCE.getString(requireContext(), Strings.idenetity_scanner_sdk_screen_review_info_display_text_capture_process_complete));
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
    public void setInstructions(String inst) {
        if (getViewModel() != null) getViewModel().setInstructions(inst);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        binding.camera.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCameraOpened(@NonNull CameraOptions options) {
        getViewModel().getState().setCapturing(true);
        binding.camFab.setEnabled(true);
        binding.camFab.setOnClickListener((v) -> capturePicture());
    }

    @Override
    public void onCameraClosed() {
        binding.camFab.setOnClickListener(null);
        binding.camFab.setEnabled(false);
        getViewModel().getState().setCapturing(false);
    }

    @Override
    public void onCameraError(@NonNull CameraException exception) {
        getViewModel().getState().setCapturing(false);
        showToast(exception.getMessage());

    }

    @Override
    public void onPictureTaken(@NonNull PictureResult result) {

        result.toFile(ImageUtils.getFilePrivately(requireContext()), file -> getViewModel().handleOnPressCapture(file));

    }

    @Override
    public void onOrientationChanged(int orientation) {

    }

    @Override
    public void onAutoFocusStart(@NonNull PointF point) {

    }

    @Override
    public void onAutoFocusEnd(boolean successful, @NonNull PointF point) {

    }

    @Override
    public void onZoomChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {

    }

    @Override
    public void onExposureCorrectionChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {

    }
}
