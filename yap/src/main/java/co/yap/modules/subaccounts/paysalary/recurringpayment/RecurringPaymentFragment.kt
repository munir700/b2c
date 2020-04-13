package co.yap.modules.subaccounts.paysalary.recurringpayment

import androidx.fragment.app.FragmentManager
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentRecurringPaymentBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import javax.inject.Inject

class RecurringPaymentFragment :
    BaseNavViewModelFragment<FragmentRecurringPaymentBinding, IRecurringPayment.State, RecurringPaymentVM>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_recurring_payment

}