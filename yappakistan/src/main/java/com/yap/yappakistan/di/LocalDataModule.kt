package com.yap.yappakistan.di

import android.content.Context
import com.yap.yappakistan.SharedPreferenceManager
import com.yap.yappakistan.SessionManager
import com.yap.yappakistan.networking.microservices.authentication.AuthApi
import com.yap.yappakistan.networking.microservices.customers.CustomersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun providesSessionManager(
        customersApi: CustomersApi,
        authApi: AuthApi,
        sharedPreferenceManager: SharedPreferenceManager
    ) = SessionManager(customersApi, authApi, sharedPreferenceManager)

//    @Singleton
//    @Provides
//    fun providesEventManager(
//        pkBuildConfigurations: PKBuildConfigurations
//    ) = EventManager(pkBuildConfigurations)

    @Singleton
    @Provides
    fun providesSharePrefManager(
        @ApplicationContext context: Context
    ) = SharedPreferenceManager(context)

//    @Singleton
//    @Provides
//    fun providesPkConfigurations(
//        retroNetwork: RetroNetwork,
//        sharedPreferenceManager: SharedPreferenceManager
//    ) = PKBuildConfigurations(retroNetwork, sharedPreferenceManager)
//
}