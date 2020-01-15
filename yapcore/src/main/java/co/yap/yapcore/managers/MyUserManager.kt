package co.yap.yapcore.managers

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object MyUserManager : IRepositoryHolder<CardsRepository> {

    override val repository: CardsRepository = CardsRepository
    var user: AccountInfo? = null
    var userAddress: Address? = null
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var cards: MutableLiveData<ArrayList<Card>> = MutableLiveData()
    var addressPhotoUrl: Bitmap? = null


    fun updateCardBalance() {
        getAccountBalanceRequest()
    }

    private fun getAccountBalanceRequest() {

        GlobalScope.launch {
            when (val response = repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    cardBalance.postValue(CardBalance(availableBalance = response.data.data?.availableBalance.toString()))
                }
                is RetroApiResponse.Error -> {

                }
            }
        }
    }

    fun getCardSerialNumber(): String {
        cards.value?.let {
            for (card in it) {
                if (card.cardType == "DEBIT") {
                    return card.cardSerialNumber
                }
            }
        }
        return ""
    }

    fun getPrimaryCard(): Card? {
        cards.value?.let {
            for (card in it) {
                if (card.cardType == "DEBIT") {
                    return card
                }
            }
        }
        return null
    }

    fun expireUserSession()
    {
        user = null
    }

}