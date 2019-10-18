package co.yap.modules.yapit.y2ytransfer.viewmodels

import android.app.Application
import co.yap.modules.yapit.y2ytransfer.interfaces.IY2Y
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class Y2YBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    var parentViewModel: IY2Y.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.tootlBarTitle = title
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        val VISIBLE: Int = 0x00000000
        val GONE: Int = 0x00000008
        if (visibility) {
            parentViewModel?.state?.tootlBarVisibility = VISIBLE

        } else {
            parentViewModel?.state?.tootlBarVisibility = GONE

        }
    }
}