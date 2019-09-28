package co.yap.yapcore.managers

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.responsedtos.AccountInfo

object MyUserManager {
    var user: AccountInfo? = null
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var cards: MutableLiveData<ArrayList<Card>> = MutableLiveData()
}
