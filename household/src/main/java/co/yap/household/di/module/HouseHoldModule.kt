package co.yap.household.di.module

import android.app.Application
import android.content.Context
import co.yap.household.app.HouseHoldApplication
import co.yap.yapcore.dagger.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HouseHoldModule {

    @Provides
    @Singleton
    fun provideApplication(app: Application) = app

//    @Provides
//    @Singleton
//    fun provideApp(application: Application) = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(app: HouseHoldApplication): Context = app.applicationContext

}