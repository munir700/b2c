package co.yap.modules.subaccounts.account.card

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class SubAccountCardModule/* : BaseFragmentModule<SubAccountCardFragment>()*/ {

    /*@Provides
    @ViewModelInjection
    fun provideSubAccountCardVM(
        fragment: SubAccountCardFragment,
        viewModelProvider: InjectionViewModelProvider<SubAccountCardVM>
    ): SubAccountCardVM = viewModelProvider.get(fragment, SubAccountCardVM::class)

    @Provides
    @FragmentScope
    fun provideSubAccountCardState(): ISubAccountCard.State = SubAccountCardState()*/

    @Provides
    fun provideSubAccountCardAdapter() =
        SubAccountAdapter(
            ArrayList(),
            null
        )
}