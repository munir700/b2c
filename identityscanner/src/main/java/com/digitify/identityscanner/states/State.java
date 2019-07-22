package com.digitify.identityscanner.states;

import com.digitify.identityscanner.utils.Constants;

import androidx.databinding.BaseObservable;

public abstract class State extends BaseObservable {
    boolean debugging = Constants.SHOW_DEBUG_VIEWS;

    public abstract void reset();

    public boolean isDebugging() {
        return debugging;
    }
}
