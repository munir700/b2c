package co.yap.household.di.module
import co.yap.yapcore.helpers.validation.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideValidator(): Validator {
        return Validator(null)
    }
}