package co.yap.app.di.module

import android.app.Application
import android.content.Context
import co.yap.app.AAPApplication
import co.yap.yapcore.dagger.di.qualifiers.AppScope
import co.yap.yapcore.dagger.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideApplication(app: AAPApplication): Application = app

    @Provides
    @AppScope
    @ApplicationContext
    fun provideApplicationContext(app: AAPApplication): Context = app.applicationContext


}