package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.yapcore.BaseState

class SMHomeCountryState: BaseState(), ISMHomeCountry.State {

    @get:Bindable
    override var countryCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.countryCode)

        }

    @get:Bindable
    override var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)

        }

    @get:Bindable
    override var rate: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.rate)

        }

    @get:Bindable
    override var symbol: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.symbol)

        }

    @get:Bindable
    override var time: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)

        }
}