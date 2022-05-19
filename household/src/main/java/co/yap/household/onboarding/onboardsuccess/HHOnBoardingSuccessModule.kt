package co.yap.household.onboarding.onboardsuccess

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingSuccessModule {}/*: BaseFragmentModule<HHOnBoardingSuccessFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnboardingSuccessVM(
        fragment: HHOnBoardingSuccessFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingSuccessVM>
    ): HHOnBoardingSuccessVM = viewModelProvider.get(fragment, HHOnBoardingSuccessVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingSuccessState(): IHHOnBoardingSuccess.State =
        HHOnBoardingSuccessState()
}*/