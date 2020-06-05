package co.yap.modules.subaccounts.account.card

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides


@Module
class SubAccountCardModule : BaseFragmentModule<SubAccountCardFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSubAccountCardVM(
        fragment: SubAccountCardFragment,
        viewModelProvider: InjectionViewModelProvider<SubAccountCardVM>
    ): SubAccountCardVM = viewModelProvider.get(fragment, SubAccountCardVM::class)

    @Provides
    @FragmentScope
    fun provideSubAccountCardState(): ISubAccountCard.State = SubAccountCardState()

    @Provides
    fun provideSubAccountCardAdapter(fragment: SubAccountCardFragment) =
        SubAccountAdapter(
            ArrayList(),
            null
        )
}