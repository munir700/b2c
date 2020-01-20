package co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces

import androidx.databinding.ObservableField
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.CardStatement
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface ICardStatments {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var card: Card
        fun handlePressOnView(id: Int)
        fun loadStatements(serialNumber: String)
    }

    interface State : IBase.State {
        var year: ObservableField<String>
        var hasRecords: ObservableField<Boolean>
        var statements: ObservableField<List<CardStatement>>
    }
}