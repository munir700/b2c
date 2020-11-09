package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.library.baseAdapters.BR
import co.yap.yapcore.BaseState

class SMHomeCountryState: BaseState(), ISMHomeCountry.State {

    override var countryCode: ObservableField<String>? = ObservableField()
    override var name: ObservableField<String>? = ObservableField()
    override var rate: ObservableField<String>? = ObservableField()
    override var symbol: ObservableField<String>? = ObservableField()
    override var time: ObservableField<String>? = ObservableField()
    override var flagDrawableResId: ObservableInt? = ObservableInt()
    override var rightButtonText: ObservableField<String> = ObservableField("")

}