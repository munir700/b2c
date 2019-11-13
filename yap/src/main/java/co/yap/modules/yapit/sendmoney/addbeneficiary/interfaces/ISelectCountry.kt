package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ISelectCountry {

    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnSeclectCountry(id: Int)
        var countries: List<Country>?
        val populateSpinnerData: MutableLiveData<List<Country>>

    }

    interface View : IBase.View<ViewModel> {
        fun setUpSpinner()
    }
}