package co.yap.yapcore.dagger.di.module.activity

import co.yap.yapcore.dagger.base.MvvmNavHostModule
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterModule
import co.yap.yapcore.dagger.di.module.fragment.FragmentInjectorsModule
import co.yap.yapcore.dagger.di.qualifiers.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ActivityInjectorsModule {

   /* @ActivityScope
    @ContributesAndroidInjector(modules = [NavHostPresenterModule::class, MvvmNavHostModule::class, FragmentInjectorsModule::class])
    abstract fun NavHostPresenterActivityInjector(): NavHostPresenterActivity*/
}
