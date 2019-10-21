package com.digitify.identityscanner.docscanner.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.arch.SingleLiveEvent;
import com.digitify.identityscanner.core.arch.WorkerHandler;
import com.digitify.identityscanner.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.docscanner.enums.DocumentType;
import com.digitify.identityscanner.docscanner.interfaces.ICamera;
import com.digitify.identityscanner.docscanner.states.CameraState;
import com.digitify.identityscanner.base.BaseAndroidViewModel;

import java.io.File;

import co.yap.translation.Strings;

public class CameraViewModel extends BaseAndroidViewModel implements ICamera.ViewModel {
    private WorkerHandler workHandler = WorkerHandler.get("camera_processing");

    private int mFrameCount = 0;
    private int FRAME_PROCESS_RATE = 3;

    private DocumentType documentType;
    private DocumentPageType scanMode;
    private SingleLiveEvent<String> capturedImage;
    private SingleLiveEvent<String> capturedDocument;
    private CameraState state;

    public CameraViewModel(@NonNull Application application) {
        super(application);
        reset();
    }

    @Override
    public void reset() {
        getState().reset();
        getState().setSubmitButtonTitle(getString(Strings.idenetity_scanner_sdk_screen_scanner_button_scan));
    }

    @Override
    public void handleOnPressCapture(File file) {
        if (validateFile(file.getAbsolutePath())) setCapturedImage(file.getAbsolutePath());
        getState().setCapturing(false);
    }
    //    @Override
//    public synchronized void processFrame(Mat frame) {
//        if (mFrameCount++ % FRAME_PROCESS_RATE != 0) return;
//        workHandler.post(() -> {
//            switch (getDocumentType()) {
//                case EID:
//                    proceedWithCardDetection(frame);
//                    break;
//                case PASSPORT:
//                    proceedWithMrzDetection(frame);
//                    break;
//            }
//        });
//    }

//    private void proceedWithMrzDetection(Mat frame) {
//        Passport passport = getPassportDetector().detect(frame);
//        validateImageReadiness(passport);
//    }

//    private void proceedWithCardDetection(Mat frame) {
//        Card card = getCardDetector().detect(frame);
//        validateImageReadiness(card);
//    }
//
//    private void validateImageReadiness(Entity card) {
//        if (card.isValid()) {
//            // we found a card. so show the visual
//            getState().setCardRect(card.getBoundingBox());
//            return;
//        }
//
//        getState().setCardRect(null);
//    }

//    @Override
//    public void handleOnPressCapture(Mat snapshot) {
//        if (!getState().isCapturing()) {
//            getState().setCapturing(true);
//            if (!snapshot.empty()) {
//                Bitmap image = OpenCVUtils.convertToBitmap(snapshot);
//                String file = ImageUtils.savePicture(getApplication().getApplicationContext(), image);
//                if (validateFile(file)) setCapturedImage(file);
//            }
//            getState().setCapturing(false);
//        }
//    }

//    @Override
//    public void handleOnPressQuickCapture(Mat snapshot) {
//        if (!getState().isCapturing()) {
//
//            getState().setCapturing(true);
//            Entity entity = null;
//            if (getDocumentType() == DocumentType.PASSPORT)
//                entity = getPassportDetector().detect(snapshot);
//            if (getDocumentType() == DocumentType.EID) entity = getCardDetector().detect(snapshot);
//
//            if (entity != null && entity.isValid()) {
//                String file = ImageUtils.savePicture(getApplication().getApplicationContext(), entity.getBitmap());
//                if (validateFile(file)) setCapturedDocument(file);
//            } else {
//                setInstructions(getString(Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_detecting_document));
//            }
//            getState().setCapturing(false);
//        }
//    }

    private boolean validateFile(String file) {
        if (TextUtils.isEmpty(file)) {
            // This is most probably a developer's mistake. Handle it.
            setInstructions(getString(Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_saving_file));
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

//    public CardDetector getCardDetector() {
//        if (cardDetector == null) cardDetector = new CardDetector();
//        return cardDetector;
//    }

//    public PassportDetector getPassportDetector() {
//        if (passportDetector == null) passportDetector = new PassportDetector();
//        return passportDetector;
//    }

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
                    getString(Strings.idenetity_scanner_sdk_screen_scanner_display_text_front_side) :
                    getString(Strings.idenetity_scanner_sdk_screen_scanner_display_text_back_side);
        }

        return getString(Strings.idenetity_scanner_sdk_screen_scanner_button_scan);

    }

    private String getStepString(DocumentPageType mode) {
        if (getDocumentType() == DocumentType.PASSPORT) {
            return getString(Strings.idenetity_scanner_sdk_screen_scanner_display_text_step_1);
        } else if (getDocumentType() == DocumentType.EID) {
            return (mode == DocumentPageType.FRONT) ?
                    getString(Strings.idenetity_scanner_sdk_screen_scanner_display_text_step_1) :
                    getString(Strings.idenetity_scanner_sdk_screen_scanner_display_text_step_2);
        }

        return getString(Strings.idenetity_scanner_sdk_screen_scanner_button_scan);

    }
}
