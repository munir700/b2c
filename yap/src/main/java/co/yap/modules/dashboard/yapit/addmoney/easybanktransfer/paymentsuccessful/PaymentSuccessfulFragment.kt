package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.paymentsuccessful

import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPaymentSuccessfulBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment

class PaymentSuccessfulFragment:AddMoneyBaseFragment<FragmentPaymentSuccessfulBinding,IPaymentSuccessful.ViewModel>(),
    IPaymentSuccessful.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_payment_successful
    override val viewModel: PaymentSuccessfulViewModel by viewModels()
}