package co.yap.yapcore.helpers.livedata

import androidx.annotation.MainThread
import co.yap.yapcore.helpers.SingleSingletonHolder

class SwitchProfileLiveData : LiveDataCallAdapter<String>() {

    override fun onActive() {
        super.onActive()
        launch {

        }
    }

    override fun onInactive() {
        super.onInactive()
        value = null
        cancelAllJobs()
    }

    @MainThread
    companion object : SingleSingletonHolder<SwitchProfileLiveData>(::SwitchProfileLiveData)
}