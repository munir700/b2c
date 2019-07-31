package com.digitify.identityscanner.modules.docscanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.activities.PermissionAwareFragmentActivity;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType;
import com.digitify.identityscanner.modules.docscanner.fragments.*;
import com.digitify.identityscanner.modules.docscanner.interfaces.IIdentityScanner;
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult;
import com.digitify.identityscanner.modules.docscanner.viewmodels.IdentityScannerViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class IdentityScannerActivity extends PermissionAwareFragmentActivity implements IIdentityScanner.IView {

    public static final int SCAN_FROM_CAMERA = 1;
    public static final int SCAN_FROM_GALLERY = 2;
    private static final String DOC_TYPE = "docType";
    private static final String SCAN_FROM = "scanFrom";
    public static final String SCAN_RESULT = "scannerResult";


    public static Intent getLaunchIntent(Context context, DocumentType type, int scanFrom) {
        Intent in = new Intent(context, IdentityScannerActivity.class);
        in.putExtra(DOC_TYPE, type);
        in.putExtra(SCAN_FROM, scanFrom);
        return in;
    }

    private int scanFrom = SCAN_FROM_CAMERA;
    private IdentityScannerViewModel vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_scanner_frag);
        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cross);
        setTitle("");

        init();
    }

    @Override
    public void init() {
        scanFrom = getIntent().getIntExtra(SCAN_FROM, scanFrom);
        vm = ViewModelProviders.of(this).get(IdentityScannerViewModel.class);
        vm.init((DocumentType) getIntent().getSerializableExtra(DOC_TYPE), this);

        setObservers();
        vm.onStart();
    }

    @Override
    public void setObservers() {
        vm.getErrorMessage().observe(this, this::showToast);
    }

    @Override
    protected FrameLayout getFragmentHolder() {
        return findViewById(R.id.fragment_holder);
    }

    @Override
    public void reviewDoc(String filePath) {
        showFragment(DocReviewFragment.get(filePath, vm.getDocumentType(), vm.getState().getScanMode()));
    }

    @Override
    public void scanDoc() {
        if (scanFrom == SCAN_FROM_CAMERA) {
            scanDocFromCamera();
        } else {
            scanDocFromGallery();
        }
    }

    @Override
    public void scanDocFromCamera() {
        showFragment(new YapCameraFragment());
    }

    @Override
    public void scanDocFromGallery() {
        showFragment(new GalleryFragment());
    }


    @Override
    public void finishWithResult(IdentityScannerResult result) {
        Intent in = new Intent();
        in.putExtra(SCAN_RESULT, result);
        setResult(RESULT_OK, in);
        finish();
    }

    @Override
    public void finishWithoutResult() {
        Intent in = new Intent();
        setResult(RESULT_CANCELED, in);
        finish();
    }

    @Override
    public void onPermissionGranted(String permission) {
        scanDoc();
    }

    @Override
    public void onPermissionNotGranted(String permission) {
        finishWithoutResult();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        vm.onStop();
        super.onDestroy();
    }
}
