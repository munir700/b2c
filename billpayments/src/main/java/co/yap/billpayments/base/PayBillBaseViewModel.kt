package co.yap.billpayments.base

import android.app.Application
import android.graphics.drawable.Drawable
import co.yap.billpayments.dashboard.IBillPayments
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

    fun toggleRightFirstIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.sortIconVisibility?.set(visibility)
    }

    fun toolgleRightSecondIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightIconVisibility?.set(visibility)
    }

    fun setRightSecondIconDrawable(drawable: Drawable) {
        parentViewModel?.state?.rightIconDrawable?.set(drawable)
    }
}
