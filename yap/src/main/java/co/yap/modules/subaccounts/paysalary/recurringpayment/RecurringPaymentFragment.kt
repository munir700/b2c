package co.yap.modules.subaccounts.paysalary.recurringpayment

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentRecurringPaymentBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class RecurringPaymentFragment :
    BaseNavViewModelFragment<FragmentRecurringPaymentBinding, IRecurringPayment.State, RecurringPaymentVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_recurring_payment

    override fun getToolBarTitle() = getString(Strings.screen_household_recurring_payment_title)
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> {
                state.schedulePayment.value?.nextProcessingDate = state.date.get()
                navigateForwardWithAnimation(
                    RecurringPaymentFragmentDirections.actionRecurringPaymentFragmentToPaymentConfirmationFragment(),
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