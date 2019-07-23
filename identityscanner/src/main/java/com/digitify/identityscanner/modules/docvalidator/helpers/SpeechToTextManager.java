package com.digitify.identityscanner.modules.docvalidator.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * A Class that Listens for user's voice and converts it to text.
 * @return SpeechRecognizer
 */
public class SpeechToTextManager implements LifecycleObserver {
    private static String TAG = SpeechToTextManager.class.getSimpleName();

    private Context context;
    private SpeechRecognizer mSpeechRecognizer;
    private OnSpeechToTextConvertListener onSpeechToTextConvertListener;

    public SpeechToTextManager(Context context, LifecycleOwner lifecycleOwner) {
        this.context = context;
        attachLifeCycleOwner(lifecycleOwner);
    }

    public SpeechToTextManager(Context context) {
        this.context = context;
    }

    public void attachLifeCycleOwner(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        mSpeechRecognizer.setRecognitionListener(recognitionListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
        }
    }

    private Intent getListeningIntent() {
        Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getContext().getPackageName());
        return mSpeechRecognizerIntent;
    }

    /**
     * Start listening to user's voice
     * @return SpeechRecognizer
     */
    public void startListening() {
        getSpeechRecognizer().startListening(getListeningIntent());
    }

    /**
     * Stop listening to user's voice
     * @return SpeechRecognizer
     */
    public void stopListening() {
        getSpeechRecognizer().stopListening();
    }

    /**
     * Cancel listening to user's voice
     * @return SpeechRecognizer
     */
    public void cancelListening() {
        getSpeechRecognizer().cancel();
    }

    /**
     * This function provides the core SpeechRecognizer to perform low level operations.
     * @return SpeechRecognizer
     */
    public SpeechRecognizer getSpeechRecognizer() {
        if (mSpeechRecognizer == null) init();
        return mSpeechRecognizer;
    }

    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        @Override
        public void onBeginningOfSpeech() {
            getOnSpeechToTextConvertListener().onSpeechStart();
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
            getOnSpeechToTextConvertListener().onSpeechEnd();
        }

        @Override
        public void onError(int error) {
            getOnSpeechToTextConvertListener().onError(error);

        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            getOnSpeechToTextConvertListener().onResults(matches);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };

    @Contract(pure = true)
    private Context getContext() {
        return context;
    }

    public OnSpeechToTextConvertListener getOnSpeechToTextConvertListener() {
        if (onSpeechToTextConvertListener == null) onSpeechToTextConvertListener = OnSpeechToTextConvertListener.DEFAULT;
        return onSpeechToTextConvertListener;
    }

    public void setOnSpeechToTextConvertListener(OnSpeechToTextConvertListener onSpeechToTextConvertListener) {
        this.onSpeechToTextConvertListener = onSpeechToTextConvertListener;
    }

    @Contract(pure = true)
    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    interface OnSpeechToTextConvertListener {
        void onResults(List<String> matches);
        void onError(int error);
        void onSpeechStart();
        void onSpeechEnd();

        OnSpeechToTextConvertListener DEFAULT = new OnSpeechToTextConvertListener() {
            @Override
            public void onResults(List<String> matches) {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onSpeechStart() {

            }

            @Override
            public void onSpeechEnd() {

            }
        };
    }
}
