package co.yap.modules.subaccounts.paysalary.profile

import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.subaccounts.paysalary.profile.adapter.HHSalaryProfileTransfersAdapter
import co.yap.modules.subaccounts.paysalary.profile.adapter.SalarySetupAdapter
import co.yap.translation.Strings
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped


@Module
@InstallIn(FragmentComponent::class)
class HHSalaryProfileModule {

    @Provides
    @FragmentScoped
    fun provideHHSalaryProfileTransfersAdapter() =
        HHSalaryProfileTransfersAdapter(
            emptyMap()
        )

    @Provides
    @FragmentScoped
    fun provideWrappedAdapter(
        adapter: HHSalaryProfileTransfersAdapter,
        mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
    ): RecyclerView.Adapter<*> = mRecyclerViewExpandableItemManager.createWrappedAdapter(adapter)

    @Provides
    @FragmentScoped
    fun provideRecyclerViewExpandableItemManager() =
        RecyclerViewExpandableItemManager(null)

    @Provides
    @FragmentScoped
    fun provideHHPaySalaryAdapter() =
        SalarySetupAdapter(
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