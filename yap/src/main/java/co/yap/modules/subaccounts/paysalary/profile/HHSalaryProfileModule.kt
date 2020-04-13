package co.yap.modules.subaccounts.paysalary.profile

import co.yap.R
import co.yap.translation.Strings
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides


@Module
class HHSalaryProfileModule : BaseFragmentModule<HHSalaryProfileFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHSalaryProfileVM(
        fragment: HHSalaryProfileFragment,
        viewModelProvider: InjectionViewModelProvider<HHSalaryProfileVM>
    ): HHSalaryProfileVM = viewModelProvider.get(fragment, HHSalaryProfileVM::class)

    @Provides
    @FragmentScope
    fun provideHHSalaryProfileState(): IHHSalaryProfile.State = HHSalaryProfileState()

    @Provides
    fun provideHHSalaryProfileTransfersAdapter() =
        HHSalaryProfileTransfersAdapter(
            ArrayList(),
            null
        )

    @Provides
    fun provideHHPaySalaryAdapter(fragment: HHSalaryProfileFragment) =
        HHSalaryProfileFragment.Adapter(
            getNoTransactionsData(),
            null
        )

    private fun getNoTransactionsData(): ArrayList<PaySalaryModel> {
        return ArrayList<PaySalaryModel>().apply {
            add(
                PaySalaryModel(
                    Strings.screen_house_hold_salary_profile_set_up_salary_text,
                    R.drawable.ic_transaction_rate_arrow
                )
            )
            add(
                PaySalaryModel(
                    Strings.screen_house_hold_salary_profile_set_up_expense_text,
                    R.drawable.ic_expense
                )
            )
            add(
                PaySalaryModel(
                    Strings.screen_house_hold_salary_profile_transfer_bonus_text,
                    R.drawable.ic_yap_to_yap
                )
            )

        }
    }
}