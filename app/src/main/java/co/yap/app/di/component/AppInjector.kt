package co.yap.app.di.component

/*import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import co.yap.app.AAPApplication
import co.yap.yapcore.dagger.di.components.Injectable
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector*/

/**
 * Helper class to automatically inject fragments if they implement [Injectable].
 */
object AppInjector /*: HouseHoldComponentProvider, CoreComponentProvider, YapComponentProvider*/ {
    /* private lateinit var coreComponent: CoreComponent
     private lateinit var holdComponent: HouseHoldComponent
     private lateinit var yapComponent: YapComponent*/
  /*  fun init(application: AAPApplication): AppComponent {
        val component = DaggerAppComponent.builder()
            .application(application)
            *//* .coreComponent(provideCoreComponent(application))
             .houseHoldComponent(provideHouseHoldComponent())
             .yapComponent(provideYapComponent())*//*
            .build()
        component.inject(application)

        // handle injection for all activities created
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })

        return component
    }*/

  /*  private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector || activity is Injectable) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager, f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }*/

/*
    override fun provideYapComponent(): YapComponent {
        if (!this::yapComponent.isInitialized) {
            yapComponent = DaggerYapComponent
                .builder().coreComponent(coreComponent)
                .build()
        }
        return yapComponent
    }

    override fun provideHouseHoldComponent(): HouseHoldComponent {
        if (!this::holdComponent.isInitialized) {
            holdComponent = DaggerHouseHoldComponent
                .builder().coreComponent(coreComponent)
                .build()
        }
        return holdComponent
    }


    override fun provideCoreComponent(application: DaggerApplication): CoreComponent {
        if (!this::coreComponent.isInitialized) {
            coreComponent = DaggerCoreComponent
                .builder().application(application)
                .build()
        }
        return coreComponent
    }
*/
}
