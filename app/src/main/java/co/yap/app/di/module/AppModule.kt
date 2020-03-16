package co.yap.app.di.module

import android.app.Application
import android.content.Context
import co.yap.app.AAPApplication
import co.yap.yapcore.dagger.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: AAPApplication): Application = app

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(app: AAPApplication): Context = app.applicationContext


}