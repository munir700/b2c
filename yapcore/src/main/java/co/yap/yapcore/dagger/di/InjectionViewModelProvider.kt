package co.yap.yapcore.dagger.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject
import kotlin.reflect.KClass

typealias ViewModelInjectionField<T> = dagger.Lazy<T>

class InjectionViewModelProvider<VM : DaggerBaseViewModel<*>> @Inject constructor(
    private val lazyViewModel: dagger.Lazy<VM>
) {

    @Suppress("UNCHECKED_CAST")
    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) = lazyViewModel.get() as T
    }

    fun <ACTIVITY : FragmentActivity> get(activity: ACTIVITY, viewModelClass: KClass<VM>) =
        ViewModelProviders.of(activity, viewModelFactory).get(viewModelClass.java)

    fun <FRAGMENT : Fragment> get(fragment: FRAGMENT, viewModelClass: KClass<VM>) =
        ViewModelProviders.of(fragment, viewModelFactory).get(viewModelClass.java)


}