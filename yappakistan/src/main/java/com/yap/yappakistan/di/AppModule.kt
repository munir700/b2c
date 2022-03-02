package com.yap.yappakistan.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }
}