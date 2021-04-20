package co.yap.billpayments.dashboard.editbill

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class EditBillFragment : PayBillBaseFragment<IEditBill.ViewModel>(),
    IEditBill.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_edit_bill

    override val viewModel: IEditBill.ViewModel
        get() = ViewModelProviders.of(this).get(EditBillViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }


    override fun setObservers() {
    }

    override fun removeObservers() {
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
