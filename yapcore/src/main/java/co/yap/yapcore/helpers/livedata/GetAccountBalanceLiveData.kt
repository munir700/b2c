package co.yap.yapcore.helpers.livedata

import android.os.Handler
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

                is RetroApiResponse.Success -> {
                    Handler().postDelayed({
                        value = response.data.data
                    }, 2000)
                }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        cancelAllJobs()
    }

    @MainThread
    companion object : SingleSingletonHolder<GetAccountBalanceLiveData>(::GetAccountBalanceLiveData)
//
//    companion object {
//        private lateinit var sInstance: GetAccountBalanceLiveData
//        @MainThread
//        fun get(): GetAccountBalanceLiveData {
//            sInstance = if (::sInstance.isInitialized) sInstance else GetAccountBalanceLiveData()
//            return sInstance
//        }
//    }
}