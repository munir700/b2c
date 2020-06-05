package co.yap.app.di.component

import co.yap.app.AAPApplication
import co.yap.app.di.module.AppModule
import co.yap.app.di.module.NetworkModule
import co.yap.app.di.module.activity.ActivityInjectorsModule
import co.yap.app.di.module.fragment.FragmentInjectorsModule
import co.yap.yapcore.dagger.di.qualifiers.AppScope
import co.yap.household.di.components.HouseHoldComponent
import co.yap.modules.di.components.YapComponent
import co.yap.yapcore.dagger.di.components.CoreComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        FragmentInjectorsModule::class,
        ActivityInjectorsModule::class,
        AppModule::class, NetworkModule::class],
    dependencies = [CoreComponent::class, HouseHoldComponent::class, YapComponent::class]
)
interface AppComponent : AndroidInjector<AAPApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AAPApplication): Builder
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun houseHoldComponent(houseHoldComponent: HouseHoldComponent): Builder
        fun yapComponent(yapComponent: YapComponent): Builder
        fun build(): AppComponent
    }
}