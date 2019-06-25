package co.yap.yapcore

import android.app.Application
import android.content.Context
import android.view.animation.TranslateAnimation
import androidx.lifecycle.*
import co.yap.yapcore.interfaces.CoroutineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class BaseViewModel<S: IBase.State>(application: Application) : AndroidViewModel(application),
    IBase.ViewModel<S>, CoroutineViewModel {

    override val context: Context
        get() = getApplication<Application>().applicationContext

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
        state.init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun OnResume() {
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

    override fun cancelAllJobs() {
        viewModelJob.cancel()
    }

    override fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    // override fun getContext(): Context = getApplication<Application>().applicationContext

    // TODO: use Translation module to get the translated string
    override fun getString(resourceId: Int): String = context.resources.getString(resourceId)

}

