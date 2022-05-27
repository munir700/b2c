package co.yap.household.onboarding.cardselection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HHOnBoardingCardSelectionModule /*: BaseFragmentModule<HHOnBoardingCardSelectionFragment>()*/ {

   /* @Provides
    @ViewModelInjection
    fun provideHHOnBoardingCardSelectionVM(
        fragment: HHOnBoardingCardSelectionFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingCardSelectionVM>
    ): HHOnBoardingCardSelectionVM =
        viewModelProvider.get(fragment, HHOnBoardingCardSelectionVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingCardSelectionState(): IHHOnBoardingCardSelection.State =
        HHOnBoardingCardSelectionState()*/

    @Provides
    fun provideCardSelectionAdapter() =
        CardSelectionAdapter(
            mutableListOf(),
            null
        )
}
