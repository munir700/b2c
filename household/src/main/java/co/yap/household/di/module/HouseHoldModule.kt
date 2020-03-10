package co.yap.household.di.module

import android.app.Application
import android.content.Context
import co.yap.app.YAPApplication
import co.yap.household.app.HouseHoldApplication
import co.yap.yapcore.dagger.di.qualifiers.ApplicationContext
import co.yap.yapcore.helpers.GsonProvider
import com.google.gson.Gson

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HouseHoldModule {

    @Provides
    @Singleton
    fun provideApplication(app: HouseHoldApplication) = app

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(app: HouseHoldApplication): Context = app.applicationContext

}