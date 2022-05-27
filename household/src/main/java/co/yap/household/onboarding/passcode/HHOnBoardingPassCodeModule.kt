package co.yap.household.onboarding.passcode

import dagger.Module

@Module
class HHOnBoardingPassCodeModule/* : BaseFragmentModule<HHOnBoardingPassCodeFragment>() {
    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingWelcomeVM(
        fragment: HHOnBoardingPassCodeFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingPassCodeVM>
    ): HHOnBoardingPassCodeVM = viewModelProvider.get(fragment, HHOnBoardingPassCodeVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingSuccessState(): IHHOnBoardingPassCode.State =
        HHOnBoardingPassCodeState()
}*/