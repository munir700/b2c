package co.yap.app.di

import android.os.Bundle
import co.yap.app.di.component.DaggerActivityComponent
import co.yap.app.di.module.ActivityModule
import co.yap.yapcore.BaseActivity

abstract class CoreActivity : BaseActivity() {
    lateinit var mActivityComponent: DaggerActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performInjection()
    }

    override fun performInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build() as DaggerActivityComponent
    }
}