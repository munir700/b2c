package co.yap.yapcore.hilt.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.dagger.base.interfaces.CanFetchExtras
import co.yap.yapcore.managers.SessionManager

/**
 * Created by Safi ur Rehman
 */
abstract class HiltViewModel : ViewModel(), Observable, CanFetchExtras {
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null
    private var isFirstTimeUiCreate = true
    var mUserLiveData: MutableLiveData<AccountInfo> = MutableLiveData<AccountInfo>()
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.notifyCallbacks(this, fieldId, null)
    }

    override fun onCleared() {
        super.onCleared()
    }

    /**
     * called after fragment / activity is created with input bundle arguments
     *
     * @param bundle argument data
     */
    @CallSuper
    open fun onCreate(bundle: Bundle?) {
        if (isFirstTimeUiCreate) {
            mUserLiveData.value = SessionManager.user
            // this.bundle = bundle
            onFirsTimeUiCreate(bundle, null)
            isFirstTimeUiCreate = false
        }
    }

    /**
     * called after fragment / activity is created with input bundle arguments
     *
     * @param bundle argument data
     */
    @CallSuper
    open fun onCreate(bundle: Bundle?, navigation: NavController?) {
        if (isFirstTimeUiCreate) {
            mUserLiveData.value = SessionManager.user
            onFirsTimeUiCreate(bundle, navigation)
            isFirstTimeUiCreate = false
        }
    }

    /**
     * Called when UI create for first time only, since activity / fragment might be rotated,
     * we don't need to re-init data, because view model will survive, data aren't destroyed
     *
     * @param bundle
     */
    abstract fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?)
    override fun fetchExtras(extras: Bundle?) {

    }
}