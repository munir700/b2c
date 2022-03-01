package com.yap.yappakistan.di

import com.yap.yappakistan.networking.microservices.authentication.AuthApi
import com.yap.yappakistan.networking.microservices.authentication.AuthRepository
import com.yap.yappakistan.networking.microservices.customers.CustomersApi
import com.yap.yappakistan.networking.microservices.customers.CustomersRepository
import com.yap.yappakistan.networking.microservices.messages.MessagesApi
import com.yap.yappakistan.networking.microservices.messages.MessagesRepository
import com.yap.yappakistan.utils.NameValidator
import com.yap.yappakistan.utils.PersonNameValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepository: AuthRepository): AuthApi

    @Binds
    @Singleton
    abstract fun provideMessagesRepository(messagesRepository: MessagesRepository): MessagesApi

    @Binds
    @Singleton
    abstract fun provideCustomersRepository(customersRepository: CustomersRepository): CustomersApi

    @Binds
    @Singleton
    abstract fun provideNameValidator(personNameValidator: PersonNameValidator): NameValidator

}