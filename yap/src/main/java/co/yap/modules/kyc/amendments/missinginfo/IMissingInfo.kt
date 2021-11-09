package co.yap.modules.kyc.amendments.missinginfo

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import kotlin.collections.ArrayList

interface IMissingInfo {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val adapter: ObservableField<MissingInfoAdapter>
        val onClickEvent: MutableLiveData<Int>
        val missingInfoItems: MutableLiveData<ArrayList<String>>
        fun getMissingInfoItems()
        fun handlePressView(id: Int)
    }

    interface State : IBase.State {
    }
}