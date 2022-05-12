package co.yap.household.di.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HouseHoldModule {

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
//    fun provideApplicationContext(app: HouseHoldApplication): Context = app.applicationContext
//
//    @Provides
//    @Singleton
//    fun provideSharedPreferenceManager(@ApplicationContext context: Context) =
//        SharedPreferenceManager.getInstance(context)

}