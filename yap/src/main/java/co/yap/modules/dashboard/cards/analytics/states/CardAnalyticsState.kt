package co.yap.modules.dashboard.cards.analytics.states

import android.app.Application
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.app.YAPApplication
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
    override var currencyType: String? = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyType)
        }
    @get:Bindable
    override var monthlyAvgAmount: String?=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.monthlyAvgAmount)
        }

    fun setUpString(currencyType: String?, amount: String?) {
        monthlyAverageString =
            Translator.getString(context, Strings.screen_card_analytics_display_month_average_text)
                .format(currencyType, amount)
    }
}