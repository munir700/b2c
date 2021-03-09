package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import androidx.databinding.ObservableField
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.EmploymentQuestionnaireAdaptor
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentStatus

interface IEmploymentQuestionnaire {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getBinding(): FragmentEmploymentQuestionnaireBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val questionnaireAdaptor: EmploymentQuestionnaireAdaptor
        fun handleOnPressView(id: Int)
        fun questionnaires(forStatus: EmploymentStatus): ArrayList<Question>
    }

    interface State : IBase.State {
        var valid: ObservableField<Boolean>
    }

}