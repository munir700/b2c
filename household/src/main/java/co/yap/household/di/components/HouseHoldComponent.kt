package co.yap.household.di.components

/*import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@FeatureScope
@Module(
    includes = [AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class]
)
@InstallIn(SingletonComponent::class)
interface HouseHoldComponent
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class, HouseHoldModule::class, NetworkModule::class],
    dependencies = [CoreComponent::class]
)
interface HouseHoldComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        // @BindsInstance
       // fun application(application: AAPApplication): Builder
        //@BindsInstance
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): HouseHoldComponent
    }
}*/
