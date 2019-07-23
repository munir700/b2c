package com.digitify.identityscanner.modules.docscanner.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.coursion.freakycoder.mediapicker.galleries.Gallery;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.fragments.BaseFragment;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.modules.docscanner.interfaces.Cropper;
import com.digitify.identityscanner.modules.docscanner.viewmodels.IdentityScannerViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends BaseFragment {

    static {
        System.loadLibrary("opencv_java4");
    }

    private static final int PICK_IMAGE = 3;

    private IdentityScannerViewModel parentViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openGallery();
    }

    private Cropper cropper = new Cropper() {
        @Override
        public void onCropped(Uri uri) {
            getParentViewModel().onPictureTaken(uri.getPath());
        }

        @Override
        public void onCropFailed(Exception e) {
            openGallery();
        }
    };


    public void openGallery() {
        Intent intent = new Intent(getContext(), Gallery.class);
        intent.putExtra("title", getScreenTitle());
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 2);
        intent.putExtra("maxSelection", 1); // Optional
        this.startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected String getScreenTitle() {
        return getParentViewModel().getState().getScanMode() == DocumentPageType.FRONT ? getString(R.string.scan_front) : getString(R.string.scan_back);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropper.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> images = data.getStringArrayListExtra("result");
                // Select the first one because we are selecting only 1 image from gallery
                cropper.crop(getActivity(), this, images.get(0));
            } else if (resultCode == RESULT_CANCELED) {
                // User decided not to select image. so back to the caller activity
                getParentViewModel().getView().finishWithoutResult();
            }
        }
    }

    private IdentityScannerViewModel getParentViewModel() {
        if (parentViewModel == null) parentViewModel = ViewModelProviders.of(getActivity()).get(IdentityScannerViewModel.class);
        return parentViewModel;
    }
}
