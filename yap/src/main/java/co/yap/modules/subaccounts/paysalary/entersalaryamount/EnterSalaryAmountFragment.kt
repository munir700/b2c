package co.yap.modules.subaccounts.paysalary.entersalaryamount

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEnterSalaryAmountBinding
import co.yap.modules.dashboard.yapit.topup.cardslisting.TopUpBeneficiariesActivity
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.leanplum.HHUserActivityEvents
import co.yap.yapcore.leanplum.trackEvent

class EnterSalaryAmountFragment :
    BaseNavViewModelFragment<FragmentEnterSalaryAmountBinding, IEnterSalaryAmount.State, EnterSalaryAmountVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_enter_salary_amount
    override fun getToolBarTitle() = getString(
        Strings.screen_household_pay_salary_screen_display_text_title,
        state.subAccount.value?.getFullName() ?: ""
    )

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        GetAccountBalanceLiveData.get().observe(this, Observer {})
    }

    override fun onClick(id: Int) {
        arguments?.putParcelable(
            SchedulePayment::class.simpleName,
            SchedulePayment(
                amount = state.amount.value,
                isRecurring = state.isRecurring.value
            )
        )
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> {
                trackEvent(HHUserActivityEvents.HH_SALARY_PAID.type)
                navigateForwardWithAnimation(
                    EnterSalaryAmountFragmentDirections.actionEnterSalaryAmountFragmentToPaymentConfirmationFragment(),
                    arguments
                )
            }
            viewModel.GO_TO_RECURING -> {
                trackEvent(HHUserActivityEvents.HH_RECURRING_SALARY_SET_AND_PAID.type)
                navigateForwardWithAnimation(
                    EnterSalaryAmountFragmentDirections.actionEnterSalaryAmountFragmentToRecurringPaymentFragment(),
                    arguments
                )
            }
            viewModel.TOP_UP_ACCOUNT -> {
                launchActivity<TopUpBeneficiariesActivity>(requestCode = RequestCodes.REQUEST_SHOW_BENEFICIARY) {
                    putExtra(
                        Constants.SUCCESS_BUTTON_LABEL,
                        getString(Strings.screen_topup_success_display_text_dashboard_action_button_title)
                    )
                }
            }
        }
    }
}
