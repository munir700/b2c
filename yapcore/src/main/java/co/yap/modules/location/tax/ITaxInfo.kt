package co.yap.modules.location.tax

import androidx.lifecycle.MutableLiveData
import co.yap.modules.location.POBCountry
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.databinding.FragmentTaxInfoBinding

interface ITaxInfo {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getBinding(): FragmentTaxInfoBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handleOnPressView(id: Int)
        var clickEvent: SingleClickEvent
        var populateSpinnerData: MutableLiveData<List<POBCountry>>
        var countries: ArrayList<POBCountry>
        var selectedCountry: POBCountry?
    }

    interface State : IBase.State {
        var cityOfBirth: String
        var valid: Boolean
    }
}