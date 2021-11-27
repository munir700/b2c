package co.yap.billpayments.addbiller.base

import android.app.Application
import co.yap.billpayments.addbiller.main.IAddBill
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class AddBillBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IAddBill.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitleString?.set(title)
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        parentViewModel?.state?.toolbarVisibility?.set(visibility)
    }

    fun toolgleRightIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightIconVisibility?.set(visibility)
    }
}
