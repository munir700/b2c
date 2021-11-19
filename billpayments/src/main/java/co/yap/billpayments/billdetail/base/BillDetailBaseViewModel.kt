package co.yap.billpayments.billdetail.base

import android.app.Application
import co.yap.billpayments.billdetail.IBillDetail
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class BillDetailBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IBillDetail.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitleString?.set(title)
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        parentViewModel?.state?.toolbarVisibility?.set(visibility)
    }

    fun toggleRightIconVisibility(visibility: Boolean) {
        parentViewModel?.state?.rightIconVisibility?.set(visibility)
    }
}
