package co.yap.billpayments.base

import android.app.Application
import co.yap.billpayments.home.IBillPayments
import co.yap.billpayments.paybills.IPayBills
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class PayBillBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IBillPayments.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitle = title
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        parentViewModel?.state?.toolbarVisibility?.set(visibility)
    }
}