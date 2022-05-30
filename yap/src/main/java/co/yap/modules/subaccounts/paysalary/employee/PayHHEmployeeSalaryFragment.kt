package co.yap.modules.subaccounts.paysalary.employee

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPayHhemployeeSalaryBinding
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BackNavigationResult
import co.yap.yapcore.dagger.base.navigation.BackNavigationResultListener

import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayHHEmployeeSalaryFragment :
    BaseNavViewModelFragmentV2<FragmentPayHhemployeeSalaryBinding, IPayHHEmployeeSalary.State, PayHHEmployeeSalaryVM>(),
    BackNavigationResultListener {
    override fun getBindingVariable() = BR.payHHEmployeeSalaryVM

    override fun getLayoutId() = R.layout.fragment_pay_hhemployee_salary

    override val viewModel: PayHHEmployeeSalaryVM by viewModels()

    override fun getToolBarTitle() = getString(
        Strings.screen_household_pay_salary_screen_display_text_title,
        viewModel.state.subAccount.value?.getFullName() ?: ""
    )

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnPayNow -> {
                arguments?.putParcelable(
                    SalaryTransaction::class.simpleName,
                    viewModel.state.lastTransaction?.value
                )
                navigateForwardWithAnimation(
                    PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToEnterSalaryAmountFragment(),
                    arguments
                )
            }
            R.id.llScheduleOnce -> {
                arguments?.remove(SchedulePayment::class.simpleName)
                viewModel.state.futureTransaction?.value?.let {
                    arguments?.putParcelable(
                        SchedulePayment::class.simpleName, it
                    )
                }
                viewModel.state.futureTransaction?.value?.nextProcessingDate?.let {
                    navigateForwardWithAnimation(
                        PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToEditFuturePaymentFragment(),
                        arguments
                    )
                } ?: navigateForwardWithAnimation(
                    PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToFuturePaymentFragment(),
                    arguments
                )

            }
            R.id.llMakeRecurring -> {
                arguments?.remove(SchedulePayment::class.simpleName)
                arguments?.putParcelable(SchedulePayment::class.simpleName, null)
                viewModel.state.recurringTransaction?.value?.let {
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
        viewModel.state.subAccount.value?.let {
            viewModel.getSchedulePayment(it.accountUuid)
            viewModel.getLastTransaction(it.accountUuid)
        }
    }
}
