package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ISelectCountry {

    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry.State> {
         var clickEvent: SingleClickEvent
         fun handlePressOnAddNow()
    }

    interface View : IBase.View<co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry.ViewModel>
}