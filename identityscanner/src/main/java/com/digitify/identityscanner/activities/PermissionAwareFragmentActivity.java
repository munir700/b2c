package com.digitify.identityscanner.activities;

import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class PermissionAwareFragmentActivity extends PermissionAwareActivity {

    protected void showFragment(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment).commit();
    }

    protected void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(getFragmentHolderId(), fragment).commit();
    }

    protected int getFragmentHolderId() {
        return getFragmentHolder().getId();
    }

    protected abstract FrameLayout getFragmentHolder();

}
