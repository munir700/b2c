package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class SMHomeCountryState : BaseState(), ISMHomeCountry.State {

    override var countryCode: ObservableField<String>? = ObservableField()
    override var name: ObservableField<String>? = ObservableField()
    override var rate: ObservableField<String>? = ObservableField()
    override var symbol: ObservableField<String>? = ObservableField()
    override var time: ObservableField<String>? = ObservableField()
    override var rightButtonText: ObservableField<String> = ObservableField("")
    override var isNoRecentsBeneficiries: ObservableBoolean = ObservableBoolean()
    override var isRecentsVisible: ObservableBoolean = ObservableBoolean(false)

}