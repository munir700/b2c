package co.yap.yapcore.helpers.livedata

import android.content.Context
import androidx.annotation.MainThread
import co.yap.networking.authentication.AuthApi
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.SingletonHolder

class LogOutLiveData constructor(val context: Context) : LiveDataCallAdapter<Boolean>() {
    private val authRepository: AuthApi = AuthRepository

    override fun onActive() {
        super.onActive()
        val deviceId: String? =
            SharedPreferenceManager.getInstance(context).getValueString(KEY_APP_UUID)
        launch {
            when (val response = authRepository.logout(deviceId.toString())) {
                is RetroApiResponse.Success -> {
                    value = true
                }
                is RetroApiResponse.Error -> {
                    value = false
                }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        cancelAllJobs()
    }

    @MainThread
    companion object : SingletonHolder<LogOutLiveData, Context>(::LogOutLiveData)
}
