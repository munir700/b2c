package co.yap.modules.location.kyc_additional_info.employment_info.amendment

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
import co.yap.yapcore.enums.EmploymentStatus

interface IEmploymentQuestionnaireAmendment {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun showInfoDialog(title: String, message: String)
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var selectedQuestionItemPosition: Int
        val industrySegmentsList: ArrayList<IndustrySegment>
        var employmentStatus: MutableLiveData<EmploymentStatus>
        var serverEmploymentStatus: EmploymentStatus?
        val selectedBusinessCountries: ObservableField<ArrayList<String>>
        var questionsList: ArrayList<QuestionUiFields>
        var employmentStatusValue: MutableLiveData<EmploymentInfoAmendmentResponse>
        var isInEditMode: MutableLiveData<Boolean>
        var accountActivated : MutableLiveData<Boolean>
        var businessCountriesLiveData: MutableLiveData<ArrayList<String>>
        var countries: ArrayList<Country>
        val documentAdapter : DocumentsAdapter
        var salaryAmount : String?
        var monthlyCreditAmount : String?
        fun handleOnPressView(id: Int)
        fun updateEditMode(isEditable: Boolean)
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

        fun getAllApiCallsInParallelForScreen()

        fun setAnswersForQuestions()

        fun saveEmploymentInfo(
            employmentInfoRequest: EmploymentInfoRequest,
            success: () -> Unit
        )

        fun getEmploymentInfoRequest(
            status: EmploymentStatus
        ): EmploymentInfoRequest

        fun getEmploymentResponse(currentEmploymentStatus : EmploymentStatus) : EmploymentInfoAmendmentResponse?

        fun getDataForPosition(position: Int): QuestionUiFields
        fun getEmploymentTypesList(): MutableList<CoreBottomSheetData>
        fun validateForm()
    }

    interface State : IBase.State {
        var ruleValid: Boolean
        var rightButtonText: String?
    }

}