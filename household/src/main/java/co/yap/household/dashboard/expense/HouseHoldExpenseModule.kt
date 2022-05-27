package co.yap.household.dashboard.expense

import dagger.Module

@Module
class HouseHoldExpenseModule{} /*: BaseFragmentModule<HouseHoldExpenseFragment>() {
    @Provides
    @ViewModelInjection
    fun provideHouseHoldExpenseVM(
        fragment: HouseHoldExpenseFragment,
        viewModelProvider: InjectionViewModelProvider<HouseHoldExpenseVM>
    ): HouseHoldExpenseVM = viewModelProvider.get(fragment, HouseHoldExpenseVM::class)

    @Provides
    @FragmentScope
    fun provideHouseHoldExpenseState(): IHouseHoldExpense.State = HouseHoldExpenseState()
}
*/