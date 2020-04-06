package co.yap.yapcore.dagger.di.module

import android.app.Application
import android.content.Context
import co.yap.app.YAPApplication
import co.yap.yapcore.dagger.di.qualifiers.ApplicationContext
import co.yap.yapcore.helpers.GsonProvider
import co.yap.yapcore.helpers.SharedPreferenceManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    fun provideApplication(app: Application): Application = app

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(app: YAPApplication): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonProvider.makeGson()
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceManager(@ApplicationContext context: Context) =
        SharedPreferenceManager.getInstance(context)
}