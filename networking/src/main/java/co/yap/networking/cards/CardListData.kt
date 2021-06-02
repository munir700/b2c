package co.yap.networking.cards

import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.models.ApiResponse

data class CardListData(
    var cardType: String,
    var cards: List<Card>
) : ApiResponse()
