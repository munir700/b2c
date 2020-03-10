package co.yap.yapcore.dagger.di.components

import co.yap.app.YAPApplication
import co.yap.yapcore.dagger.di.module.CoreModule
import co.yap.yapcore.dagger.di.module.activity.ActivityInjectorsModule
import co.yap.yapcore.dagger.di.module.fragment.FragmentInjectorsModule
import co.yap.yapcore.dagger.di.qualifiers.CoreScope
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
        FragmentInjectorsModule::class, CoreModule::class]
)
interface CoreComponent:AndroidInjector<YAPApplication> {


}