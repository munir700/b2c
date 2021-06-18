package co.yap.modules.dashboard.cards.home.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.yapcore.BaseState
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.managers.SessionManager

class YapCardsState : BaseState(), IYapCards.State {

    override var enableAddCard: ObservableBoolean =
        ObservableBoolean(PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus)
    override var showIndicator: ObservableBoolean = ObservableBoolean(false)

    @get:Bindable
    override var noOfCard: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.noOfCard)
        }

    override var totalCardsCount: ObservableField<Int> = ObservableField(0)
    override var cardIndicator: ObservableField<String> = ObservableField("0 of 0")
    override var enableLeftIcon: ObservableBoolean = ObservableBoolean(false)
}
