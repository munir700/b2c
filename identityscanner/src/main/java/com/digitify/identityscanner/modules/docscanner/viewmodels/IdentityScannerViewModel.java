package com.digitify.identityscanner.modules.docscanner.viewmodels;

import android.app.Application;
import android.graphics.Bitmap;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.arch.SingleLiveEvent;
import com.digitify.identityscanner.helpers.IdentityBuilder;
import com.digitify.identityscanner.models.Error;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType;
import com.digitify.identityscanner.modules.docscanner.interfaces.IIdentityScanner;
import com.digitify.identityscanner.modules.docscanner.models.DocumentImage;
import com.digitify.identityscanner.modules.docscanner.models.Identity;
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult;
import com.digitify.identityscanner.modules.docscanner.states.IdentityScannerState;
import com.digitify.identityscanner.utils.FileUtils;
import com.digitify.identityscanner.utils.ImageUtils;
import com.digitify.identityscanner.viewmodels.BaseAndroidViewModel;

import androidx.annotation.NonNull;

public class IdentityScannerViewModel extends BaseAndroidViewModel implements IIdentityScanner.IViewModel {
    private DocumentType documentType;
    private IIdentityScanner.IView view;
    // error message to show to user.
    private SingleLiveEvent<String> error;
    private IdentityScannerState state;
    private IdentityScannerResult scannerResult;

    public IdentityScannerViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void init(DocumentType documentType, IIdentityScanner.IView view) {
        this.documentType = documentType;
        this.view = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reset();
    }

    @Override
    public void onPictureTaken(@NonNull String filepath) {
        if (FileUtils.isValidFile(filepath)) {
            getView().reviewDoc(filepath);
        } else {
            setErrorMessage(getString(R.string.invalid_file));
        }
    }

    @Override
    public void onPictureReviewFailed() {
        // start Retake flow
        getView().scanDoc();
    }

    @Override
    public void onPictureReviewCancelled() {
        // Cancel scanning and return
        getView().finishWithoutResult();
    }

    @Override
    public void onPictureReviewComplete(@NonNull String filepath) {
        DocumentType type = getDocumentType();
        saveCurrentDocument(filepath, getState().getScanMode());
        if (type == DocumentType.EID) {
            DocumentPageType mode = getState().getScanMode();
            if (mode == DocumentPageType.FRONT) {
                // We need to scan other side
                getState().setScanMode(DocumentPageType.BACK);
                getView().scanDoc();
            } else {
                // proceed with mrz detection and parsing as EID has mrz on its back
                onScanComplete(filepath);
            }
        } else if (type == DocumentType.PASSPORT) {
            // All passports has mrz on its front page.
            onScanComplete(filepath);
        }
    }

    private void saveCurrentDocument(String filepath, DocumentPageType type) {
        DocumentImage image = new DocumentImage(filepath, type);
        getScannerResult().getDocument().addFile(image);
    }

    private void onScanComplete(@NonNull String filepath) {
        getState().setLoading(true);
        Bitmap bitmap = ImageUtils.getBitmapFromStorage(filepath);

        new IdentityBuilder(getContext()).from(bitmap, getDocumentType()).listen(new IdentityBuilder.OnIdentityFetchingCompleteListener() {
            @Override
            public void onIdentityFetchingComplete(Identity identity, String mrz) {
                getScannerResult().setIdentity(identity);
                getScannerResult().setMrzText(mrz);

                // all set
                getState().setLoading(false);
                onResults(getScannerResult());
            }

            @Override
            public void onIdentityFetchingFailed(String error) {
                getState().setLoading(false);
                setErrorMessage(error);
                getScannerResult().setError(new Error(error));
                onResults(getScannerResult());
            }
        }).build();
    }

    private void onResults(IdentityScannerResult result) {
        getView().finishWithResult(getScannerResult());
    }

    @Override
    public void onResultsAccepted(IdentityScannerResult result) {
        getView().finishWithResult(result);
    }

    @Override
    public void onResultNotAccepted() {
        // start retake flow
        reset();
        getView().scanDoc();
    }

    @Override
    public void reset() {
        scannerResult = new IdentityScannerResult();
        getState().reset();
        getScannerResult().getDocument().setType(getDocumentType());
        getScannerResult().setError(null);
    }

    @Override
    public SingleLiveEvent<String> getErrorMessage() {
        if (error == null) error = new SingleLiveEvent<>();
        return error;
    }

    @Override
    public void setErrorMessage(String message) {
        getErrorMessage().setValue(message);
    }

    @Override
    public DocumentType getDocumentType() {
        return documentType;
    }

    @Override
    public IIdentityScanner.IView getView() {
        return view;
    }

    @Override
    public IdentityScannerState getState() {
        if (state == null) state = new IdentityScannerState();
        return state;
    }

    @Override
    public IdentityScannerResult getScannerResult() {
        if (scannerResult == null) scannerResult = new IdentityScannerResult();
        return scannerResult;
    }
}
