package co.yap.household.onboard.onboarding.newuser

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHNewUserModule : BaseFragmentModule<HHNewUserFragment>() {
    @Provides
    @ViewModelInjection
    fun provideSetPinVM(
        fragment: HHNewUserFragment,
        viewModelProvider: InjectionViewModelProvider<NewHouseholdUserViewModel>
    ): NewHouseholdUserViewModel = viewModelProvider.get(fragment, NewHouseholdUserViewModel::class)

    @Provides
    @FragmentScope
    fun provideHHSetPinState(): INewHouseHoldUser.State = NewHouseHoldUserState()
}