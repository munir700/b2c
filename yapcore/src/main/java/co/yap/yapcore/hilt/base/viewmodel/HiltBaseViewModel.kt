package co.yap.yapcore.hilt.base.viewmodel

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import co.yap.translation.Translator
import co.yap.widgets.State
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.interfaces.OnClickHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Created by Safi ur Rehman
 */
abstract class HiltBaseViewModel<S : IBase.State> : HiltCoroutineViewModel(),
    IBase.ViewModel<S>, OnClickHandler {
    var stateLiveData: MutableLiveData<State> = MutableLiveData()
    override val toolBarClickEvent = SingleClickEvent()
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

    override fun launch(block: suspend () -> Unit) =
        viewModelScope.launch { block() }


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

    override fun getString(resourceId: String) = Translator.getString(context, resourceId)
    override val clickEvent: SingleClickEvent? = SingleClickEvent()

    /**
     * override this method when there is  no need to use its super implementation.
     * recommended to not override this method. use @see <handleOnClick> must override
     */
    override fun handlePressOnView(id: Int) {
        clickEvent?.setValue(id)
        handleOnClick(id)
    }

    /**
     * Override this method in your [ViewModel]
     * you can manage your owen onclick logic by overriding this method
     */
    protected abstract fun handleOnClick(id: Int)
}


