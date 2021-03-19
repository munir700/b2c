package co.yap.billpayments.billerlist

import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class BillersFragment : PayBillBaseFragment<IBillers.ViewModel>(),
    IBillers.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_billers

    override val viewModel: BillersViewModel
        get() = ViewModelProviders.of(this).get(BillersViewModel::class.java)

}
