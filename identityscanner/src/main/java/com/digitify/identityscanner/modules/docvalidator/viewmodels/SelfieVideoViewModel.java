package com.digitify.identityscanner.modules.docvalidator.viewmodels;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.components.RecordButton;
import com.digitify.identityscanner.core.arch.MainHandler;
import com.digitify.identityscanner.core.arch.TaskCounter;
import com.digitify.identityscanner.core.arch.WorkerHandler;
import com.digitify.identityscanner.core.detection.detectors.FaceDetector;
import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.core.recognition.models.FaceMatchResult;
import com.digitify.identityscanner.core.recognition.recognisers.FaceRecogniser;
import com.digitify.identityscanner.modules.docvalidator.enums.AliveHumanValidation;
import com.digitify.identityscanner.modules.docvalidator.helpers.HumanAliveValidator;
import com.digitify.identityscanner.modules.docvalidator.interfaces.ISelfieVideo;
import com.digitify.identityscanner.modules.docvalidator.models.ComparisonResult;
import com.digitify.identityscanner.modules.docvalidator.models.IdentityValidatorResult;
import com.digitify.identityscanner.modules.docvalidator.states.SelfieVideoState;
import com.digitify.identityscanner.utils.ImageUtils;
import com.digitify.identityscanner.viewmodels.BaseAndroidViewModel;
import org.opencv.core.Mat;

public class SelfieVideoViewModel extends BaseAndroidViewModel implements
        ISelfieVideo.ViewModel,
        RecordButton.RecordingListener,
        TaskCounter.OnTaskCountCompletedListener
        // VideoRecorder.OnVideoRecordingListener
    {

    /**
     * Worker thread to perform Face Recognition tasks
     */
    private WorkerHandler worker = WorkerHandler.get("worker");
    private WorkerHandler framer = WorkerHandler.get("framer");

    /**
     * holds the state of what is being displayed on screen
     */
    private SelfieVideoState state;

    /**
     * Helpers class to perform all liveliness check validations on frames coming from camera
     */
    private HumanAliveValidator humanValidator;
    private FaceRecogniser faceRecogniser;
    private FaceDetector faceDetector;

    /**
     * variables for face to be recognised
     */
    private String documentPath;
    private Face documentFace;
    private boolean requestRecognition = false; // flag to trigger recognition request of documentFace and current face in frame

    private LifecycleOwner lifecycleOwner;

    private boolean isValidating = false;
    private boolean isRecording = false;

    private FaceMatchResult faceMatchResult = null;
    private IdentityValidatorResult validatorResult = null;

    // private VideoRecorder videoRecorder = new VideoRecorder();

    private TaskCounter taskCounter;

    public SelfieVideoViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        validatorResult = new IdentityValidatorResult();
        // getVideoRecorder().setOnVideoRecordingListener(this);
        reset();
        initFaceDetectorAsync();
    }

    private void reset() {
        stopHumanValidations();
        resetHumanValidations();
        stopRecording();
        getState().reset();
        faceMatchResult = null;
        validatorResult = null;
        requestRecognition = false;
        getTaskCounter().reset();
        resetFlashMessage();
    }

    @Override
    public void attach(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.lifecycleOwner = lifecycleOwner;
        if (humanValidator != null) {
            // async initialisation is complete so attach the lifecycle right away
            humanValidator.attachLifeCycle(lifecycleOwner);
        }
    }

    private void initFaceDetectorAsync() {
        getState().setLoading(true);
        // face detector loads a large file that takes time. so load it in a new thread.
        worker.post(() -> {
            faceDetector = new FaceDetector(getApplication().getApplicationContext(), true);
            getState().setLoading(false);
            MainHandler.get().post(this::onFaceDetectorInitComplete);
        });
    }

    private void onFaceDetectorInitComplete() {
        initiateHumanValidator();
        if (lifecycleOwner != null) {
            // lifecycleOwner was requested to be attached
            humanValidator.attachLifeCycle(lifecycleOwner);
        }
    }

    private void initiateHumanValidator() {
        humanValidator = new HumanAliveValidator(getApplication().getApplicationContext(), getFaceDetector());
        humanValidator.setAliveHumanValidationListener(new HumanAliveValidator.AliveHumanValidationListener() {
            @Override
            public void onValidationStart(AliveHumanValidation validation) {
                switch (validation) {
                    case BLINK:
                        getValidatorResult().setValidation(AliveHumanValidation.BLINK.toString(), false);
                        int blinks = humanValidator.getRandomBlinksCountValidator().getTotalBlinksRequired();
                        getState().setFlashMessage("Blink " + blinks + " times");
                        getState().setBlinkValidationText(getString(R.string.validation_checking));
                        break;
                    case CLEAR_FACE:
                        getTaskCounter().reset();
                        getValidatorResult().setValidation(AliveHumanValidation.CLEAR_FACE.toString(), false);
                        getState().setFlashMessage(getString(R.string.validation_flash_clear_face));
                        getState().setFaceValidationText(getString(R.string.validation_checking));
                        break;
                    case VOICE_CHECK:
                        getValidatorResult().setValidation(AliveHumanValidation.VOICE_CHECK.toString(), false);
                        String text = humanValidator.getSpeechValidator().getSpeechText();
                        getState().setFlashMessage(getString(R.string.validation_flash_speak_aloud) + " '" + text + "'");
                        getState().setVoiceValidationText(getString(R.string.validation_checking));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onValidationComplete(AliveHumanValidation validation) {
                switch (validation) {
                    case BLINK:
                        requestRecognition = true; // start the face recognition procedure with current frame
                        getValidatorResult().setValidation(AliveHumanValidation.BLINK.toString(), true);
                        getState().setBlinkValidationText(getString(R.string.ok));
                        break;
                    case CLEAR_FACE:
                        getValidatorResult().setValidation(AliveHumanValidation.CLEAR_FACE.toString(), true);
                        getState().setFaceValidationText(getString(R.string.ok));
                        break;
                    case VOICE_CHECK:
                        getValidatorResult().setValidation(AliveHumanValidation.VOICE_CHECK.toString(), true);
                        stopHumanValidations();
                        getState().setVoiceValidationText(getString(R.string.ok));
                        onLiveValidationsComplete();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onValidationFailed(AliveHumanValidation validation) {
                getTaskCounter().reset();
                resetFlashMessage();
                faceMatchResult = null;
                resetHumanValidations();
                stopHumanValidations();

                switch (validation) {
                    case BLINK:
                        getValidatorResult().setValidation(AliveHumanValidation.BLINK.toString(), false);
                        getState().setBlinkValidationText(getString(R.string.nok));
                        break;
                    case CLEAR_FACE:
                        getValidatorResult().setValidation(AliveHumanValidation.CLEAR_FACE.toString(), false);
                        resetValidationMessages();
                        // restart this validation
                        startHumanAliveValidations();
                        break;
                    case VOICE_CHECK:
                        getValidatorResult().setValidation(AliveHumanValidation.VOICE_CHECK.toString(), false);
                        getState().setVoiceValidationText(getString(R.string.nok));
                        break;
                    default:
                        break;
                }
            }
        });

        humanValidator.setOnExpressionDetectedListener(new HumanAliveValidator.OnExpressionDetectedListener() {
            @Override
            public void onFaceDetected(Face face) {
                if (requestRecognition) {
                    requestRecognition = false;
                    requestFaceMatch(documentFace, face);
                }
            }

            @Override
            public void onBlinkDetected(int count) {
                getState().setBlinkValidationText("" + count);
            }
        });
    }

    private void resetValidationMessages() {
        getState().setBlinkValidationText("");
        getState().setFaceValidationText("");
        getState().setVoiceValidationText("");
    }

    private void resetFlashMessage() {
        getState().setFlashMessage(getString(R.string.validation_flash_press_recording_btn));
    }

    @Override
    public void requestFaceMatch(Face docFace, Face frameFace) {
        worker.post(() -> {
            faceMatchResult = getFaceRecogniser().match(docFace, frameFace);
            // mark this task as complete
            getTaskCounter().count();
        });
    }

    @Override
    public SelfieVideoState getState() {
        if (state == null) state = new SelfieVideoState();
        return state;
    }

    @Override
    public void onCameraStarted(int width, int height) {
//        MainHandler.get().post(() -> getVideoRecorder().onUpdateFrameSize(new Size(width, height), 40));
    }

    @Override
    public void processFrame(Mat frame) {
        Mat clone = frame.clone();
        framer.post(() -> {
            if (isValidating && humanValidator != null) {
                humanValidator.processFrame(clone);
            }
//            if (getVideoRecorder().isRecording()) {
//                getVideoRecorder().onFrame(ImageUtils.convertMatToByteArray(clone));
//            }
            clone.release();
        });
    }

    @Override
    public void onLiveValidationsComplete() {
        // start recording the video now
        startRecording();
    }

    private void startHumanAliveValidations() {
        if (!isValidating && humanValidator != null) {
            isValidating = true;
            resetValidationMessages();
            // humanValidator.resetValidations();
            humanValidator.startValidations();
            // set button state to indicate the validations in progress
            RecordButton.ButtonState state = getState().getRecordingButtonState();
            getState().setRecordingButtonState(state == RecordButton.ButtonState.STOP_RECORDING ? RecordButton.ButtonState.START_RECORDING : RecordButton.ButtonState.STOP_RECORDING);
        }
    }

    private void resetHumanValidations() {
        if (humanValidator != null) humanValidator.resetValidations();
    }

    private void stopHumanValidations() {
        isValidating = false;
        getState().setRecordingButtonState(RecordButton.ButtonState.START_RECORDING);
    }

    @Override
    public void onClickRecordButton() {

        if (!isValidating && !isRecording && !getState().isLoading()) {
            resetHumanValidations();
            startHumanAliveValidations();
        }
    }

    public void startRecording() {
        // run in separate thread
        isRecording = true;
//        getVideoRecorder().setRecordingFile(ImageUtils.getFilePublically(Constants.KYC_SDK__VIDEO_PATH, "video.mp4").getAbsolutePath());
//        getVideoRecorder().startRecording();
    }

    public void stopRecording() {
        isRecording = false;
//        getVideoRecorder().stopRecording();
    }

    @Override
    public void onRecordingAnimationStart() {
        getState().setFlashMessage(getString(R.string.validation_flash_recording_video));
    }

    @Override
    public void onRecordingAnimationComplete() {
        // stop the recorder now
        stopRecording();
        getState().setFlashMessage(getString(R.string.validation_flash_recording_complete));
        // onVideoRecorded(getVideoRecorder().getVideoFile());

        if (getFaceMatchResult() == null) {
            // wait for face match results and show the loader
            getState().setLoading(true);
        }

        getTaskCounter().count();
    }

//    @Override
//    public void onVideoRecorderInitialising() {
//        getState().setLoading(true);
//    }
//
//    @Override
//    public void onVideoRecorderInitialised() {
//        getState().setLoading(false);
//    }
//
//    @Override
//    public void onVideoRecordingStarted() {
//        // start the recording animation now
//        getState().getStartRecordingEvent().setValue(true);
//    }
//
//    @Override
//    public void onVideoRecordingStopped() {
//        // stop the recording animation now
//        getState().getStopRecordingEvent().setValue(true);
//    }

    @Override
    public void onVideoRecorded(String file) {
        getValidatorResult().setVideoPath(file);
    }

    @Override
    public void onVideoRecordingFailed() {
    }

    @Override
    public void onTaskCountCompleted() {
        // always run in main thread
        MainHandler.get().post(() -> {
            // Recording and Recognising tasks are complete so prepare results now
            getState().setLoading(false);
            prepareResults();
        });
    }

    private void prepareResults() {
        ComparisonResult result = new ComparisonResult();
        result.setConfidence(Math.round(getFaceMatchResult().getConfidence())); // round off
        result.setMatched(getFaceMatchResult().isMatched());
        String sourceImg = ImageUtils.savePicture(getContext(), getFaceMatchResult().getSource().getBitmap(), "recog_source" + System.currentTimeMillis() + ".jpg");
        String targetImg = ImageUtils.savePicture(getContext(), getFaceMatchResult().getTarget().getBitmap(), "recog_target" + System.currentTimeMillis() + ".jpg");
        result.setSourceImage(sourceImg);
        result.setTargetImage(targetImg);
        getValidatorResult().setComparison(result);

        getState().getValidatorResultCompleteTrigger().setValue(true);
    }

    @Nullable
    public Face getFaceFromDocument(String file) {
        // Initialising new detector without deep analysing
        FaceDetector detector = new FaceDetector(getApplication().getApplicationContext());
        Face face = detector.detect(ImageUtils.getBitmapFromStorage(file));
        if (detector.validate(face)) {
            return face;
        }
        return null;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
        // detect face from image provided
        if (!TextUtils.isEmpty(documentPath) && getFaceDetector() != null) {
            documentFace = getFaceFromDocument(documentPath);
        }

        // detect face from image provided if we have documentPath up-till now
        if (!TextUtils.isEmpty(documentPath)) {
            documentFace = getFaceFromDocument(documentPath);
            boolean isValid = (documentFace != null);
            getState().getIsValidDocumentFace().setValue(isValid);
            if (isValid) {
                getState().setFaceBitmap(documentFace.getBitmap());
            }
        }
    }

    private FaceRecogniser getFaceRecogniser() {
        if (faceRecogniser == null)
            faceRecogniser = new FaceRecogniser(getApplication().getApplicationContext());
        return faceRecogniser;
    }

    private FaceDetector getFaceDetector() {
        return faceDetector;
    }

    private FaceMatchResult getFaceMatchResult() {
        return faceMatchResult;
    }

    public IdentityValidatorResult getValidatorResult() {
        if (validatorResult == null) validatorResult = new IdentityValidatorResult();
        return validatorResult;
    }

    private TaskCounter getTaskCounter() {
        if (taskCounter == null) {
            taskCounter = new TaskCounter(2); // count for 2 tasks to be completed
            taskCounter.setOnTaskCountCompletedListener(this);
        }
        return taskCounter;
    }

//    private VideoRecorder getVideoRecorder() {
//        if (videoRecorder == null) {
//            videoRecorder = new VideoRecorder();
//        }
//        return videoRecorder;
//    }

    @Override
    public void onStop() {
        super.onStop();
//        getVideoRecorder().destroy();
    }

}
