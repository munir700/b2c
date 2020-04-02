package co.yap.modules.subaccounts.confirmation

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPaymentConfirmationBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment


class PaymentConfirmationFragment : BaseViewModelFragment<FragmentPaymentConfirmationBinding,IPaymentConfirmation.State,PaymentConfirmationVM>() {

    override fun getBindingVariable() = BR.paymentConfirmationVM

    override fun getLayoutId()= R.layout.fragment_payment_confirmation
}