package com.yap.yappakistan.di

import com.yap.core.utils.SharedPreferenceManager
import com.yap.yappakistan.SessionManager
import com.yap.yappakistan.configs.PKBuildConfigurations
import com.yap.yappakistan.networking.apiclient.base.RetroNetwork
import com.yap.yappakistan.networking.microservices.authentication.AuthApi
import com.yap.yappakistan.networking.microservices.customers.CustomersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
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
    fun providesPkConfigurations(
        retroNetwork: RetroNetwork,
        sharedPreferenceManager: SharedPreferenceManager
    ) = PKBuildConfigurations(retroNetwork, sharedPreferenceManager)

//    @Singleton
//    @Provides
//    fun provideDeeplinkNavigator(
//        @ApplicationContext context: Context
//    ): IDeeplinkNavigator {
//        return DeeplinkHandler(context)
//    }
//
//    @Singleton
//    @Provides
//    fun provideNotificationPayloadImpl(
//        deeplinkHandler: IDeeplinkNavigator
//    ): DeeplinkNavigatorPayload {
//        return NotificationPayloadImpl(deeplinkHandler)
//    }
}