package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class AdditionalInfoBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IAdditionalInfo.ViewModel? = null

    fun setTitle(title: String) {
        parentViewModel?.state?.title?.set(title)
    }

    fun setSubTitle(title: String) {
        parentViewModel?.state?.subTitle?.set(title)
    }

}