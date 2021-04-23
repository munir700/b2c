package co.yap.modules.subaccounts.paysalary.recurringpayment

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentRecurringPaymentBinding
import co.yap.modules.others.helper.Constants.EVENT_GO_BACK
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.leanplum.HHUserActivityEvents
import co.yap.yapcore.leanplum.trackEvent

class RecurringPaymentFragment :
    BaseNavViewModelFragment<FragmentRecurringPaymentBinding, IRecurringPayment.State, RecurringPaymentVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_recurring_payment

    override fun getToolBarTitle() = getString(Strings.screen_household_recurring_payment_title)

    override fun onClick(id: Int) {
        state.schedulePayment.value?.nextProcessingDate = state.date.value
        arguments?.putParcelable(
            SchedulePayment::class.java.simpleName,
            state.schedulePayment.value
        )
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> {
                trackEvent(HHUserActivityEvents.HH_RECURRING_SALARY_SET.type)
                navigateForwardWithAnimation(
                    RecurringPaymentFragmentDirections.actionRecurringPaymentFragmentToPaymentConfirmationFragment(),
                    arguments
                )
            }
            R.id.tvCancel -> {
                state.recurringTransaction?.value?.let {
                    confirm(
                        message = "Are you sure you want to cancel this recurring salary?",
                        title = ""
                    ) {
                        viewModel.cancelSchedulePayment(it.scheduledPaymentUuid)
                    }
                }
            }
            EVENT_GO_BACK -> navigateBackWithResult(
                resultCode = EVENT_GO_BACK,
                data = arguments
            )
        }
    }
}
