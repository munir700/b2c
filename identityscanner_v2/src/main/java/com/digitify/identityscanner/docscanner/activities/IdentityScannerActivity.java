package com.digitify.identityscanner.docscanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.base.BaseActivity;
import com.digitify.identityscanner.docscanner.enums.DocumentType;
import com.digitify.identityscanner.docscanner.fragments.DocReviewFragment;
import com.digitify.identityscanner.docscanner.fragments.YapCameraFragment;
import com.digitify.identityscanner.docscanner.interfaces.IIdentityScanner;
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult;
import com.digitify.identityscanner.docscanner.viewmodels.IdentityScannerViewModel;

public class IdentityScannerActivity extends BaseActivity implements IIdentityScanner.IView {

    public static final int SCAN_FROM_CAMERA = 1;
    public static final int SCAN_FROM_GALLERY = 2;
    public static final int SCAN_EID_CAM = 12;
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

    protected void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_holder, fragment).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void init() {
        scanFrom = getIntent().getIntExtra(SCAN_FROM, scanFrom);
        vm = ViewModelProviders.of(this).get(IdentityScannerViewModel.class);
        vm.init((DocumentType) getIntent().getSerializableExtra(DOC_TYPE), this);

        setObservers();
        scanDocFromCamera();
        vm.onStart();
    }

    @Override
    public void setObservers() {
        vm.getErrorMessage().observe(this, this::showToast);
    }

//    @Override
//    protected FrameLayout getFragmentHolder() {
//        return findViewById(R.id.fragment_holder);
//    }

    @Override
    public void reviewDoc(String filePath) {
        showFragment(DocReviewFragment.get(filePath, vm.getDocumentType(), vm.getState().getScanMode()));
    }

    @Override
    public void scanDoc() {
        if (scanFrom == SCAN_FROM_CAMERA) {
            scanDocFromCamera();
        }
    }

    @Override
    public void scanDocFromCamera() {
        showFragment(new YapCameraFragment());
    }

//    @Override
//    public void scanDocFromGallery() {
//        showFragment(new GalleryFragment());
//    }


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

//    @Override
//    public void onPermissionGranted(String permission) {
//        scanDoc();
//    }
//
//    @Override
//    public void onPermissionNotGranted(String permission) {
//        finishWithoutResult();
//    }

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
