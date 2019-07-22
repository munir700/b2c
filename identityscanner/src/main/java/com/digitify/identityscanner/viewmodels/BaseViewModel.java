package com.digitify.identityscanner.viewmodels;

import com.digitify.identityscanner.interfaces.ILIfeCycle;
import com.digitify.identityscanner.utils.Constants;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel implements ILIfeCycle {

    boolean debugging = Constants.SHOW_DEBUG_VIEWS;

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public boolean isDebugging() {
        return debugging;
    }
}
