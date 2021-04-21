package co.yap.billpayments.paybill.base

import android.app.Application
import android.graphics.drawable.Drawable
import co.yap.billpayments.dashboard.IBillPayments
import co.yap.billpayments.paybill.main.IPayBillMain
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class PayBillMainBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IPayBillMain.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitleString?.set(title)
    }

    fun toggleRightIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightIconVisibility?.set(visibility)
    }

}
