package co.yap.modules.subaccounts.paysalary.entersalaryamount

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEnterSalaryAmountBinding
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData

class EnterSalaryAmountFragment :
    BaseNavViewModelFragment<FragmentEnterSalaryAmountBinding, IEnterSalaryAmount.State, EnterSalaryAmountVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_enter_salary_amount
    override fun getToolBarTitle() = getString(
        Strings.screen_household_pay_salary_screen_display_text_title,
        state.subAccount.value?.getFullName() ?: ""
    )

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        GetAccountBalanceLiveData.get().observe(this, Observer {})
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        arguments?.putParcelable(
            SchedulePayment::class.simpleName,
            SchedulePayment(
                amount = state.amount.value,
                isRecurring = state.isRecurring.value
            )
        )
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> navigateForwardWithAnimation(
                EnterSalaryAmountFragmentDirections.actionEnterSalaryAmountFragmentToPaymentConfirmationFragment(),
                arguments
            )
            viewModel.GO_TO_RECURING -> {
                navigateForwardWithAnimation(
                    EnterSalaryAmountFragmentDirections.actionEnterSalaryAmountFragmentToRecurringPaymentFragment(),
                    arguments
                )
            }
        }
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()
    }
}
