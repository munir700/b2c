package co.yap.yapcore

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.PropertyChangeRegistry
import androidx.navigation.NavController
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.dagger.base.viewmodel.DaggerViewModel

/**
 * Created by Muhammad Irfan Arshad
 *
 */
abstract class BaseListItemViewModel<ITEM : ApiResponse> : DaggerViewModel(), OnItemClickListener {


    abstract fun setItem(item: ITEM, position: Int)
    abstract fun getItem(): ITEM


    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }
    /**
     * It is importance to un-reference activity / fragment instance after they are destroyed
     * For situation of configuration changes, activity / fragment will be destroyed and recreated,
     * but view model will survive, so if we don't un-reference them, memory leaks will occur
     */
    @CallSuper
    open fun onDestroyView() {
    }

    @LayoutRes
    public abstract fun layoutRes(): Int

}