package co.yap.billpayments.dashboard.analytics

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.BR
import co.yap.yapcore.BaseState

class BillPaymentAnalyticsState : BaseState(), IBillPaymentAnalytics.State {

    override var previousMonth: ObservableBoolean = ObservableBoolean()
    override var nextMonth: ObservableBoolean = ObservableBoolean()
    override var displayMonth: ObservableField<String> = ObservableField()
    override var isNTRYShow: ObservableBoolean = ObservableBoolean()

    @get:Bindable
    override var selectedMonth: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedMonth)
        }

    @get:Bindable
    override var monthCount: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.monthCount)
        }

    @get:Bindable
    override var selectedItemSpentValue: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemSpentValue)
        }

    @get:Bindable
    override var selectedItemName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemName)
        }

    @get:Bindable
    override var selectedItemPosition: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemPosition)
        }

    @get:Bindable
    override var totalSpent: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalSpent)
        }


}
