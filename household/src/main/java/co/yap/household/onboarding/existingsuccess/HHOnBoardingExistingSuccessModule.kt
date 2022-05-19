package co.yap.household.onboarding.existingsuccess

import co.yap.household.onboarding.existing.HHOnBoardingExistingState
import co.yap.household.onboarding.existing.IHHOnBoardingExisting
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingExistingSuccessModule{}/*:BaseFragmentModule<HHOnBoardingExistingSuccessFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingExistingSuccessVM(
        fragment: HHOnBoardingExistingSuccessFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingExistingSuccessVM>
    ): HHOnBoardingExistingSuccessVM = viewModelProvider.get(fragment,HHOnBoardingExistingSuccessVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingExistingSuccessState(): IHHOnBoardingExistingSuccess.State =
        HHOnBoardingExistingSuccessState()
}*/