package co.yap.billpayments.billerdetail

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class BillerDetailFragment : PayBillBaseFragment<IBillerDetail.ViewModel>(),
    IBillerDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_biller_detail

    override val viewModel: IBillerDetail.ViewModel
        get() = ViewModelProviders.of(this).get(BillerDetailViewModel::class.java)

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
