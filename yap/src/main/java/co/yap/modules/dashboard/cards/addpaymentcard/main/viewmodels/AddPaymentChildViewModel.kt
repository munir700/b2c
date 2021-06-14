package co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.main.interfaces.IAddPaymentCard
import co.yap.sendmoney.base.SMFeeViewModel
import co.yap.yapcore.IBase

abstract class AddPaymentChildViewModel<S : IBase.State>(application: Application) :
    SMFeeViewModel<S>(application) {
    var parentViewModel: IAddPaymentCard.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.tootlBarTitle = title
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        val VISIBLE: Int = 0x00000000
        val GONE: Int = 0x00000008
        if (visibility) {
            parentViewModel?.state?.tootlBarVisibility = VISIBLE

        } else {
            parentViewModel?.state?.tootlBarVisibility = GONE

        }
    }
    fun toggleToolBarInVisibility(visibility: Boolean) {
        val VISIBLE: Int = 0x00000000
        val INVISIBLE: Int = 0x00000004
        if (visibility) {
            parentViewModel?.state?.tootlBarVisibility = INVISIBLE

        } else {
            parentViewModel?.state?.tootlBarVisibility = VISIBLE

        }
    }
}