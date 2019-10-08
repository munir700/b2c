package co.yap.yapcore.managers

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.responsedtos.AccountInfo

object MyUserManager {
    var user: AccountInfo? = null
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var cards: MutableLiveData<ArrayList<Card>> = MutableLiveData()
    val userImage: String = "https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg"
}
