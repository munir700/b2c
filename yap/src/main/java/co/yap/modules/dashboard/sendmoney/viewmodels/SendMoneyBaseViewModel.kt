package co.yap.modules.dashboard.sendmoney.viewmodels

import android.app.Application
import co.yap.modules.dashboard.sendmoney.interfaces.ISendMoney
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class SendMoneyBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: ISendMoney.ViewModel? = null

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
}