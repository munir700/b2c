package co.yap.app.di.module.activity

import dagger.Module

@Module(
    includes = [co.yap.household.di.module.activity.ActivityInjectorsModule::class,
        co.yap.yapcore.dagger.di.module.activity.ActivityInjectorsModule::class]
)
abstract class ActivityInjectorsModule {
}