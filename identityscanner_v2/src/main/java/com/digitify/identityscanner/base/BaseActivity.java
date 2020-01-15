package com.digitify.identityscanner.base;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.digitify.identityscanner.interfaces.IBase;

public abstract class BaseActivity extends AppCompatActivity implements IBase.View, LifecycleOwner {

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
