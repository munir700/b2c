package com.digitify.identityscanner.modules.docvalidator.helpers;

import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.utils.NumberUtils;

public class RandomBlinksCountValidator implements BlinksCounter.OnBlinkCountListener {
    interface OnBlinkValidationListener {
        void onBlinkCount(int count);

        void onBlinksValidated();

        void onBlinksValidationFailed();

        RandomBlinksCountValidator.OnBlinkValidationListener DEFAULT = new OnBlinkValidationListener() {
            @Override
            public void onBlinkCount(int count) {

            }

            @Override
            public void onBlinksValidated() {

            }

            @Override
            public void onBlinksValidationFailed() {

            }
        };
    }

    private int totalBlinksRequired = 3;
    private BlinksCounter blinksCounter;
    private OnBlinkValidationListener onBlinkValidationListener;

    public RandomBlinksCountValidator(BlinksCounter blinksCounter) {
        this.blinksCounter = blinksCounter;
        blinksCounter.setOnBlinkCountListener(this);
        reset();
    }

    public void feed(Face face) {
        this.blinksCounter.analyse(face);
    }

    @Override
    public void onBlinkCount(int count) {
        getOnBlinkValidationListener().onBlinkCount(count);
        if (count == totalBlinksRequired) {
            getOnBlinkValidationListener().onBlinksValidated();
        }
    }

    public void destroy() {
        if (blinksCounter != null) {
            blinksCounter.destroy();
        }
    }

    public void setTotalBlinksRequired(int totalBlinksRequired) {
        this.totalBlinksRequired = totalBlinksRequired;
    }

    public int getTotalBlinksRequired() {
        return totalBlinksRequired;
    }

    public void reset() {
        this.blinksCounter.reset();
        setTotalBlinksRequired(NumberUtils.generateRandomIntIntRange(2, 3));
    }

    public OnBlinkValidationListener getOnBlinkValidationListener() {
        if (onBlinkValidationListener == null) onBlinkValidationListener = OnBlinkValidationListener.DEFAULT;
        return onBlinkValidationListener;
    }

    public void setOnBlinkValidationListener(OnBlinkValidationListener onBlinkValidationListener) {
        this.onBlinkValidationListener = onBlinkValidationListener;
    }
}
