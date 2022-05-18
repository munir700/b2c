package co.yap.yapcore.hilt.base.viewmodel

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.databinding.ObservableField

import androidx.navigation.NavController
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.base.interfaces.OnPullToRefreshable


/**
 * Created by Munir Ahmad
 *
 */
abstract class BaseRecyclerAdapterVMV2<T : ApiResponse, S : IBase.State> : HiltBaseViewModel<S>(),
    OnPullToRefreshable {
    val adapter = ObservableField<BaseRVAdapter<T, *, *>>()

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
    protected fun setData(@NonNull newData: MutableList<T>?, refresh: Boolean = true) {
        newData?.let {
            data = it
            adapter.get()?.setData(data)
        }

    }
    fun removeItem(type: T) {
        val position = this.data.indexOf(type)
        removeItemAt(position)
//        this.data.remove(type)
//        adapter.get()?.remove(type)
    }

    fun removeItemAt(position: Int) {
        this.data.removeAt(position)
        adapter.get()?.removeAt(position)
        //adapter.notifyChange()
    }


//    fun clear() {
//        data = ArrayList()
//        adapter.get()?.setData(data)
//        adapter.notifyChange()
//    }

    override fun onRefresh() {

    }
}