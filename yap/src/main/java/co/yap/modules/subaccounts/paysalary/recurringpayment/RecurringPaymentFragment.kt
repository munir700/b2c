package co.yap.modules.subaccounts.paysalary.recurringpayment

import android.widget.CompoundButton
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentRecurringPaymentBinding
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import kotlinx.android.synthetic.main.fragment_recurring_payment.*

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
        state.schedulePayment.value?.nextProcessingDate = state.date.get()
        arguments?.putParcelable(
            SchedulePayment::class.java.simpleName,
            state.schedulePayment.value
        )
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> {
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