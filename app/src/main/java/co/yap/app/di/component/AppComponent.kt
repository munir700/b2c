package co.yap.app.di.component

import co.yap.app.AAPApplication
import co.yap.app.di.module.AppModule
import co.yap.app.di.module.activity.ActivityInjectorsModule
import co.yap.app.di.module.fragment.FragmentInjectorsModule
import co.yap.household.di.components.HouseHoldComponent
import co.yap.yapcore.dagger.di.components.CoreComponent
import co.yap.yapcore.dagger.di.qualifiers.AppScope
import co.yap.yapcore.dagger.di.qualifiers.FeatureScope
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
        AppModule::class],
    dependencies = [HouseHoldComponent::class , CoreComponent::class]
)
interface AppComponent : AndroidInjector<AAPApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AAPApplication): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder
        fun houseHoldComponent(houseHoldComponent: HouseHoldComponent): Builder

        fun build(): AppComponent
    }
}