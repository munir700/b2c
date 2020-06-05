package co.yap.yapcore.interfaces

import co.yap.networking.models.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

interface CoroutineViewModel {
    val viewModelJob: Job
    val viewModelScope: CoroutineScope
    fun cancelAllJobs()
    fun launch(block: suspend () -> Unit)
    fun async(block: suspend () -> Unit): Any
    fun launchBG(block: suspend () -> Unit): Any
}