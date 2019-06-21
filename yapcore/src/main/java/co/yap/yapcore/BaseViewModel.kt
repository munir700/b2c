package co.yap.yapcore

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import co.yap.yapcore.interfaces.CoroutineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    IBase.ViewModel, CoroutineViewModel {

    private var state: BaseState? = null
    override val viewModelJob: Job
        get() = Job()
    override val viewModelScope: CoroutineScope
        get() = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        cancelAllJobs()
        super.onCleared()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        getState().init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun OnResume() {
        getState().resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        getState().pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        getState().destroy()
    }

    override fun registerLifecycleOwner(owner: LifecycleOwner?) {
        unregisterLifecycleOwner(owner)
        owner?.lifecycle?.addObserver(this)
    }

    override fun unregisterLifecycleOwner(owner: LifecycleOwner?) {
        owner?.lifecycle?.removeObserver(this)
    }

    override fun getState(): IBase.State {
        if (state == null) state = BaseState()
        return state as BaseState
    }

    override fun cancelAllJobs() {
        viewModelJob.cancel()
    }

    override fun launch(block: () -> Unit) {
        viewModelScope.launch { block() }
    }

    override fun getContext(): Context = getApplication<Application>().applicationContext

    // TODO: use Translation module to get the translated string
    override fun getString(resourceId: Int): String = getContext().resources.getString(resourceId)
}

