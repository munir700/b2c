package co.yap.household.onboarding.existing

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

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