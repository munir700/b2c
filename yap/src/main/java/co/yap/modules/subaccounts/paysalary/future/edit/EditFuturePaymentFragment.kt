package co.yap.modules.subaccounts.paysalary.future.edit

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEditFuturePaymentBinding
import co.yap.modules.others.helper.Constants
import co.yap.modules.subaccounts.paysalary.future.FuturePaymentVM
import co.yap.modules.subaccounts.paysalary.future.IFuturePayment
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.confirm

class EditFuturePaymentFragment :
    BaseNavViewModelFragment<FragmentEditFuturePaymentBinding, IFuturePayment.State, FuturePaymentVM>() {
    override fun getBindingVariable() = BR.futurePaymentVM
    override fun getLayoutId() = R.layout.fragment_edit_future_payment
    override fun getToolBarTitle() =
        getString(Strings.screen_household_future_payment_screen_tool_bar_text)

    override fun onClick(id: Int) {
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> {
                arguments?.putParcelable(
                    SchedulePayment::class.java.simpleName, SchedulePayment(
                        amount = state.amount.value,
                        nextProcessingDate = state.date.value
                    )
                )
                navigateForwardWithAnimation(
                    EditFuturePaymentFragmentDirections.actionEditFuturePaymentFragmentToPaymentConfirmationFragment(),
                    arguments
                )
            }
            R.id.tvCancel -> {
                state.futureTransaction?.value?.let {
                    confirm(
                        message = "Are you sure you want to cancel this scheduled salary?",
                        title = ""
                    ) {
                        viewModel.cancelSchedulePayment(it.scheduledPaymentUuid)
                    }
                }
            }
            Constants.EVENT_GO_BACK -> navigateBackWithResult(
                resultCode = Constants.EVENT_GO_BACK,
                data = arguments
            )
        }
    }
}
