package co.yap.yapcore.dagger.base.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import co.yap.widgets.State
import co.yap.yapcore.IBase
import co.yap.yapcore.interfaces.CoroutineViewModel


import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammad Irfan Arshad
 *
 */
abstract class DaggerBaseViewModel<S : IBase.State>() : DaggerCoroutineViewModel(),
    IBase.ViewModel<S> {
    var stateLiveData: MutableLiveData<State> = MutableLiveData()
    override fun onCleared() {
        cancelAllJobs()
        super.onCleared()
    }

    override fun cancelAllJobs() {
        viewModelBGScope.close()
        viewModelScope.cancel()
        viewModelJob.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        state.init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        state.resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        state.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        state.destroy()
    }

    override fun registerLifecycleOwner(owner: LifecycleOwner?) {
        unregisterLifecycleOwner(owner)
        owner?.lifecycle?.addObserver(this)
    }

    override fun unregisterLifecycleOwner(owner: LifecycleOwner?) {
        owner?.lifecycle?.removeObserver(this)
    }

    override fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    override fun launchBG(block: suspend () -> Unit) = viewModelScope.async {
        block()

    }

    fun publishState(state: State) {
        stateLiveData.value = state
    }

    override val context: Context
        get() = c
    lateinit var c: Context
    override fun getString(resourceId: Int) = ""

    override fun getString(resourceId: String) = ""
}


