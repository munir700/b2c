package co.yap.billpayments.base

import android.app.Application
import android.graphics.drawable.Drawable
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

    fun toolgleRightFirstIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightFirstIconVisibility?.set(visibility)
    }
    fun toolgleRightSecondIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightSecondIconVisibility?.set(visibility)
    }

    fun setRightFirstIconDrawable(drawable:Drawable){
        parentViewModel?.state?.rightFirstIconDrawable?.set(drawable)
    }

    fun setRightSecondIconDrawable(drawable:Drawable){
        parentViewModel?.state?.rightSecondIconDrawable?.set(drawable)
    }
}
