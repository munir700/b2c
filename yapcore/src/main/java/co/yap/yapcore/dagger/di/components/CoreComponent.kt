package co.yap.yapcore.dagger.di.components

import co.yap.yapcore.dagger.di.module.CoreModule
import co.yap.yapcore.dagger.di.module.NetworkModule
import co.yap.yapcore.dagger.di.module.activity.ActivityInjectorsModule
import co.yap.yapcore.dagger.di.module.fragment.FragmentInjectorsModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

//@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class, CoreModule::class, NetworkModule::class]
)
interface CoreComponent:AndroidInjector<DaggerApplication> {


}