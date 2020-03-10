package co.yap.app.di.component

import co.yap.app.AAPApplication
import co.yap.app.di.module.AppModule
import co.yap.yapcore.dagger.di.components.CoreComponent
import co.yap.yapcore.dagger.di.qualifiers.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class],
    dependencies = [CoreComponent::class]
)
interface AppComponent : AndroidInjector<AAPApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AAPApplication): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun build(): AppComponent
    }
}