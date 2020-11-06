package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.app.Application
import co.yap.yapcore.BaseViewModel

class SMHomeCountryViewModel (application: Application) : BaseViewModel<ISMHomeCountry.State>(application), ISMHomeCountry.ViewModel {
    override val state: ISMHomeCountry.State = SMHomeCountryState()

}