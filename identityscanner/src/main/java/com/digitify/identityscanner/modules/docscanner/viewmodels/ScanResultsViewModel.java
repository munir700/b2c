package com.digitify.identityscanner.modules.docscanner.viewmodels;

import android.app.Application;

import com.digitify.identityscanner.core.detection.detectors.FaceDetector;
import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.modules.docscanner.interfaces.IScanResults;
import com.digitify.identityscanner.modules.docscanner.models.DocumentImage;
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult;
import com.digitify.identityscanner.modules.docscanner.states.ScanResultsState;
import com.digitify.identityscanner.utils.ImageUtils;
import com.digitify.identityscanner.viewmodels.BaseAndroidViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ScanResultsViewModel extends BaseAndroidViewModel implements IScanResults.ViewModel {

    private IScanResults.View view;
    private IdentityScannerResult scanResults;
    private ScanResultsState state;
    private FaceDetector faceDetector;

    public ScanResultsViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void init(IdentityScannerResult scanResults, IScanResults.View view) {
        this.scanResults = scanResults;
        this.view = view;

        setupState();
    }

    private void setupState() {
        getState().reset();
        getState().setResult(getScanResults());

        // Find the front side of document
        ArrayList<DocumentImage> files = getScanResults().getDocument().getFiles();
        DocumentImage front = null;
        for (DocumentImage img: files) {
            if (img.getType() == DocumentPageType.FRONT) {
                front = img;
                break;
            }
        }

        if (front != null) {
            // get the face
            Face face = getFaceDetector().detect(ImageUtils.getBitmapFromStorage(front.getOriginalFile()));
            if (getFaceDetector().validate(face)) {
                getState().setFaceBitmap(face.getBitmap());
            }
        }
    }

    public FaceDetector getFaceDetector() {
        if (faceDetector == null) faceDetector = new FaceDetector(getContext());
        return faceDetector;
    }

    @Override
    public IScanResults.View getView() {
        return view;
    }

    @Override
    public ScanResultsState getState() {
        if (state == null) state = new ScanResultsState(getContext());
        return state;
    }

    public IdentityScannerResult getScanResults() {
        return scanResults;
    }
}
