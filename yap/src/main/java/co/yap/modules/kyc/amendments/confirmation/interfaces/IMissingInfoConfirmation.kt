package co.yap.modules.kyc.amendments.confirmation.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IMissingInfoConfirmation {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val onClickEvent: MutableLiveData<Int>
        fun handlePressView(id: Int)
    }

    interface State : IBase.State {
        val subTitle: ObservableField<String>
    }
}