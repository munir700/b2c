package co.yap.modules.subaccounts.account.card

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped


@Module
@InstallIn(FragmentComponent::class)
class SubAccountCardModule{

    @Provides
    @FragmentScoped
    fun provideSubAccountCardAdapter() =
        SubAccountAdapter(
            ArrayList(),
            null
        )
}