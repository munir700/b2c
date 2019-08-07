package com.digitify.identityscanner.viewmodels;

import android.app.Application;
import android.content.Context;

import co.yap.translation.Translator;
import com.digitify.identityscanner.interfaces.ILIfeCycle;
import com.digitify.identityscanner.utils.Constants;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class BaseAndroidViewModel extends AndroidViewModel implements ILIfeCycle, LifecycleObserver {

    boolean debugging = Constants.SHOW_DEBUG_VIEWS;

    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onStop() {

    }

    public void registerLifecycleOwner(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
    }

    protected Context getContext() {
        return getApplication().getApplicationContext();
    }

    protected String getString(int resourceId) {
        return getApplication().getResources().getString(resourceId);
    }

    protected String getString(String resourceId) {return  Translator.INSTANCE.getString(getContext(), resourceId); }
}
