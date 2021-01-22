package co.yap.yapcore

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData


abstract class BaseState : BaseObservable(), IBase.State {

    @get:Bindable
    override var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    override var toolbarTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolbarTitle)
        }

    @get:Bindable
    override var toolsBarVisibility: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolsBarVisibility)
        }

    @get:Bindable
    override var error: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
        }

    @get:Bindable
    override var toast: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toast)
        }

    override var viewState: MutableLiveData<Any?> = MutableLiveData()

    override fun reset() {
        loading = false
        toolbarTitle = ""
        error = ""
        toast = ""
    }

    override fun destroy() {

    }

    override fun init() {

    }

    override fun resume() {

    }

    override fun pause() {

    }

}