package com.digitify.identityscanner.modules.docscanner.viewmodels;

import android.app.Application;
import android.graphics.Bitmap;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.detection.detectors.FaceDetector;
import com.digitify.identityscanner.core.detection.detectors.MrzDetector;
import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.core.detection.models.Mrz;
import com.digitify.identityscanner.core.ocr.Tesseract;
import com.digitify.identityscanner.helpers.IdentityBuilder;
import com.digitify.identityscanner.models.Error;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType;
import com.digitify.identityscanner.modules.docscanner.interfaces.IDocReview;
import com.digitify.identityscanner.modules.docscanner.models.Identity;
import com.digitify.identityscanner.modules.docscanner.states.DocReviewState;
import com.digitify.identityscanner.utils.FileUtils;
import com.digitify.identityscanner.utils.ImageUtils;
import com.digitify.identityscanner.viewmodels.BaseAndroidViewModel;

import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;

public class DocReviewViewModel extends BaseAndroidViewModel implements IDocReview.ViewModel {
    private String filepath;
    private DocumentPageType pageType;
    private DocumentType docType;
    private IDocReview.View view;
    private DocReviewState state;
    private FaceDetector faceDetector;

    public DocReviewViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public IDocReview.View getView() {
        return view;
    }

    @Override
    public void init(String filepath, DocumentType docType, DocumentPageType pageType, IDocReview.View view) {
        this.filepath = filepath;
        this.pageType = pageType;
        this.docType = docType;
        this.view = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initiateReview(getFilepath());
    }

    private void initiateReview(String path) {
        if (!FileUtils.isValidFile(path)) {
            handleClickOnRetake();
            return;
        }

        Bitmap bitmap = ImageUtils.getBitmapFromStorage(filepath);
        getState().setPreviewBitmap(bitmap);

        // Detect Face and MRZ based on Document type
        if (getDocType() == DocumentType.EID) {
            // 2 sided doc. Check which side is it.

            if (getPageType() == DocumentPageType.FRONT) {
                // check face
                Face face = detectFace(bitmap);
                onFaceValidationComplete(bitmap, face != null);
            } else if (getPageType() == DocumentPageType.BACK) {
                // check face
                Mrz mrz = detectMrz(bitmap);
                onMrzValidationComplete(bitmap, mrz != null);
            }

        } else if (getDocType() == DocumentType.PASSPORT) {
            // 1 sided doc. Check face and MRZ both on Front side
            Face face = detectFace(bitmap);
            if (face != null) {
                Mrz mrz = detectMrz(bitmap);
                onMrzValidationComplete(bitmap, mrz != null);
            } else {
                onFaceValidationComplete(bitmap, false);
            }

        } else {
            handleClickOnRetake();
        }
    }

    @Nullable
    private Face detectFace(Bitmap bitmap) {
        Face face = getFaceDetector().detect(bitmap);
        if (getFaceDetector().validate(face)) {
            return face;
        }
        return null;
    }

    @Nullable
    private Mrz detectMrz(Bitmap bitmap) {
        MrzDetector detector = new MrzDetector();
        Mrz mrz = detector.detect(bitmap);
        if (detector.validate(mrz)) {
            return mrz;
        }
        return null;
    }

    private void onFaceValidationComplete(Bitmap bm, boolean isValidated) {
        getState().setDocValid(isValidated);
        getState().setReviewText(isValidated ? getString(R.string.review_ok) : getString(R.string.review_face_nok));
    }

    private void onMrzValidationComplete(Bitmap bitmap, boolean isValidated) {
        getState().setLoading(true);
        if (isValidated) {
            new IdentityBuilder(getContext()).from(bitmap, getDocType()).listen(new IdentityBuilder.OnIdentityFetchingCompleteListener() {
                @Override
                public void onIdentityFetchingComplete(Identity identity, String mrz) {
                    getState().setLoading(false);
                    getState().setDocValid(true);
                    getState().setReviewText(getString(R.string.review_ok));
                }

                @Override
                public void onIdentityFetchingFailed(String error) {
                    getState().setLoading(false);
                    getState().setDocValid(false);
                    getState().setReviewText(getString(R.string.review_mrz_nok));
                }
            }).build();
        } else {
            getState().setDocValid(false);
            getState().setReviewText(getString(R.string.review_mrz_nok));
        }

    }

    @Override
    public void handleClickOnCancel() {
        getView().requestCancel();
    }

    @Override
    public void handleClickOnDone() {
        getView().requestDone();
    }

    @Override
    public void handleClickOnRetake() {
        getView().requestRetake();
    }

    @Override
    public DocReviewState getState() {
        if (state == null) state = new DocReviewState();
        return state;
    }

    public String getFilepath() {
        return filepath;
    }

    public DocumentPageType getPageType() {
        return pageType;
    }

    public DocumentType getDocType() {
        return docType;
    }

    public FaceDetector getFaceDetector() {
        if (faceDetector == null) faceDetector = new FaceDetector(getContext());
        return faceDetector;
    }
}
