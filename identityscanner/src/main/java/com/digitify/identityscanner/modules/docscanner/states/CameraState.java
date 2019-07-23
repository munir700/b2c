package com.digitify.identityscanner.modules.docscanner.states;

import android.graphics.Bitmap;

import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.modules.docscanner.enums.ImageReadinessStatus;
import com.digitify.identityscanner.states.State;

import org.opencv.core.Rect;

import androidx.databinding.Bindable;

public class CameraState extends State {
    private String title;
    private String instructions;
    private ImageReadinessStatus imageReadinessStatus;
    private Rect cardRect;
    private Bitmap cardPreview;
    private Bitmap mrzPreview;
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
    public ImageReadinessStatus getImageReadinessStatus() {
        return imageReadinessStatus;
    }

    public void setImageReadinessStatus(ImageReadinessStatus status) {
        this.imageReadinessStatus = status;
        notifyPropertyChanged(BR.imageReadinessStatus);
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
    public Bitmap getCardPreview() {
        return cardPreview;
    }

    public void setCardPreview(Bitmap cardPreview) {
        this.cardPreview = cardPreview;
        notifyPropertyChanged(BR.cardPreview);
    }

    @Bindable
    public Bitmap getMrzPreview() {
        return mrzPreview;
    }

    public void setMrzPreview(Bitmap mrzPreview) {
        this.mrzPreview = mrzPreview;
        notifyPropertyChanged(BR.mrzPreview);
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
        imageReadinessStatus = ImageReadinessStatus.NOK;
        cardRect = null;
        cardPreview = null;
        capturing = false;
        notifyChange();
    }
}
