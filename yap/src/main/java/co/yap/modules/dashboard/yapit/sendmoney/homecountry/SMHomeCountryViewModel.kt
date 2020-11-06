package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.app.Application
import co.yap.R
import co.yap.yapcore.BaseViewModel

class SMHomeCountryViewModel (application: Application) : BaseViewModel<ISMHomeCountry.State>(application), ISMHomeCountry.ViewModel {
    override val state: ISMHomeCountry.State = SMHomeCountryState()

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = getString(R.string.screen_send_money_home_title)
    }

}