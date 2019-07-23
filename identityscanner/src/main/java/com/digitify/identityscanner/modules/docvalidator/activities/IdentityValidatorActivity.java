package com.digitify.identityscanner.modules.docvalidator.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.activities.PermissionAwareFragmentActivity;
import com.digitify.identityscanner.models.Error;
import com.digitify.identityscanner.modules.docvalidator.fragments.SelfieVideoFragment;
import com.digitify.identityscanner.modules.docvalidator.interfaces.IIdentityValidator;
import com.digitify.identityscanner.modules.docvalidator.models.IdentityValidatorResult;
import com.digitify.identityscanner.utils.FileUtils;

import org.opencv.core.Point;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class IdentityValidatorActivity extends PermissionAwareFragmentActivity implements IIdentityValidator.View {
    public static final String VALIDATION_RESULT = "validationResult";
    public static final int ERROR_INVALID_DOCUMENT = 1;
    public static final int ERROR_DOCUMENT_NO_FACE = 2;

    protected static final String ARG_DOCUMENT_PATH = "documentPath";

    public static Intent getLaunchIntent(Context context, String documentPath) {
        Intent in = new Intent(context, IdentityValidatorActivity.class);
        in.putExtra(ARG_DOCUMENT_PATH, documentPath);
        return in;
    }

    private String documentPath = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_validator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Validate Identity");
        init();

    }

    @Override
    protected FrameLayout getFragmentHolder() {
        return findViewById(R.id.fragment_holder);
    }

    @Override
    public void init() {
        documentPath = getIntent().getStringExtra(ARG_DOCUMENT_PATH);
        validateDocument(documentPath);
        setObservers();
    }

    @Override
    public void validateDocument(String documentPath) {
        boolean isValid = FileUtils.isValidFile(documentPath);
        if (!isValid) {
            finishWithInvalidDocumentResult();
        }
    }

    @Override
    public void onPermissionGranted(String permission) {
        showFragment(SelfieVideoFragment.getInstance(documentPath));
    }

    @Override
    public void onPermissionNotGranted(String permission) {
        finishWithoutResult();
    }

    @Override
    public void finishWithResult(IdentityValidatorResult result) {
        Intent in = new Intent();
        in.putExtra(VALIDATION_RESULT, result);
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
    public void finishWithInvalidDocumentResult() {
        IdentityValidatorResult result = new IdentityValidatorResult();
        Error err =  new Error(ERROR_INVALID_DOCUMENT);
        result.setError(err);
        finishWithResult(result);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setObservers() {

    }

}
