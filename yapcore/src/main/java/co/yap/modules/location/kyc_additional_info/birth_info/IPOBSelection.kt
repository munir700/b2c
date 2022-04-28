package co.yap.modules.location.kyc_additional_info.birth_info

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding
import co.yap.yapcore.interfaces.OnItemClickListener


interface IPOBSelection {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getBinding(): FragmentPlaceOfBirthSelectionBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handleOnPressView(id: Int)
        fun saveDOBInfo(success: () -> Unit)
        var clickEvent: SingleClickEvent
        var populateSpinnerData: MutableLiveData<ArrayList<Country>>
        var populateCitiesSpinnerData: MutableLiveData<ArrayList<String>>
        fun getAllCountries()
        fun getAllCities()
        val dualNatioanlitySpinnerItemClickListener: OnItemClickListener
        val dualNationalityQuestionOptions: ArrayList<String>
        fun canSkipFragment(): Boolean
        fun getAmendmentsBirthInfo()
        fun isFromAmendment(): Boolean
        fun validateForm()
    }

    interface State : IBase.State {
        var cityOfBirth: ObservableField<String>
        var valid: ObservableField<Boolean>
        var selectedCountry: ObservableField<Country?>
        var selectedCity: ObservableField<String?>
        var selectedSecondCountry: ObservableField<Country?>
        var eidNationality: ObservableField<String>
        var isDualNational: ObservableBoolean
        var dualNationalityOption: MutableLiveData<Int>
        var previousSelectedCountry: ObservableField<String?>
        var previousCityOfBirth: ObservableField<String?>
        var previousEidNationality: ObservableField<String?>
        var previousSelectedSecondCountry: ObservableField<String?>
        fun validate()
    }
}
