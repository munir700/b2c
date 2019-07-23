package com.digitify.identityscanner.activities;

import android.os.Bundle;

import com.digitify.identityscanner.helpers.PermissionsManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

public abstract class PermissionAwareActivity extends BaseActivity implements PermissionsManager.OnPermissionGrantedListener {

    private PermissionsManager permissionsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionsManager = new PermissionsManager(this, this, this);
    }

    public PermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    @Override
    public void onPermissionGranted(String permission) {

    }

    @Override
    public void onPermissionNotGranted(String permission) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
