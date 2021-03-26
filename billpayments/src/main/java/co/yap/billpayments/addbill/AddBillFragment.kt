package co.yap.billpayments.addbill

import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class AddBillFragment : PayBillBaseFragment<IAddBill.ViewModel>(),
    IAddBill.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_bill

    override val viewModel: IAddBill.ViewModel
        get() = ViewModelProviders.of(this).get(AddBillViewModel::class.java)
}
