package com.digitify.identityscanner.modules.docvalidator.helpers;

import com.digitify.identityscanner.core.detection.analysers.EyeStateAnalyser;
import com.digitify.identityscanner.core.detection.interfaces.IExpressionsAnalyser;

public class BlinkDetector extends EyeStateAnalyser implements EyeStateAnalyser.EyeStateChangeListener {

    public interface OnBlinkListener {
        void onBlink();

        OnBlinkListener DEFAULT = () -> {
        };
    }

    private long BLINK_INTERVAL = 1000;
    private OnBlinkListener onBlinkListener;
    private long blinkStartTime = 0;

    public BlinkDetector(IExpressionsAnalyser analyser) {
        super(analyser);
        setEyeStateChangeListener(this);
    }

    @Override
    public void onEyeOpened() {
        long interval = System.currentTimeMillis() - blinkStartTime;
        if (interval <= BLINK_INTERVAL) {
            getOnBlinkListener().onBlink();
        }
    }

    @Override
    public void onEyeClosed() {
        blinkStartTime = System.currentTimeMillis();
    }

    public OnBlinkListener getOnBlinkListener() {
        if (onBlinkListener == null) onBlinkListener = OnBlinkListener.DEFAULT;
        return onBlinkListener;
    }

    public void setOnBlinkListener(OnBlinkListener onBlinkListener) {
        this.onBlinkListener = onBlinkListener;
    }
}
