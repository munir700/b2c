package com.yap.core.biometric

import android.content.Context
import androidx.biometric.BiometricManager

class BiometricUtils {
    fun hasBioMetricFeature(
        context: Context
    ): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }
}