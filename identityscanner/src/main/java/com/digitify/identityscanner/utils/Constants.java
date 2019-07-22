package com.digitify.identityscanner.utils;

import android.os.Environment;

import java.io.File;

public class Constants {

    public static boolean SHOW_DEBUG_VIEWS = false;
    public static String KYC_SDK_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "IdentityScanner";
    public static String KYC_SDK__VIDEO_PATH = KYC_SDK_PATH + File.separator + "Videos";
}
