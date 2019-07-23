package co.yap.app.activities

import android.os.Bundle
import co.yap.app.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator


class MainActivity : DefaultActivity(), INavigator, IFragmentHolder {

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@MainActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
