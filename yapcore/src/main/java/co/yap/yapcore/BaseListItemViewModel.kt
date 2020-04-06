package co.yap.yapcore

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.PropertyChangeRegistry
import androidx.navigation.NavController
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.dagger.base.viewmodel.DaggerViewModel

/**
 * Created by Muhammad Irfan Arshad
 *
 */
abstract class BaseListItemViewModel<ITEM : Any> : DaggerViewModel(), OnItemClickListener {
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    private var isFirstTimeUiCreate = true

    abstract fun setItem(item: ITEM, position: Int)
    abstract fun getItem(): ITEM

    /**
     * called after fragment / activity is created with input bundle arguments
     *
     * @param bundle argument dataList
     */
    @CallSuper
    open fun onCreate(bundle: Bundle?, navigation: NavController?) {
        if (isFirstTimeUiCreate) {
            onFirsTimeUiCreate(bundle, navigation)
            isFirstTimeUiCreate = false
        }
    }

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