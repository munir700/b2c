package co.yap.billpayments.paybills

import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class PayBillsFragment : PayBillBaseFragment<IPayBills.ViewModel>(),
    IPayBills.View{

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bills

    override val viewModel: PayBillsViewModel
        get() = ViewModelProviders.of(this).get(PayBillsViewModel::class.java)

}