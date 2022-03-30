package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.EmploymentType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.requestdtos.EmploymentInfoRequest
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
import co.yap.networking.customers.responsedtos.employmentinfo.IndustrySegment
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.ButtonType

interface IEmploymentQuestionnaire {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getBinding(): FragmentEmploymentQuestionnaireBinding
      /*  fun showInfoDialog(
            title: String,
            message: String,
            buttonTypes: ArrayList<ButtonType>,
            cb: (view: android.view.View) -> Unit
        )*/
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var selectedQuestionItemPosition: Int
        val industrySegmentsList: ArrayList<IndustrySegment>
        var employmentStatus: EmploymentStatus
        val selectedBusinessCountries: ObservableField<ArrayList<String>>
        var questionsList: ArrayList<QuestionUiFields>
        var employmentStatusValue: MutableLiveData<EmploymentInfoAmendmentResponse>
        var businessCountriesLiveData: MutableLiveData<ArrayList<String>>
        fun handleOnPressView(id: Int)
        fun questionnaires(
            forStatus: EmploymentStatus,
            defaultValue: EmploymentInfoAmendmentResponse?
        ): ArrayList<QuestionUiFields>

        fun employmentTypes(): MutableList<EmploymentType>
        fun getSelectedStateCountries(countries: ArrayList<Country>): List<Country>
        fun setBusinessCountries(
            lyCountries: android.view.View,
            countries: ArrayList<String>,
            position: Int
        )

        fun parseEmploymentTypes(employmentTypes: MutableList<EmploymentType>): MutableList<CoreBottomSheetData>
        fun parseSegments(segments: MutableList<IndustrySegment>): MutableList<CoreBottomSheetData>
        fun onInfoClick(
            questionUiFields: QuestionUiFields,
            callBack: (title: String, message: String) -> Unit
        )

        fun getCountriesAndSegments(
            businessCountries: ArrayList<String>? = null,
            segmentCode: String? = null
        )

        fun isDataRequiredFromApi(
            forStatus: EmploymentStatus,
            businessCountries: ArrayList<String>? = null,
            segmentCode: String? = null
        )

        fun saveEmploymentInfo(
            employmentInfoRequest: EmploymentInfoRequest,
            success: () -> Unit
        )

        fun getEmploymentInfoRequest(
            status: EmploymentStatus
        ): EmploymentInfoRequest

        fun getDataForPosition(position: Int): QuestionUiFields
        fun hasAmendmentMap(): Boolean
        fun getAmendmentsEmploymentInfo()
        fun isFromAmendment(): Boolean
        fun hasKeyInAmendmentMap(key: String?): Boolean
        fun validateForm()
        fun selfEmploymentTypes(): MutableList<EmploymentType>

    }

    interface State : IBase.State {
        var ruleValid: Boolean
    }

}