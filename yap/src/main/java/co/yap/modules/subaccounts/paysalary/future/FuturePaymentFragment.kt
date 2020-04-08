package co.yap.modules.subaccounts.paysalary.future

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentFuturePaymentBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class FuturePaymentFragment : BaseNavViewModelFragment<FragmentFuturePaymentBinding, IFuturePayment.State, FuturePaymentVM>() {

    override fun getBindingVariable() = BR.futurePaymentVM

    override fun getLayoutId() = R.layout.fragment_future_payment
}