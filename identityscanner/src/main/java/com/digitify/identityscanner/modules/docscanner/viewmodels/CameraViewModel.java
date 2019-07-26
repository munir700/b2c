package com.digitify.identityscanner.modules.docscanner.viewmodels;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.arch.SingleLiveEvent;
import com.digitify.identityscanner.core.arch.WorkerHandler;
import com.digitify.identityscanner.core.detection.detectors.CardDetector;
import com.digitify.identityscanner.core.detection.detectors.MrzDetector;
import com.digitify.identityscanner.core.detection.detectors.PassportDetector;
import com.digitify.identityscanner.core.detection.models.Card;
import com.digitify.identityscanner.core.detection.models.Entity;
import com.digitify.identityscanner.core.detection.models.Mrz;
import com.digitify.identityscanner.core.detection.models.Passport;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType;
import com.digitify.identityscanner.modules.docscanner.enums.ImageReadinessStatus;
import com.digitify.identityscanner.modules.docscanner.interfaces.ICamera;
import com.digitify.identityscanner.modules.docscanner.states.CameraState;
import com.digitify.identityscanner.utils.Constants;
import com.digitify.identityscanner.utils.ImageUtils;
import com.digitify.identityscanner.viewmodels.BaseAndroidViewModel;

import org.opencv.core.Mat;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;

public class CameraViewModel extends BaseAndroidViewModel implements ICamera.ViewModel {
    private WorkerHandler workHandler = WorkerHandler.get("camera_processing");

    private int mFrameCount = 0;
    private int FRAME_PROCESS_RATE = 3;

    private DocumentType documentType;
    private DocumentPageType scanMode;
    private SingleLiveEvent<String> capturedImage;
    private SingleLiveEvent<String> capturedDocument;
    private CameraState state;
    private CardDetector cardDetector;
    private PassportDetector passportDetector;

    public CameraViewModel(@NonNull Application application) {
        super(application);
        reset();
    }

    @Override
    public void reset() {
        getState().reset();
        getState().setSubmitButtonTitle("Scan");
    }

    @Override
    public synchronized void processFrame(Mat frame) {
        if (mFrameCount++ % FRAME_PROCESS_RATE != 0) return;
        workHandler.post(() -> {
            switch (getDocumentType()) {
                case EID:
                    proceedWithCardDetection(frame);
                    break;
                case PASSPORT:
                    proceedWithMrzDetection(frame);
                    break;
            }
        });
    }

    private void proceedWithMrzDetection(Mat frame) {
        Passport passport = getPassportDetector().detect(frame);
        validateImageReadiness(passport);
    }

    private void proceedWithCardDetection(Mat frame) {
        Card card = getCardDetector().detect(frame);
        validateImageReadiness(card);
    }

    private void validateImageReadiness(Entity card) {
        if (card.isValid()) {
            // we found a card. so show the visual
            getState().setCardRect(card.getBoundingBox());
            return;
        }

        getState().setCardRect(null);
    }

    @Override
    public void handleOnPressCapture(Mat snapshot) {
        if (!getState().isCapturing()) {
            getState().setCapturing(true);
            if (!snapshot.empty()) {
                Bitmap image = OpenCVUtils.convertToBitmap(snapshot);
                String file = ImageUtils.savePicture(getApplication().getApplicationContext(), image);
                if (validateFile(file)) setCapturedImage(file);
            }
            getState().setCapturing(false);
        }
    }

    @Override
    public void handleOnPressQuickCapture(Mat snapshot) {
        if (!getState().isCapturing()) {

            getState().setCapturing(true);
            Entity entity = null;
            if (getDocumentType() == DocumentType.PASSPORT)
                entity = getPassportDetector().detect(snapshot);
            if (getDocumentType() == DocumentType.EID) entity = getCardDetector().detect(snapshot);

            if (entity != null && entity.isValid()) {
                String file = ImageUtils.savePicture(getApplication().getApplicationContext(), entity.getBitmap());
                if (validateFile(file)) setCapturedDocument(file);
            } else {
                setInstructions(getString(R.string.error_detecting_document));
            }
            getState().setCapturing(false);
        }
    }

    private boolean validateFile(String file) {
        if (TextUtils.isEmpty(file)) {
            // This is most probably a developer's mistake. Handle it.
            setInstructions(getString(R.string.error_saving_file));
            return false;
        }
        return true;
    }

    @Override
    public CameraState getState() {
        if (state == null) state = new CameraState();
        return state;
    }

    @Override
    public SingleLiveEvent<String> getCapturedImage() {
        if (capturedImage == null) capturedImage = new SingleLiveEvent<>();
        return capturedImage;
    }

    @Override
    public void setCapturedImage(String filename) {
        getCapturedImage().setValue(filename);
    }

    @Override
    public SingleLiveEvent<String> getCapturedDocument() {
        if (capturedDocument == null) capturedDocument = new SingleLiveEvent<>();
        return capturedDocument;
    }

    @Override
    public void setCapturedDocument(String filename) {
        getCapturedDocument().setValue(filename);
    }


    @Override
    public void setInstructions(String inst) {
        getState().setInstructions(inst);
        WorkerHandler.get("instruction_handler").get().postDelayed(() -> {
            getState().setInstructions("");
        }, 3000);
    }

    public DocumentType getDocumentType() {
        if (documentType == null) documentType = DocumentType.EID;
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public CardDetector getCardDetector() {
        if (cardDetector == null) cardDetector = new CardDetector();
        return cardDetector;
    }

    public PassportDetector getPassportDetector() {
        if (passportDetector == null) passportDetector = new PassportDetector();
        return passportDetector;
    }

    @Override
    public void setScanMode(DocumentPageType mode) {
        this.scanMode = mode;
        reset();
        getState().setTitle(getTitleForMode(mode));
        getState().setStepInstructions(getStepString(mode));
    }

    @Override
    public DocumentPageType getScanMode() {
        return this.scanMode;
    }

    private String getTitleForMode(DocumentPageType mode) {
        if (getDocumentType() == DocumentType.PASSPORT) {
            return getString(R.string.scan_passport);
        } else if (getDocumentType() == DocumentType.EID) {
            return (mode == DocumentPageType.FRONT) ?
                    getString(R.string.scan_front_eid) :
                    getString(R.string.scan_back_eid);
        }

        return getString(R.string.scan);

    }

    private String getStepString(DocumentPageType mode) {
        if (getDocumentType() == DocumentType.PASSPORT) {
            return "Step 1 of 1";
        } else if (getDocumentType() == DocumentType.EID) {
            return (mode == DocumentPageType.FRONT) ?
                    "Step 1 of 2" :
                    "Step 2 of 2";
        }

        return getString(R.string.scan);

    }
}
