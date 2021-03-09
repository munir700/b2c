package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.content.Context
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.EmploymentQuestionnaireAdaptor
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.EmploymentSegment
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.interfaces.OnItemClickListener

interface IEmploymentQuestionnaire {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getBinding(): FragmentEmploymentQuestionnaireBinding
        fun onInfoClick(questionUiFields: QuestionUiFields)
        fun showInfoDialog(title: String, message: String)
        fun setBusinessCountries(countries: ArrayList<String>, position: Int)
        fun getSelectedStateCountries(countries: ArrayList<Country>): List<Country>
        fun launchBottomSheetSegment(
            itemClickListener: OnItemClickListener? = null,
            label: String = "Select which statement describes your situation best:",
            viewType: Int = Constants.VIEW_WITHOUT_FLAG,
            employmentSegments: List<EmploymentSegment>? = null
        )
        fun parseSegment(
            context: Context,
            employmentSegments: java.util.ArrayList<EmploymentSegment>
        ): java.util.ArrayList<EmploymentSegment>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val questionnaireAdaptor: EmploymentQuestionnaireAdaptor
        fun handleOnPressView(id: Int)
        fun questionnaires(forStatus: EmploymentStatus): ArrayList<QuestionUiFields>
        fun getEmploymentType(): List<EmploymentSegment>
        fun employeeSegment(): List<EmploymentSegment>
    }

    interface State : IBase.State {
        var valid: ObservableField<Boolean>
    }

}