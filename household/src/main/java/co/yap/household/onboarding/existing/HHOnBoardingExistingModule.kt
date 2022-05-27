package co.yap.household.onboarding.existing

import dagger.Module

@Module
class HHOnBoardingExistingModule{}
/* : BaseFragmentModule<HHOnBoardingExistingFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingExistingVM(
        fragment: HHOnBoardingExistingFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingExistingVM>
    ): HHOnBoardingExistingVM = viewModelProvider.get(fragment, HHOnBoardingExistingVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingExistingState(): IHHOnBoardingExisting.State =
        HHOnBoardingExistingState()
}*/