package co.yap.yapcore.dagger.base.viewmodel

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import co.yap.yapcore.interfaces.CoroutineViewModel
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext


abstract class DaggerCoroutineViewModel: DaggerViewModel(), CoroutineViewModel {

    private val TAG: String = this.javaClass.simpleName

    private var isFirstTimeUiCreate = true

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
    override fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    override fun launchBG(block: suspend () -> Unit) = viewModelScope.async {
        block()

    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    /**
     * called after fragment / activity is created with input bundle arguments
     *
     * @param bundle argument data
     */
    @CallSuper
    open fun onCreate(bundle: Bundle?) {

        if (isFirstTimeUiCreate) {
            // this.bundle = bundle
            onFirsTimeUiCreate(bundle , null)
            isFirstTimeUiCreate = false
        }
    }
}