package co.yap.billpayments.billers.search

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.BaseState

class BillerSearchState : BaseState(), IBillerSearch.State {
    override var nextButtonEnabled: ObservableBoolean = ObservableBoolean()
}
