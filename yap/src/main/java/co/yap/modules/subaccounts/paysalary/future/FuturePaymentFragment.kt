package co.yap.modules.subaccounts.paysalary.future

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentFuturePaymentBinding
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class FuturePaymentFragment :
    BaseNavViewModelFragment<FragmentFuturePaymentBinding, IFuturePayment.State, FuturePaymentVM>() {
    override fun getBindingVariable() = BR.futurePaymentVM
    override fun getLayoutId() = R.layout.fragment_future_payment
    override fun getToolBarTitle() =
        getString(Strings.screen_household_future_payment_screen_tool_bar_text)

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> {
                arguments?.putParcelable(
                    SchedulePayment::class.java.simpleName, SchedulePayment(
                        amount = state.amount.value,
                        nextProcessingDate = state.date.get()
                    )
                )
                navigateForwardWithAnimation(
                    FuturePaymentFragmentDirections.actionFuturePaymentFragmentToPaymentConfirmationFragment(),
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
