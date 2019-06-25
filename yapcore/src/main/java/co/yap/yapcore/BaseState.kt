package co.yap.yapcore

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


abstract class BaseState : BaseObservable(), IBase.State {
    private var loading = false
    private lateinit var error: String
    private lateinit var toolbarTitle: String

    override fun reset() {
        loading = false
        toolbarTitle = ""
        error = ""
    }

    @Bindable
    fun setLoading(loading: Boolean) {
        this.loading = loading
        notifyPropertyChanged(BR.loading)
    }

    override fun isLoading(): Boolean {
        return this.loading
    }

    fun setToolbarTitle(title: String) {
        toolbarTitle = title
        notifyPropertyChanged(BR.toolbarTitle)
    }

    @Bindable
    override fun getToolbarTitle(): String {
        return toolbarTitle
    }

    override fun getString(key: String): String {
//        return Translator.getInstance().getString(key)
        return ""
    }

    @Bindable
    override fun getError(): String {
        return error
    }

    fun setError(error: String) {
        this.error = error
        notifyPropertyChanged(BR.error)
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