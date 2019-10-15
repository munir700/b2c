package co.yap.modules.dashboard.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ISelectCountry {

    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
         var clickEvent: SingleClickEvent
         fun handlePressOnAddNow()
    }

    interface View : IBase.View<ViewModel>
}