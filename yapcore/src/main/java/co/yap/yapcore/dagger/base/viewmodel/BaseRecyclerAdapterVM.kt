package co.yap.yapcore.dagger.base.viewmodel

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.models.ApiResponse
import co.yap.widgets.State
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.base.interfaces.Refreshable


/**
 * Created by Muhammad Irfan Arshad
 *
 */
abstract class BaseRecyclerAdapterVM<T : Any, S : IBase.State> : DaggerBaseViewModel<S>(),
    Refreshable {
    val adapter = ObservableField<BaseRVAdapter<T, *, *>>()
    var stateLiveData: MutableLiveData<State> = MutableLiveData()
    var data: MutableList<T> = ArrayList()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }


    /**
     * Call this to fill data to [.adapter]
     * @param newData new data
     * @param refresh true if data come from refresh action (call remote api)
     */
    protected fun addData(@NonNull newData: MutableList<T>, refresh: Boolean = true) {
        if (data == null) {
            data = newData
        } else {
            if (refresh) {
                data = ArrayList()
            }
            data.addAll(newData)
        }
        adapter.get()?.setData(data)
    }

    /**
     * Call this to fill data to [.adapter]
     * @param newData new data
     * @param refresh true if data come from refresh action (call remote api)
     */
    protected fun setData(@NonNull newData: MutableList<T>, refresh: Boolean = true) {
        data = newData
        adapter.get()?.setData(data)
    }

    fun clear() {
        data = ArrayList()
        adapter.get()?.setData(data)
        adapter.notifyChange()
    }
    override fun refresh() {

    }
}