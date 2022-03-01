package co.yap.modules.dashboard.cards.cardlist

import co.yap.modules.dashboard.cards.cardlist.ICardsList
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseState

class CardsListState : BaseState(), ICardsList.State {
    override var cardMap: MutableMap<String?, List<Card>> = mutableMapOf()
}