package co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels

import android.app.Application
import android.view.View
import co.yap.modules.dashboard.cards.addpaymentcard.main.interfaces.IAddPaymentCard
import co.yap.sendmoney.base.SMFeeViewModel
import co.yap.yapcore.IBase

abstract class AddPaymentChildViewModel<S : IBase.State>(application: Application) :
    SMFeeViewModel<S>(application) {
    var parentViewModel: IAddPaymentCard.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.tootlBarTitle = title
    }

    fun toggleToolBarVisibility(visibility : Int = View.VISIBLE) {
            parentViewModel?.state?.tootlBarVisibility = visibility
    }
}