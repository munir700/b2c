package co.yap.yapcore.helpers.livedata

import androidx.lifecycle.LiveData
import co.yap.yapcore.dagger.base.viewmodel.DaggerCoroutineViewModel
import co.yap.yapcore.interfaces.CoroutineViewModel
import kotlinx.coroutines.*

abstract class LiveDataCallAdapter<T> : LiveData<T>(), CoroutineViewModel {
    override val viewModelJob: Job
        get() = Job()
    override val viewModelScope: CoroutineScope
        get() = CoroutineScope(viewModelJob + Dispatchers.Main)
    val viewModelBGScope =
        DaggerCoroutineViewModel.CloseableCoroutineScope(viewModelJob + Dispatchers.IO)

    override fun cancelAllJobs() {
        viewModelBGScope.close()
        viewModelScope.cancel()
        viewModelJob.cancel()
    }

    override fun launch(block: suspend () -> Unit)=
        viewModelScope.launch { block() }


    override fun launchBG(block: suspend () -> Unit) = viewModelScope.async {
        block()
    }

    override fun async(block: suspend () -> Unit) = viewModelScope.async { block }
    override fun onInactive() {
        super.onInactive()
        value = null
    }
}