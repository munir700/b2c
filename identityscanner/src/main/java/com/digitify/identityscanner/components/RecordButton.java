package com.digitify.identityscanner.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.digitify.identityscanner.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class RecordButton extends LinearLayout implements View.OnClickListener {
    public interface RecordingListener {
        void onRecordingAnimationStart();

        void onRecordingAnimationComplete();

        void onClickRecordButton();

        RecordingListener DEFAULT = new RecordingListener() {
            @Override
            public void onRecordingAnimationStart() {

            }

            @Override
            public void onRecordingAnimationComplete() {

            }

            @Override
            public void onClickRecordButton() {

            }
        };
    }

    public enum ButtonState {
        START_RECORDING, STOP_RECORDING
    }

    private long PROGRESS_INTERVAL_CALLBACK = 1000;
    private CircularProgressBar progressbar;
    private CircleImageView icon;
    private boolean recording = false;
    private long maxRecordTime = 60 * 60 * 1000; // 1 hour
    private RecordingListener recordingListener;
    private ButtonState state = ButtonState.START_RECORDING;
    private CountDownTimer timer;

    public RecordButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordButton, 0, 0);
        typedArray.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.record_button, null, false);
        progressbar = view.findViewById(R.id.record_progress);
        icon = view.findViewById(R.id.record_icon);
        addView(view);

        icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getRecordingListener().onClickRecordButton();
    }

    public void startRecordAnimation() {
        setRecording(true);
        setButtonState(ButtonState.STOP_RECORDING);
        getRecordingListener().onRecordingAnimationStart();
        // Start the timer to show progress
        setProgress(0);
        startTimer();
    }

    public void stopRecordAnimation() {
        setRecording(false);
        setButtonState(ButtonState.START_RECORDING);
        // Reset the timer and progress
        stopTimer();
        setProgress(0);
    }

    public void setProgress(float progress) {
        if (progressbar != null) {
            progressbar.setProgressWithAnimation(progress);
        }
    }

    public boolean isRecording() {
        return recording;
    }

    private void setRecording(boolean recording) {
        this.recording = recording;
    }

    public long getMaxRecordTime() {
        return maxRecordTime;
    }

    public void setMaxRecordTime(long maxRecordTime) {
        this.maxRecordTime = maxRecordTime;
        if (progressbar != null) {
            progressbar.setProgressMax(maxRecordTime);
        }
    }

    public void setRecordingListener(RecordingListener recordingListener) {
        this.recordingListener = recordingListener;
    }

    public RecordingListener getRecordingListener() {
        if (recordingListener == null) recordingListener = RecordingListener.DEFAULT;
        return recordingListener;
    }

    public void setButtonState(ButtonState state) {
        this.state = state;
        if (icon != null) {
            int color = state == ButtonState.START_RECORDING ? R.color.red : R.color.transparent;
            icon.setColor(ContextCompat.getColor(getContext(), color));
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(getMaxRecordTime(), PROGRESS_INTERVAL_CALLBACK) {

            public void onTick(long millisUntilFinished) {
                long timeElapsed = (getMaxRecordTime() - (millisUntilFinished)) + PROGRESS_INTERVAL_CALLBACK;
                setProgress(timeElapsed);
            }

            public void onFinish() {
                timer = null;
                setProgress(getMaxRecordTime());
                stopRecordAnimation();
                getRecordingListener().onRecordingAnimationComplete();
            }
        }.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

}
