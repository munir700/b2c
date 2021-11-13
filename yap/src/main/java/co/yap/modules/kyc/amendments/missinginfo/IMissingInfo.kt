package co.yap.modules.kyc.amendments.missinginfo

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.Section
import co.yap.yapcore.IBase

interface IMissingInfo {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val adapter: ObservableField<MissingInfoAdapter>
        val onClickEvent: MutableLiveData<Int>
        val missingInfoMap: MutableLiveData<HashMap<Section?, List<String>?>>
        fun getMissingInfoItems()
        fun handlePressView(id: Int)
    }

    interface State : IBase.State {
    }
}