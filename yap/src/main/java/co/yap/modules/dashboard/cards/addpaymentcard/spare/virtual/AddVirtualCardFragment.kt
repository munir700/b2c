package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment

class AddVirtualCardFragment() : AddPaymentChildFragment<IAddVirtualCard.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_virtual_card
    override val viewModel: IAddVirtualCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddVirtualCardViewModel::class.java)
}