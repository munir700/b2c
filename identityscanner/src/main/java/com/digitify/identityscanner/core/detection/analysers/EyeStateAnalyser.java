package com.digitify.identityscanner.core.detection.analysers;

import com.digitify.identityscanner.core.detection.interfaces.Analyser;
import com.digitify.identityscanner.core.detection.interfaces.IExpressionsAnalyser;
import com.digitify.identityscanner.core.detection.models.Face;

import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

/**
 * A class that observes state of eyes from continuous frames and send callbacks of detected eye state.
 */
public class EyeStateAnalyser implements Analyser<Face> {
    protected static String TAG = EyeStateAnalyser.class.getSimpleName();

    private int SAME_EYE_STATE_THRESHOLD = 2;
    private int FRAME_CACHE_SIZE = SAME_EYE_STATE_THRESHOLD + 1;

    private int STATE_EYE_OPEN = 0;
    private int STATE_EYE_CLOSE = 1;
    private int STATE_EYE_UNPREDICTED = -1;

    private ObservableArrayList<Integer> eyeStateBuffer;
    private IExpressionsAnalyser expAnalyser;
    private EyeStateChangeListener eyeStateChangeListener;

    // To hold value which callback was sent last time.
    private int previousCallBack = STATE_EYE_UNPREDICTED;

    public EyeStateAnalyser(IExpressionsAnalyser analyser) {
        this.expAnalyser = analyser;
        observeEyeData();
    }

    private void observeEyeData() {
        getEyeStateBuffer().addOnListChangedCallback(listChangedCallback);
    }

    private ObservableList.OnListChangedCallback listChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<Integer>>() {
        @Override
        public void onChanged(ObservableList<Integer> list) {

        }

        @Override
        public void onItemRangeChanged(ObservableList<Integer> sender, int positionStart, int itemCount) {
        }

        @Override
        public void onItemRangeInserted(ObservableList<Integer> list, int positionStart, int itemCount) {
            if (list.size() == FRAME_CACHE_SIZE) {
                // we have enough data to analyse
                analyseEyeState(list);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList<Integer> sender, int fromPosition, int toPosition, int itemCount) {
        }

        @Override
        public void onItemRangeRemoved(ObservableList<Integer> list, int positionStart, int itemCount) {
            if (list.size() == FRAME_CACHE_SIZE) {
                // we have enough data to analyse
                analyseEyeState(list);
            }
        }
    };

    /**
     * A function that receives frames of Face from camera/video and analyses eye's open/close state
     * @param face
     */
    @Override
    public void analyse(Face face) {
        if (face != null && face.isValid()) {
            cacheEyeState(getExpressionsAnalyser().isRightEyeOpen(face) ? STATE_EYE_OPEN : STATE_EYE_CLOSE);
        }
    }

    /**
     * analyse if we got continuous @Link
     *
     * @param eyeStates
     */
    private void analyseEyeState(List<Integer> eyeStates) {
        int currentState = eyeStates.get(0);

        // Send First callback if it was never sent
        if (previousCallBack == STATE_EYE_UNPREDICTED) {
            previousCallBack = currentState;
            publishState(currentState);
        }

        int size = eyeStates.size();
        int sameStateCount = 0;
        for (int i = 0; i < size; i++) {
            int prevState = eyeStates.get(i);
            if (currentState == prevState) {
                sameStateCount++;
            } else {
                // Change detected
                if (sameStateCount >= SAME_EYE_STATE_THRESHOLD) {

                    // real change detected. check if we need to send callback
                    if (currentState != previousCallBack) {
                        previousCallBack = currentState;
                        publishState(currentState);
                    }
                    break;
                }
                sameStateCount = 1;
            }
            currentState = prevState;
        }
    }

    private void publishState(int state) {
        if (state == STATE_EYE_OPEN) {
            getEyeStateChangeListener().onEyeOpened();
        } else {
            getEyeStateChangeListener().onEyeClosed();
        }
    }

    private void cacheEyeState(int state) {
        // New data on top
        getEyeStateBuffer().add(0, state);
        if (getEyeStateBuffer().size() > FRAME_CACHE_SIZE) {
            getEyeStateBuffer().remove(getEyeStateBuffer().size() - 1);
        }
    }

    protected void clear() {
        getEyeStateBuffer().clear();
    }

    public void destroy() {
        getEyeStateBuffer().removeOnListChangedCallback(listChangedCallback);
        clear();
    }

    private ObservableArrayList<Integer> getEyeStateBuffer() {
        if (eyeStateBuffer == null) eyeStateBuffer = new ObservableArrayList<>();
        return eyeStateBuffer;
    }

    public IExpressionsAnalyser getExpressionsAnalyser() {
        return expAnalyser;
    }

    public EyeStateChangeListener getEyeStateChangeListener() {
        if (eyeStateChangeListener == null) eyeStateChangeListener = EyeStateChangeListener.DEFAULT;
        return eyeStateChangeListener;
    }

    public void setEyeStateChangeListener(EyeStateChangeListener eyeStateChangeListener) {
        this.eyeStateChangeListener = eyeStateChangeListener;
    }

    public interface EyeStateChangeListener {
        void onEyeOpened();

        void onEyeClosed();

        EyeStateChangeListener DEFAULT = new EyeStateChangeListener() {
            @Override
            public void onEyeOpened() {

            }

            @Override
            public void onEyeClosed() {

            }
        };
    }
}
