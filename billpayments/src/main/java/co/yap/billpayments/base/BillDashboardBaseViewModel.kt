package co.yap.billpayments.base

import android.app.Application
import android.graphics.drawable.Drawable
import co.yap.billpayments.dashboard.IBillPayments
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class BillDashboardBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IBillPayments.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitleString?.set(title)
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        parentViewModel?.state?.toolbarVisibility?.set(visibility)
    }

    fun toggleSortIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.sortIconVisibility?.set(visibility)
    }

    fun toggleRightIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightIconVisibility?.set(visibility)
    }

    fun setRightIconDrawable(drawable: Drawable) {
        parentViewModel?.state?.rightIconDrawable?.set(drawable)
    }

    fun toogleLeftIconVisibility(visibility: Boolean){
        parentViewModel?.state?.leftIconVisibility?.set(visibility)

    }
}
