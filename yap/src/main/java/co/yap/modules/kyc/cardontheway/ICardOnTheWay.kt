package co.yap.modules.kyc.cardontheway

import androidx.databinding.ObservableField
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.yapcore.IBase

interface ICardOnTheWay {
    interface State : IBase.State {
        var bottomSheetItems: ObservableField<MutableList<CoreBottomSheetData>>
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel>
}