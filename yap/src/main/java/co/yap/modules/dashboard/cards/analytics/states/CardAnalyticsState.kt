package co.yap.modules.dashboard.cards.analytics.states

import android.app.Application
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalytics
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState

class CardAnalyticsState(application: Application) : BaseState(), ICardAnalytics.State {
    val context = application.applicationContext
    @get:Bindable
    override var monthlyAverageString: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.monthlyAverageString)
        }
    @get:Bindable
    override var currencyType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyType)
        }
    @get:Bindable
    override var selectedItemSpentValue: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemSpentValue)
        }
    @get:Bindable
    override var selectedItemPercentage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemPercentage)
        }
    @get:Bindable
    override var selectedItemName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemName)
        }
    @get:Bindable
    override var selectedItemPosition: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemPosition)
        }
    fun setUpString(currencyType: String, amount: String) {
        monthlyAverageString =
            Translator.getString(context, Strings.screen_card_analytics_display_month_average_text)
                .format(currencyType, amount)
    }


}