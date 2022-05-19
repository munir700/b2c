package co.yap.household.dashboard.expense

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

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