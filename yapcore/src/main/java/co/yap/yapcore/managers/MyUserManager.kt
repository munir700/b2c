package co.yap.yapcore.managers

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.responsedtos.AccountInfo

object MyUserManager {
    var user: AccountInfo? = null
    var userAddress: Address? = null
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var cards: MutableLiveData<ArrayList<Card>> = MutableLiveData()
    var addressPhotoUrl: Bitmap? = null

}
