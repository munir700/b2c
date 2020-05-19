package co.yap.modules.di.components

import co.yap.modules.di.module.NetworkModule
import co.yap.modules.di.module.YapModule
import co.yap.modules.di.module.activity.ActivityInjectorsModule
import co.yap.modules.di.module.fragment.FragmentInjectorsModule
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.dagger.di.components.CoreComponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

//@FeatureScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class, YapModule::class, NetworkModule::class],
    dependencies = [CoreComponent::class]
)
interface YapComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        // @BindsInstance
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): YapComponent
    }
}