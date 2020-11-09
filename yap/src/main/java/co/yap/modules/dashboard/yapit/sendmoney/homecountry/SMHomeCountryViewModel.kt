package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.app.Application
import android.view.View
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyBaseViewMode

class SMHomeCountryViewModel (application: Application) : SendMoneyBaseViewMode<ISMHomeCountry.State>(application), ISMHomeCountry.ViewModel {
    override val state: SMHomeCountryState = SMHomeCountryState()

    override fun onCreate() {
        super.onCreate()
        setToolBarTitle(getString(R.string.screen_send_money_home_title))
        setRightText(getString(R.string.screen_send_money_home_display_text_compare))

        state.name?.set("Canada")
        state.countryCode?.set("")
        state.rate?.set("0.357014")
        state.symbol?.set("CAD")
        state.time?.set("04/10/2020, 2:30 PM")
    }
}