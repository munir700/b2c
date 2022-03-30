package co.yap.yapcore.dagger.base.viewmodel

import android.content.Intent
import co.yap.yapcore.interfaces.CoroutineViewModel
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext


abstract class DaggerCoroutineViewModel : DaggerViewModel(), CoroutineViewModel {

    private val TAG: String = this.javaClass.simpleName

    override val viewModelJob: Job
        get() = Job()
    override val viewModelScope: CoroutineScope
        get() = CoroutineScope(viewModelJob + Dispatchers.Main)

    val viewModelBGScope = CloseableCoroutineScope(viewModelJob + Dispatchers.IO)

    class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
        override val coroutineContext: CoroutineContext = context

        override fun close() {
            coroutineContext.cancel()
        }
    }

    override fun onCleared() {
        cancelAllJobs()
        super.onCleared()
    }

    override fun cancelAllJobs() {
        viewModelBGScope.close()
        viewModelScope.cancel()
        viewModelJob.cancel()
    }

    override fun launch(block: suspend () -> Unit) =
        viewModelScope.launch { block() }


    override fun launchBG(block: suspend () -> Unit) = viewModelScope.async {
        block()
    }

    override fun async(block: suspend () -> Unit) = viewModelScope.async { block }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

}