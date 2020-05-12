package co.yap.modules.subaccounts.paysalary.employee

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPayHhemployeeSalaryBinding
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BackNavigationResult
import co.yap.yapcore.dagger.base.navigation.BackNavigationResultListener
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.enums.PackageType

class PayHHEmployeeSalaryFragment :
    BaseNavViewModelFragment<FragmentPayHhemployeeSalaryBinding, IPayHHEmployeeSalary.State, PayHHEmployeeSalaryVM>(),
    BackNavigationResultListener {
    override fun getBindingVariable() = BR.payHHEmployeeSalaryVM

    override fun getLayoutId() = R.layout.fragment_pay_hhemployee_salary
    override fun getToolBarTitle() = getString(
        Strings.screen_household_pay_salary_screen_display_text_title,
        state.subAccount.value?.getFullName() ?: ""
    )

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {

            R.id.btnPayNow -> {
                arguments?.putParcelable(
                    SalaryTransaction::class.simpleName,
                    state.lastTransaction?.value
                )
                navigateForwardWithAnimation(
                    PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToEnterSalaryAmountFragment(),
                    arguments
                )

            }
            R.id.llScheduleOnce -> {
                state.futureTransaction?.value?.let {
                    arguments?.putParcelable(
                        SchedulePayment::class.simpleName, it
                    )
                }
                if (state.futureTransaction?.value == null) {
                    navigateForwardWithAnimation(
                        PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToFuturePaymentFragment(),
                        arguments
                    )
                }
            }
            R.id.llMakeRecurring -> {
                arguments?.putParcelable(SchedulePayment::class.simpleName, null)
                state.recurringTransaction?.value?.let {
                    arguments?.putParcelable(
                        SchedulePayment::class.simpleName, it
                    )
                }
                navigateForwardWithAnimation(
                    PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToRecurringPaymentFragment(),
                    arguments
                )
            }
        }
    }

    override fun onNavigationResult(result: BackNavigationResult) {
        state.subAccount.value?.let {
            viewModel.getSchedulePayment(it.accountUuid)
            viewModel.getLastTransaction(it.accountUuid)
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
