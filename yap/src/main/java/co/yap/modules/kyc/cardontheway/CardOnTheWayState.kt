package co.yap.modules.kyc.cardontheway

import androidx.databinding.ObservableField
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.yapcore.BaseState

class CardOnTheWayState : BaseState(), ICardOnTheWay.State {
    override var bottomSheetItems: ObservableField<MutableList<CoreBottomSheetData>> =
        ObservableField(arrayListOf())
}