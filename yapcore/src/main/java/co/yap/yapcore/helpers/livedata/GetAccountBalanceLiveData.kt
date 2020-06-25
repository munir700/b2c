package co.yap.yapcore.helpers.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.SingleSingletonHolder
import co.yap.yapcore.managers.MyUserManager

class GetAccountBalanceLiveData : LiveDataCallAdapter<CardBalance?>() {
    private val repository: CardsRepository = CardsRepository
    override fun onActive() {
        super.onActive()
        launch {
            when (val response = repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    value = response.data.data
                    cardBalance.value = value
                    MyUserManager.cardBalance = cardBalance
                }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        cancelAllJobs()
    }

    @MainThread

    companion object :
        SingleSingletonHolder<GetAccountBalanceLiveData>(::GetAccountBalanceLiveData) {
        @JvmStatic
        var cardBalance: MutableLiveData<CardBalance> = MyUserManager.cardBalance
    }
}