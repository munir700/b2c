package co.yap.billpayments.mybills

import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class MyBillsFragment : PayBillBaseFragment<IMyBills.ViewModel>(),
    IMyBills.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_my_bills

    override val viewModel: IMyBills.ViewModel
        get() = ViewModelProviders.of(this).get(MyBillsViewModel::class.java)
}
