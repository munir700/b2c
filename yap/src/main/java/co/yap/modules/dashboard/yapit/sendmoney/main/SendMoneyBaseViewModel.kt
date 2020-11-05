package co.yap.modules.dashboard.yapit.sendmoney.main

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class SendMoneyBaseViewMode<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: ISendMoneyMain.ViewModel = SendMoneyMainViewModel(application)

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitle = title
    }
}