package com.yap.yappakistan.di

import com.yap.core.biometric.BiometricUtils
import com.yap.yappakistan.SharedPreferenceManager
import com.yap.yappakistan.ui.auth.AccountRouteManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
class UtilityModule {

    @Provides
    fun provideBiometricUtils(): BiometricUtils {
        return BiometricUtils()
    }

    @Provides
    fun provideAccountRouteManager(sharedPreferenceManager: SharedPreferenceManager): AccountRouteManager {
        return AccountRouteManager(sharedPreferenceManager)
    }
}