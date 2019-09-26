package co.yap.modules.dashboard.changeemailmore.activities

import android.os.Bundle
import co.yap.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class ChangeEmailActivity : DefaultActivity(), INavigator,IFragmentHolder {
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.change_email_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)
    }
}