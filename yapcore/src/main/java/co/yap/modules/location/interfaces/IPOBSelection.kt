package co.yap.modules.location.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.modules.location.POBCountry
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding


interface IPOBSelection {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getBinding(): FragmentPlaceOfBirthSelectionBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handleOnPressView(id: Int)
        var clickEvent: SingleClickEvent
        var populateSpinnerData: MutableLiveData<List<POBCountry>>
        var countries: ArrayList<POBCountry>
    }

    interface State : IBase.State {
        var cityOfBirth: String
    }
}