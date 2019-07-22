package com.digitify.identityscanner.modules.docvalidator.states;

import android.graphics.Bitmap;

import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.components.RecordButton;
import com.digitify.identityscanner.core.arch.SingleLiveEvent;
import com.digitify.identityscanner.states.State;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

public class SelfieVideoState extends State {
    /**
     * Event to tell the view to start recording
     */
    private SingleLiveEvent<Boolean> startRecording = new SingleLiveEvent<>();

    /**
     * Event to tell the view to stop recording
     */
    private SingleLiveEvent<Boolean> stopRecording = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> validatorResultCompleteTrigger = new SingleLiveEvent<>();

    private MutableLiveData<Boolean> validDocumentFace = new MutableLiveData<>();

    private Bitmap faceBitmap;
    private String voiceValidationText;
    private String faceValidationText;
    private String blinkValidationText;
    private String flashMessage;
    private String warning;
    private RecordButton.ButtonState recordingButtonState;
    private boolean loading;

    public SelfieVideoState() {
        reset();
    }

    @Override
    public void reset() {
        faceBitmap = null;
        voiceValidationText = "";
        faceValidationText = "";
        blinkValidationText = "";
        flashMessage = "";
        warning = "";
        loading = false;
        recordingButtonState = RecordButton.ButtonState.START_RECORDING;
    }

    @Bindable
    public Bitmap getFaceBitmap() {
        return faceBitmap;
    }

    public void setFaceBitmap(Bitmap faceBitmap) {
        this.faceBitmap = faceBitmap;
        notifyPropertyChanged(BR.faceBitmap);
    }

    @Bindable
    public String getVoiceValidationText() {
        return voiceValidationText;
    }

    public void setVoiceValidationText(String voiceValidationText) {
        this.voiceValidationText = voiceValidationText;
        notifyPropertyChanged(BR.voiceValidationText);
    }

    @Bindable
    public String getBlinkValidationText() {
        return blinkValidationText;
    }

    public void setBlinkValidationText(String blinkValidationText) {
        this.blinkValidationText = blinkValidationText;
        notifyPropertyChanged(BR.blinkValidationText);
    }

    @Bindable
    public String getFaceValidationText() {
        return faceValidationText;
    }

    public void setFaceValidationText(String faceValidationText) {
        this.faceValidationText = faceValidationText;
        notifyPropertyChanged(BR.faceValidationText);
    }

    @Bindable
    public String getFlashMessage() {
        return flashMessage;
    }

    public void setFlashMessage(String flashMessage) {
        this.flashMessage = flashMessage;
        notifyPropertyChanged(BR.flashMessage);
    }

    @Bindable
    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
        notifyPropertyChanged(BR.warning);
    }

    @Bindable
    public RecordButton.ButtonState getRecordingButtonState() {
        return recordingButtonState;
    }

    public void setRecordingButtonState(RecordButton.ButtonState recordingButtonState) {
        this.recordingButtonState = recordingButtonState;
        notifyPropertyChanged(BR.recordingButtonState);
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public SingleLiveEvent<Boolean> getStartRecordingEvent() {
        if (startRecording == null) startRecording = new SingleLiveEvent<>();
        return startRecording;
    }

    public SingleLiveEvent<Boolean> getStopRecordingEvent() {
        if (stopRecording == null) stopRecording = new SingleLiveEvent<>();
        return stopRecording;
    }

    public MutableLiveData<Boolean> getIsValidDocumentFace() {
        if (validDocumentFace == null) validDocumentFace = new MutableLiveData<>();
        return validDocumentFace;
    }

    public SingleLiveEvent<Boolean> getValidatorResultCompleteTrigger() {
        if (validatorResultCompleteTrigger == null) validatorResultCompleteTrigger = new SingleLiveEvent<>();
        return validatorResultCompleteTrigger;
    }
}
