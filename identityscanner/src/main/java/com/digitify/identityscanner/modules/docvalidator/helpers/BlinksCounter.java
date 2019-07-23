package com.digitify.identityscanner.modules.docvalidator.helpers;

import com.digitify.identityscanner.core.detection.interfaces.IExpressionsAnalyser;

public class BlinksCounter extends BlinkDetector implements BlinkDetector.OnBlinkListener {

    interface OnBlinkCountListener {
        void onBlinkCount(int count);

        OnBlinkCountListener DEFAULT = count -> {

        };
    }

    private int blinksCount = 0;
    private OnBlinkCountListener onBlinkCountListener;

    public BlinksCounter(IExpressionsAnalyser analyser) {
        super(analyser);
        setOnBlinkListener(this);
    }

    @Override
    public void onBlink() {
        getOnBlinkCountListener().onBlinkCount(++blinksCount);
    }

    @Override
    protected void clear() {
        reset();
        super.clear();
    }

    public void reset() {
        blinksCount = 0;
    }

    private OnBlinkCountListener getOnBlinkCountListener() {
        if (onBlinkCountListener == null) onBlinkCountListener = OnBlinkCountListener.DEFAULT;
        return onBlinkCountListener;
    }

    public void setOnBlinkCountListener(OnBlinkCountListener onBlinkCountListener) {
        this.onBlinkCountListener = onBlinkCountListener;
    }
}
