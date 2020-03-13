package co.yap.household.di.components

import co.yap.household.app.HouseHoldApplication
import co.yap.household.di.module.HouseHoldModule
import co.yap.household.di.module.activity.ActivityInjectorsModule
import co.yap.household.di.module.fragment.FragmentInjectorsModule
import co.yap.yapcore.dagger.di.components.CoreComponent
import dagger.BindsInstance
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
        FragmentInjectorsModule::class, HouseHoldModule::class],
            dependencies = [ CoreComponent::class]
)
interface HouseHoldComponent:AndroidInjector<DaggerApplication>
{
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//       // fun coreComponent(coreComponent: CoreComponent): Builder
//        fun build(): HouseHoldComponent
//    }
}