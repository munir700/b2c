package co.yap.modules.di.module

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import co.yap.yapcore.dagger.di.qualifiers.ApplicationContext
import co.yap.yapcore.dagger.di.qualifiers.ChildFragmentManager
import co.yap.yapcore.dagger.di.qualifiers.FeatureScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class YapModule {

//    @Provides
//    @FeatureScope
//    fun provideApplication(app: Application) = app
//
////    @Provides
////    @Singleton
////    fun provideApp(application: Application) = application
//
//    @Provides
//    @FeatureScope
//    @ApplicationContext
//    fun provideApplicationContext(app: Application): Context = app.applicationContext
}