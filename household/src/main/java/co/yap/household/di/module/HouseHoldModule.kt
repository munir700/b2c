package co.yap.household.di.module

import android.app.Application
import android.content.Context
import co.yap.household.app.HouseHoldApplication
import co.yap.yapcore.dagger.di.qualifiers.ApplicationContext
import co.yap.yapcore.dagger.di.qualifiers.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class HouseHoldModule {

    @Provides
    @FeatureScope
    fun provideApplication(app: Application) = app

//    @Provides
//    @Singleton
//    fun provideApp(application: Application) = application

    @Provides
    @FeatureScope
    @ApplicationContext
    fun provideApplicationContext(app: HouseHoldApplication): Context = app.applicationContext

}