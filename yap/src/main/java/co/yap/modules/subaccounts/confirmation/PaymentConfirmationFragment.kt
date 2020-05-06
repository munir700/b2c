package co.yap.modules.subaccounts.confirmation

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPaymentConfirmationBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class PaymentConfirmationFragment :
    BaseNavViewModelFragment<FragmentPaymentConfirmationBinding, IPaymentConfirmation.State, PaymentConfirmationVM>() {

    override fun getBindingVariable() = BR.paymentConfirmationVM

    override fun getLayoutId() = R.layout.fragment_payment_confirmation
    override fun getToolBarTitle() =
        getString(Strings.screen_household_payment_confirmation_tool_bar_text)

    override fun setDisplayHomeAsUpEnabled()  = false
}