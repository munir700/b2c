package com.digitify.identityscanner.utils;

import android.text.TextUtils;

import java.io.File;

public class FileUtils {

    public static boolean isValidFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) return false;
        return new File(filePath).exists();
    }
}
