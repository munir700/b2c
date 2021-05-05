package co.yap.modules.dashboard.transaction.totalpurchases

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.yapcore.BaseState

class TotalPurchaseState : BaseState(), ITotalPurchases.State {

    override var countWithDate: ObservableField<String> = ObservableField()
    override var totalSpendings: ObservableField<String> = ObservableField("Spendings")
    override var ImageUrl: ObservableField<String> = ObservableField()

    @get:Bindable
    override var position: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
}