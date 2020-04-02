package co.yap.modules.subaccounts.paysalary.recurringpayment

import android.os.Bundle
import android.view.View
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentRecurringPaymentBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment

class RecurringPaymentFragment :
    BaseViewModelFragment<FragmentRecurringPaymentBinding, IRecurringPayment.State, RecurringPaymentVM>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_recurring_payment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fragmentManager = fragmentManager
    }
}