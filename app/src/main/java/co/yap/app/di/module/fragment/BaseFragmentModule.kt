package co.yap.app.di.module.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.di.qualifiers.ActivityContext
import co.yap.yapcore.dagger.di.qualifiers.ActivityFragmentManager
import co.yap.yapcore.dagger.di.qualifiers.ChildFragmentManager
import dagger.Module
import dagger.Provides

@Module
abstract class BaseFragmentModule<in T : Fragment> {

    @Provides
    @ActivityContext
    fun provideContext(fragment: T): Context? {
        return fragment.context
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

    @Provides
    fun provideBaseActivity(fragment: T): BaseActivity<*> {
        return (fragment as BaseViewModelFragment<*, *, *>).getBaseActivity()
    }

    @Provides
    fun provideActivity(fragment: T): FragmentActivity? {
        return fragment.activity
    }

    @Provides
    fun provideLifeCycleOwner(fragment: T): LifecycleOwner {
        return fragment
    }



    //    @Provides
    //    public SimpleGlideLoader provideDefaultGlide(T fragment) {
    //        return new SimpleGlideLoader(fragment);
    //    }
    //
    //    @Provides
    //    public AvatarLoader provideAvatarLoader(T fragment) {
    //        return new AvatarLoader(fragment);
    //    }
}
