package com.digitify.identityscanner.modules.docscanner.states;

import androidx.databinding.Bindable;
import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.modules.docscanner.enums.ImageReadinessStatus;
import com.digitify.identityscanner.states.State;
import org.opencv.core.Rect;

public class CameraState extends State {
    private String title;
    private String instructions;
    private String stepInstructions;
    private String submitButtonTitle;
    private Rect cardRect;
    private boolean capturing;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
        notifyPropertyChanged(BR.instructions);
    }

    @Bindable
    public String getStepInstructions() {
        return stepInstructions;
    }

    public void setStepInstructions(String stepInstructions) {
        this.stepInstructions = stepInstructions;
        notifyPropertyChanged(BR.stepInstructions);
    }

    @Bindable
    public String getSubmitButtonTitle() {
        return submitButtonTitle;
    }

    public void setSubmitButtonTitle(String submitButtonTitle) {
        this.submitButtonTitle = submitButtonTitle;
        notifyPropertyChanged(BR.submitButtonTitle);
    }

    @Bindable
    public Rect getCardRect() {
        return cardRect;
    }

    public void setCardRect(Rect cardRect) {
        this.cardRect = cardRect;
        notifyPropertyChanged(BR.cardRect);
    }

    @Bindable
    public boolean isCapturing() {
        return capturing;
    }

    public void setCapturing(boolean capturing) {
        this.capturing = capturing;
    }

    @Override
    public void reset() {
        title = "";
        instructions = "";
        stepInstructions = "";
        cardRect = null;
        capturing = false;
        notifyChange();
    }
}
