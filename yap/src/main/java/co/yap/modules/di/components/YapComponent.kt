package co.yap.modules.di.components


import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@FeatureScope
@Module(includes = [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class])
@InstallIn(SingletonComponent::class)
interface YapComponent
/*
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
}*/
