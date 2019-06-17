package co.yap.yapcore

import android.app.Application
import androidx.lifecycle.*


abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    IBase.ViewModel {

    //var viewListener: N? = null
    private var state: BaseState? = null

    override fun onCleared() {
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

    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        if (owner != null) {
            unregisterLifecycleOwner(owner)
            owner.lifecycle.addObserver(this)
        }
    }

    override fun unregisterLifecycleOwner(owner: LifecycleOwner) {
        owner?.lifecycle?.removeObserver(this)
    }

      override fun getState(): IBase.State {
          if (state == null) state = BaseState()
          return state as BaseState
      }
}

