package co.yap.yapcore.dagger.di.module.activity

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import co.yap.yapcore.dagger.di.qualifiers.ActivityContext
import co.yap.yapcore.dagger.di.qualifiers.ActivityFragmentManager
import dagger.Module
import dagger.Provides


@Module
abstract class BaseActivityModule<in T : AppCompatActivity> {

    @Provides
    @ActivityContext
    fun provideContext(activity: T): Context {
        return activity
    }

    @Provides
    @ActivityContext
    fun provideActivity(activity: T): Activity {
        return activity
    }


    @Provides
    @ActivityFragmentManager
    fun provideFragmentManager(activity: T): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    fun provideLifeCycleOwner(activity: T): LifecycleOwner {
        return activity
    }
}
