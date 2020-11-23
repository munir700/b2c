package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment

class AddVirtualCardFragment(): AddPaymentChildFragment<IAddVirtualCard.ViewModel>(){
    override fun getBindingVariable(): Int {
        TODO("Not yet implemented")
    }

    override fun getLayoutId(): Int {
        TODO("Not yet implemented")
    }

    override val viewModel: IAddVirtualCard.ViewModel
        get() = TODO("Not yet implemented")
}