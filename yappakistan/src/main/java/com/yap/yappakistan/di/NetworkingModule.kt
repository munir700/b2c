package com.yap.yappakistan.di

import com.yap.yappakistan.networking.apiclient.base.RetroNetwork
import com.yap.yappakistan.networking.microservices.authentication.AuthRetroService
import com.yap.yappakistan.networking.microservices.customers.CustomersRetroService
import com.yap.yappakistan.networking.microservices.messages.MessagesRetroService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {
    @Singleton
    @Provides
    fun provideAuthService(retroNetwork: RetroNetwork): AuthRetroService {
        return retroNetwork.createService(AuthRetroService::class.java)
    }

    @Singleton
    @Provides
    fun provideMessagesService(retroNetwork: RetroNetwork): MessagesRetroService {
        return retroNetwork.createService(MessagesRetroService::class.java)
    }

    @Singleton
    @Provides
    fun provideCustomersService(retroNetwork: RetroNetwork): CustomersRetroService {
        return retroNetwork.createService(CustomersRetroService::class.java)
    }

}