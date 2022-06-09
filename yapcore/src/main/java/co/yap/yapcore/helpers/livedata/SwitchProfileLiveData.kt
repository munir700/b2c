package co.yap.yapcore.helpers.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.models.RetroApiResponse

class SwitchProfileLiveData(private val uuid: String?, private val owner: LifecycleOwner) :
    LiveDataCallAdapter<AccountInfo?>() {
    private val authRepository: AuthRepository = AuthRepository

    override fun onActive() {
        super.onActive()
        launch {
            when (uuid?.let { authRepository.switchProfile(it) }) {
                is RetroApiResponse.Success -> {
                    GetAccountInfoLiveData.get().observe(owner, Observer {
                        value = it
                    })
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        value = null
        cancelAllJobs()
    }

    companion object {
        private lateinit var sInstance: SwitchProfileLiveData

        @MainThread
        fun get(uuid: String, owner: LifecycleOwner): SwitchProfileLiveData {
            sInstance =
                if (::sInstance.isInitialized) sInstance else SwitchProfileLiveData(uuid, owner)
            return sInstance
        }
    }
}