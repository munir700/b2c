package co.yap.billpayments.payall.base

import android.app.Application
import co.yap.billpayments.payall.main.IPayAllMain
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class PayAllBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IPayAllMain.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitleString?.set(title)
    }

    fun toggleRightIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightIconVisibility?.set(visibility)
    }
}
