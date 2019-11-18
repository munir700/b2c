package co.yap.modules.dashboard.yapit.topup.main.topupamount.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.ITopUpCardSuccess
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.yapcore.BaseState

class TopUpCardSuccessState : BaseState(), ITopUpCardSuccess.State {

    @get:Bindable
    override var toolBarTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarTitle)
        }
    @get:Bindable
    override var buttonTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
        }

    @get:Bindable
    override var topUpSuccess: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.topUpSuccess)
        }

    @get:Bindable
    override var currencyType: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.currencyType)
        }
    @get:Bindable
    override var amount: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.amount)
        }

    override var cardInfo: ObservableField<TopUpCard> = ObservableField(TopUpCard())
}