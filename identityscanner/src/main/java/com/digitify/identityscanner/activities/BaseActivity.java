package com.digitify.identityscanner.activities;

import android.widget.Toast;

import com.digitify.identityscanner.interfaces.IBase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

public abstract class BaseActivity extends AppCompatActivity implements IBase.View, LifecycleOwner {

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
