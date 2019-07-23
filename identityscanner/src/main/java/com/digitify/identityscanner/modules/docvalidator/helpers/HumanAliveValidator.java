package com.digitify.identityscanner.modules.docvalidator.helpers;

import android.content.Context;
import android.graphics.Bitmap;

import com.digitify.identityscanner.core.arch.MainHandler;
import com.digitify.identityscanner.core.arch.WorkerHandler;
import com.digitify.identityscanner.core.detection.detectors.FaceDetector;
import com.digitify.identityscanner.core.detection.interfaces.IExpressionsAnalyser;
import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;
import com.digitify.identityscanner.interfaces.ILIfeCycle;
import com.digitify.identityscanner.modules.docvalidator.enums.AliveHumanValidation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opencv.core.Mat;

import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class HumanAliveValidator implements ILIfeCycle,
        LifecycleObserver,
        RandomBlinksCountValidator.OnBlinkValidationListener,
        SpeechValidator.SpeechValidationListener {

    protected static String TAG = HumanAliveValidator.class.getSimpleName();
    private WorkerHandler faceHandler = WorkerHandler.get("human_validator");
    private boolean processingFrame = false;

    private AliveHumanValidationListener aliveHumanValidationListener;
    private OnExpressionDetectedListener onExpressionDetectedListener;
    private LifecycleOwner lifecycleOwner;
    private Context context;

    private FaceDetector faceDetector;
    private SpeechValidator speechValidator;
    private RandomBlinksCountValidator randomBlinksCountValidator;

    // The validations need to perform to check liveliness.
    private AliveHumanValidation[] validations = AliveHumanValidation.values();
    private ObservableInt currentValidationIndex = new ObservableInt();

    // Voice validation related variables
    private boolean validatingVoice = false;
    private int voiceCheckAttemptCount = 0;

    public HumanAliveValidator(Context context, FaceDetector faceDetector) {
        this.faceDetector = faceDetector;
        this.context = context;
    }

    public HumanAliveValidator(Context context, FaceDetector faceDetector, LifecycleOwner lifecycleOwner) {
        this(context, faceDetector);
        attachLifeCycle(lifecycleOwner);
    }

    public void attachLifeCycle(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onStart() {

        // initialise and listen blink detector
        randomBlinksCountValidator = new RandomBlinksCountValidator(new BlinksCounter(getExpressionAnalyser()));
        randomBlinksCountValidator.setOnBlinkValidationListener(this);

        // initialise SpeechToTextManager
        speechValidator = new SpeechValidator(getContext(), getLifecycleOwner());
        speechValidator.setSpeechValidationListener(this);

        // Listen for speech validation to start
        currentValidationIndex.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (getValidationAtIndex(currentValidationIndex.get()) == AliveHumanValidation.VOICE_CHECK) {
                    // start voice validation now
                    if (!validatingVoice) {
                        validatingVoice = true;
                        validateVoice();
                    }
                }
            }
        });

        resetValidations();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onStop() {
        if (getRandomBlinksCountValidator() != null) getRandomBlinksCountValidator().destroy();
        if (getSpeechValidator() != null) getSpeechValidator().destroy();
    }

    public void startValidations() {
        processingFrame = false;
        currentValidationIndex.set(0);
        publishValidationStart(getValidationAtIndex(currentValidationIndex.get()));
    }

    /**
     * Function to get the frames coming from video/camera
     *
     * @param frame
     */
    public void processFrame(Mat frame) {
        if (processingFrame) return;
        Mat clone = frame.clone();
        faceHandler.post(() -> {
            processingFrame = true;
            Face face = validateFaceInFrame(clone);
            if (face != null) {
                getOnExpressionDetectedListener().onFaceDetected(face);
                performValidation(face, getValidationAtIndex(currentValidationIndex.get()));
            }
            clone.release();
            processingFrame = false;
        });

    }

    @Nullable
    private Face validateFaceInFrame(Mat src) {
        if (src != null && !src.empty()) {
            Face face = getFaceDetector().detect(src);
            if (getFaceDetector().validate(face)) {
                return face;
            } else {
                publishValidationError(AliveHumanValidation.CLEAR_FACE);
            }
        } else {
            // not valid data.
            publishValidationError(AliveHumanValidation.CLEAR_FACE);
        }
        return null;
    }


    @Contract(pure = true)
    private void validateClearFace(@NotNull Face face) {
        // if face is clear and both eyes open
        boolean validated = getFaceDetector().validate(face);

        if (validated) {
            // check eyes open
            validated = getExpressionAnalyser().isRightEyeOpen(face) || getFaceDetector().isLeftEyeOpen(face);
        }

        if (validated) {
            publishValidationSuccess(AliveHumanValidation.CLEAR_FACE);
        } else {
            publishValidationError(AliveHumanValidation.CLEAR_FACE);
        }
    }

    @Contract(pure = true)
    private void validateBlinks(@NotNull Face face) {
        getRandomBlinksCountValidator().feed(face);
    }

    @Override
    public void onBlinkCount(int count) {
        getOnExpressionDetectedListener().onBlinkDetected(count);
    }

    @Override
    public void onBlinksValidated() {
        publishValidationSuccess(AliveHumanValidation.BLINK);
    }

    @Override
    public void onBlinksValidationFailed() {
        publishValidationError(AliveHumanValidation.BLINK);
    }

    @Contract(pure = true)
    private void validateVoice() {
        MainHandler.get().post(() -> {
            validatingVoice = true;
            getSpeechValidator().startValidation();
        });
    }

    @Override
    public void onSpeechValidated() {
        voiceCheckAttemptCount = 0;
        publishValidationSuccess(AliveHumanValidation.VOICE_CHECK);
        validatingVoice = false;
    }

    @Override
    public void onSpeechValidationStart() {
        voiceCheckAttemptCount++;
        publishValidationStart(AliveHumanValidation.VOICE_CHECK);
    }

    @Override
    public void onSpeechValidationError(int error) {
        // retry if it is a mismatch
        if (voiceCheckAttemptCount >= 3) {
            // throw validation error
            voiceCheckAttemptCount = 0;
            publishValidationError(AliveHumanValidation.VOICE_CHECK);
            validatingVoice = false;
        } else {
            // retry
            getSpeechValidator().reset();
            getSpeechValidator().startValidation();
        }

    }

    private void performValidation(@NotNull Face face, @NotNull AliveHumanValidation validation) {
        switch (validation) {
            case CLEAR_FACE:
                validateClearFace(face);
                break;
            case VOICE_CHECK:
                // Voice validation will start from observing the currentValidationIndex.. to avoid repetitive calls to validateVoice()
                break;
            case BLINK:
                validateBlinks(face);
                break;
        }
    }

    AliveHumanValidation getValidationAtIndex(int index) {
        if (index < 0) index = 0;
        if (index >= validations.length) index = validations.length - 1;
        return validations[index];
    }

    public void resetValidations() {
        currentValidationIndex.set(0);
        getRandomBlinksCountValidator().reset();
        MainHandler.get().post(() -> {
            getSpeechValidator().reset();
            validatingVoice = false;
        });
    }

    private void publishValidationError(AliveHumanValidation validation) {
        getAliveHumanValidationListener().onValidationFailed(validation);
    }

    private void publishValidationSuccess(AliveHumanValidation validation) {
        currentValidationIndex.set(currentValidationIndex.get() + 1); // increment
        getAliveHumanValidationListener().onValidationComplete(validation);

        // automatically start next validation
        if (currentValidationIndex.get() < (validations.length - 1)) {
            publishValidationStart(getValidationAtIndex(currentValidationIndex.get()));
        }
    }

    private void publishValidationStart(AliveHumanValidation validation) {
        getAliveHumanValidationListener().onValidationStart(validation);
    }

    /*********************************** Getters and Setters **************************************/

    public SpeechValidator getSpeechValidator() {
        return speechValidator;
    }

    public RandomBlinksCountValidator getRandomBlinksCountValidator() {
        return randomBlinksCountValidator;
    }

    public FaceDetector getFaceDetector() {
        return faceDetector;
    }

    public IExpressionsAnalyser<Face> getExpressionAnalyser() {
        return getFaceDetector();
    }

    public AliveHumanValidationListener getAliveHumanValidationListener() {
        if (aliveHumanValidationListener == null)
            aliveHumanValidationListener = AliveHumanValidationListener.DEFAULT;
        return aliveHumanValidationListener;
    }

    public void setAliveHumanValidationListener(AliveHumanValidationListener aliveHumanValidationListener) {
        this.aliveHumanValidationListener = aliveHumanValidationListener;
    }

    public OnExpressionDetectedListener getOnExpressionDetectedListener() {
        if (onExpressionDetectedListener == null)
            onExpressionDetectedListener = OnExpressionDetectedListener.DEFAULT;
        return onExpressionDetectedListener;
    }

    public void setOnExpressionDetectedListener(OnExpressionDetectedListener onExpressionDetectedListener) {
        this.onExpressionDetectedListener = onExpressionDetectedListener;
    }

    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    public Context getContext() {
        return context;
    }


    /*********************************** Enums and Listeners **************************************/

    public interface OnExpressionDetectedListener {
        void onFaceDetected(Face face);

        void onBlinkDetected(int count);

        OnExpressionDetectedListener DEFAULT = new OnExpressionDetectedListener() {
            @Override
            public void onFaceDetected(Face face) {

            }

            @Override
            public void onBlinkDetected(int count) {

            }
        };
    }

    public interface AliveHumanValidationListener {
        void onValidationStart(AliveHumanValidation validation);

        void onValidationComplete(AliveHumanValidation validation);

        void onValidationFailed(AliveHumanValidation validation);

        AliveHumanValidationListener DEFAULT = new AliveHumanValidationListener() {
            @Override
            public void onValidationComplete(AliveHumanValidation validation) {

            }

            @Override
            public void onValidationFailed(AliveHumanValidation validation) {

            }

            @Override
            public void onValidationStart(AliveHumanValidation validation) {

            }
        };
    }
}
