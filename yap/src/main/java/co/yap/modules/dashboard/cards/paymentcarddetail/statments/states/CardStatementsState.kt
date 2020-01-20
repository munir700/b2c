package co.yap.modules.dashboard.cards.paymentcarddetail.statments.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces.ICardStatments
import co.yap.networking.transactions.responsedtos.CardStatement
import co.yap.yapcore.BaseState

class CardStatementsState : BaseState(), ICardStatments.State {

    override var year: ObservableField<String> = ObservableField()
    override var hasRecords: ObservableField<Boolean> = ObservableField()
    override var statements: ObservableField<List<CardStatement>> = ObservableField()
}