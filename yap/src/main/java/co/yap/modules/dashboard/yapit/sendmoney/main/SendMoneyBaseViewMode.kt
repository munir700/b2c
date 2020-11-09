package co.yap.modules.dashboard.yapit.sendmoney.main

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class SendMoneyBaseViewMode<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: SendMoneyMainViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitle = title
    }

    fun setRightText(text: String){
        parentViewModel?.state?.rightButtonTextVisibility?.set(true)
        parentViewModel?.state?.rightIconVisibility?.set(false)
        parentViewModel?.state?.rightButtonText?.set(text)
    }

    fun toggleRightIconVisibility(visible: Boolean){
        parentViewModel?.state?.rightIconVisibility?.set(visible)
        parentViewModel?.state?.rightButtonTextVisibility?.set(!visible)
    }
}