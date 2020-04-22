package co.yap.yapcore.helpers.livedata

import androidx.annotation.MainThread
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.SingleSingletonHolder

class GetAccountBalanceLiveData : LiveDataCallAdapter<CardBalance?>() {
    private val repository: CardsRepository = CardsRepository
    override fun onActive() {
        super.onActive()
        launch {
            when (val response = repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> value = response.data.data
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        cancelAllJobs()
    }

    @MainThread
    companion object : SingleSingletonHolder<GetAccountBalanceLiveData>(::GetAccountBalanceLiveData)
}