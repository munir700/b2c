package com.digitify.identityscanner.modules.docvalidator.helpers;

import android.content.Context;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;

import com.digitify.identityscanner.utils.NumberUtils;

import org.jetbrains.annotations.Contract;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;

public class SpeechValidator implements SpeechToTextManager.OnSpeechToTextConvertListener {

    public interface SpeechValidationListener {
        void onSpeechValidated();

        void onSpeechValidationError(int error);

        void onSpeechValidationStart();

        SpeechValidationListener DEFAULT = new SpeechValidationListener() {
            @Override
            public void onSpeechValidated() {

            }

            @Override
            public void onSpeechValidationError(int error) {

            }

            @Override
            public void onSpeechValidationStart() {

            }
        };
    }

    private SpeechToTextManager manager;
    private String speechText;
    private SpeechValidationListener speechValidationListener;
    boolean speaking = false;

    public SpeechValidator(Context context, LifecycleOwner lifecycleOwner) {
        manager = new SpeechToTextManager(context, lifecycleOwner);
        manager.setOnSpeechToTextConvertListener(this);
    }

    public void reset() {
        stopValidation();
        setSpeechText(generateRandomText());
    }

    public void destroy() {
        reset();
        getSpeechManager().destroy();
    }

    private String generateRandomText() {
        int n1 = NumberUtils.generateRandomIntIntRange(1, 9);
        int n2 = NumberUtils.generateRandomIntIntRange(1, 9);
        int n3 = NumberUtils.generateRandomIntIntRange(1, 9);
        return String.valueOf(n1) + " " + n2 + " " + n3;
    }

    public String getSpeechText() {
        if (TextUtils.isEmpty(speechText)) speechText = generateRandomText();
        return speechText;
    }

    private void setSpeechText(String randomText) {
        this.speechText = randomText;
    }

    public void startValidation() {
        getSpeechValidationListener().onSpeechValidationStart();
        getSpeechManager().startListening();
    }

    public void stopValidation() {
        if (isSpeaking()) {
            getSpeechManager().stopListening();
        }
    }

    public SpeechToTextManager getSpeechManager() {
        return manager;
    }

    @Override
    public void onResults(List<String> matches) {
        setSpeaking(false);
        String speech = getSpeechText().replaceAll("\\s+", "").trim();
        boolean matched = false;
        for (String match : matches) {
            if (!TextUtils.isEmpty(match) &&
                    match.replaceAll("\\s", "").trim().equalsIgnoreCase(speech)) {
                matched = true;
                break;
            }
        }
        if (matched) {
            getSpeechValidationListener().onSpeechValidated();
        } else {
            onError(SpeechRecognizer.ERROR_NO_MATCH);
        }
    }

    @Override
    public void onError(int error) {
        setSpeaking(false);
        getSpeechValidationListener().onSpeechValidationError(error);
    }

    @Override
    public void onSpeechStart() {
        setSpeaking(true);
    }

    @Override
    public void onSpeechEnd() {
        setSpeaking(false);
    }

    @Contract(pure = true)
    public String getErrorText(int errorCode) {
        return getSpeechManager().getErrorText(errorCode);
    }

    public SpeechValidationListener getSpeechValidationListener() {
        if (speechValidationListener == null)
            speechValidationListener = SpeechValidationListener.DEFAULT;
        return speechValidationListener;
    }

    public void setSpeechValidationListener(SpeechValidationListener speechValidationListener) {
        this.speechValidationListener = speechValidationListener;
    }

    public boolean isSpeaking() {
        return speaking;
    }

    private void setSpeaking(boolean speaking) {
        this.speaking = speaking;
    }
}
