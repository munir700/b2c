package co.yap.yapcore.dagger.di.module.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.dagger.di.qualifiers.ActivityContext
import co.yap.yapcore.dagger.di.qualifiers.ActivityFragmentManager
import co.yap.yapcore.dagger.di.qualifiers.ChildFragmentManager
import co.yap.yapcore.helpers.validation.Validator
import dagger.Module
import dagger.Provides


/**
 * Module for fragment component, modified by Duy Pham (reference: Patrick Löwenstein)
 *
 *
 * NOTE: all method must be public (since children module might not in same package,
 * thus dagger can't generate inherit method
 */
@Module
abstract class BaseFragmentModule<in T : Fragment> {

    @Provides
    @ActivityContext
    fun provideContext(fragment: T): Context {
        return fragment.requireContext()
    }

    @Provides
    @ChildFragmentManager
    fun provideChildFragmentManager(fragment: T): FragmentManager {
        return fragment.childFragmentManager
    }

    @Provides
    @ActivityFragmentManager
    fun provideActivityFragmentManager(activity: FragmentActivity): FragmentManager {
        return activity.supportFragmentManager
    }

   /* @Provides
    fun provideBaseActivity(fragment: T): BaseActivity<*> {
        return (fragment as BaseViewModelFragment<*, *, *>).getBaseActivity()
    }*/
    @Provides
    fun provideValidator(): Validator {
        return Validator(null)
    }
    @Provides
    fun provideActivity(fragment: T): FragmentActivity {
        return fragment.requireActivity()
    }

    @Provides
    fun provideLifeCycleOwner(fragment: T): LifecycleOwner {
        return fragment
    }

}
