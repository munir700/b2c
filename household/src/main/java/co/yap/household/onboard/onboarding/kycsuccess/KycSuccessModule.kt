package co.yap.household.onboard.onboarding.kycsuccess

import co.yap.household.dashboard2.home.HouseholdHomeState
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class KycSuccessModule : BaseFragmentModule<KycSuccessFragment>() {

    @Provides
    @ViewModelInjection
    fun provideKycSuccessVM(
        fragment: KycSuccessFragment,
        viewModelProvider: InjectionViewModelProvider<KycSuccessVM>
    ) = viewModelProvider.get(fragment, KycSuccessVM::class)

    @Provides
//    @ViewModelInjection
    fun provideHouseholdHomeState(): IKycSuccess.State {
       return KycSuccessState()
    }
}