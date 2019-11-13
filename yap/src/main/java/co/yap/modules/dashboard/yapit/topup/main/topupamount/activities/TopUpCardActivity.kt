package co.yap.modules.dashboard.yapit.topup.main.topupamount.activities

import android.os.Bundle
import co.yap.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class TopUpCardActivity : DefaultActivity(), INavigator, IFragmentHolder {
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.card_top_up_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up_card)
    }


}