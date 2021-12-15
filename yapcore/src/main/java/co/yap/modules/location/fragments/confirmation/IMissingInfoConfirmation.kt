package co.yap.modules.location.fragments.confirmation

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IMissingInfoConfirmation {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val onClickEvent: SingleClickEvent
        fun handlePressView(id: Int)
    }

    interface State : IBase.State {
        val title: ObservableField<String>
        val subTitle: ObservableField<String>
        var missingInfoMap: HashMap<String?, List<String>?>?
    }
}