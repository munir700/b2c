package co.yap.billpayments.base

import android.app.Application
import co.yap.billpayments.home.IBillPayments
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class PayBillBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IBillPayments.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitleString?.set(title)
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        parentViewModel?.state?.toolbarVisibility?.set(visibility)
    }

    fun toolgleRightIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightIconVisibility?.set(visibility)
    }

    fun toogleLeftIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.leftIconVisibility?.set(visibility)
    }
}
