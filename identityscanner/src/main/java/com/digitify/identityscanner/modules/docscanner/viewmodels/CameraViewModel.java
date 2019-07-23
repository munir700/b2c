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
    private WorkerHandler mrzHandler = WorkerHandler.get("mrz_detector");

    private int mFrameCount = 0;
    private int FRAME_PROCESS_RATE = 3;

    private DocumentType documentType;
    private SingleLiveEvent<String> capturedImage;
    private SingleLiveEvent<String> capturedDocument;
    private CameraState state;
    private CardDetector cardDetector;
    private MrzDetector mrzDetector;
    private PassportDetector passportDetector;

    public CameraViewModel(@NonNull Application application) {
        super(application);
        init();
        reset();
    }

    private void init() {

        // only for debugging purpose
        if (Constants.SHOW_DEBUG_VIEWS) {
            // Observe if Card is detected.. then do some more processing on card
            getState().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if (propertyId == BR.cardPreview) {
                        findMrz(getState().getCardPreview());
                    }
                }
            });
        }
    }

    @Override
    public void reset() {
        getState().reset();
    }

    private void findMrz(final Bitmap bitmap) {
        mrzHandler.post(() -> {
            if (bitmap != null) {
                Mrz mrz = getMrzDetector().detect(bitmap);
                if (getMrzDetector().validate(mrz)) {
                    getState().setMrzPreview(getDocumentType() == DocumentType.EID ? OpenCVUtils.binaryText(mrz.getBitmap()) : mrz.getBitmap());
                } else {
                    getState().setMrzPreview(null);
                }
            }
        });
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
            getState().setImageReadinessStatus(ImageReadinessStatus.OK);
            getState().setCardPreview(OpenCVUtils.convertToBitmap(card.getDetection()));
            return;
        }

        getState().setCardRect(null);
        getState().setImageReadinessStatus(ImageReadinessStatus.NOK);
        getState().setCardPreview(null);
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
    public void setTitle(String title) {
        getState().setTitle(title);
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

    public MrzDetector getMrzDetector() {
        if (mrzDetector == null) mrzDetector = new MrzDetector();
        return mrzDetector;
    }

    public PassportDetector getPassportDetector() {
        if (passportDetector == null) passportDetector = new PassportDetector();
        return passportDetector;
    }
}
