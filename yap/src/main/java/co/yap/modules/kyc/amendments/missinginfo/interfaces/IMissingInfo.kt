package co.yap.modules.kyc.amendments.missinginfo.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IMissingInfo {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val onClickEvent: MutableLiveData<Int>
        val missingInfoItems: MutableLiveData<ArrayList<String>>
        fun getMissingInfoItems()
        fun handlePressView(id: Int)
    }

    interface State : IBase.State {
    }
}