package co.yap.app.di.component

/*
import co.yap.yapcore.dagger.di.components.CoreComponent
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        FragmentInjectorsModule::class,
        ActivityInjectorsModule::class,
        AppModule::class*, NetworkModule::class]
   ,dependencies = [CoreComponent::class, HouseHoldComponent::class, YapComponent::class]
)
@Module(includes = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class])
@InstallIn(SingletonComponent::class)
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
}*/
