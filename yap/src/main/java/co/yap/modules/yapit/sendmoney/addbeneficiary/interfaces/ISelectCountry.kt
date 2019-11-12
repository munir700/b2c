package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ISelectCountry {

    interface State : IBase.State {
          fun getCountriesLoadObservable(): SingleLiveEvent<Boolean>
    }

    interface ViewModel : IBase.ViewModel<State> {
         var clickEvent: SingleClickEvent
         fun handlePressOnSeclectCountry(id:Int)
    }

    interface View : IBase.View<ViewModel>
}