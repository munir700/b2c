package co.yap.app.activities

import android.content.Intent
import android.os.Bundle
import co.yap.app.R
import co.yap.modules.dashboard.activities.YapDashboardActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator


class MainActivity : DefaultActivity(), IFragmentHolder, INavigator {

     override val navigator: IBaseNavigator get() = DefaultNavigator(this@MainActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, YapDashboardActivity::class.java))
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

}
