package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.app.Application
import android.view.View
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.EmploymentQuestionnaireAdaptor
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.interfaces.OnItemClickListener

class EmploymentQuestionnaireViewModel(application: Application) :
    LocationChildViewModel<IEmploymentQuestionnaire.State>(application),
    IEmploymentQuestionnaire.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val questionnaireAdaptor: EmploymentQuestionnaireAdaptor =
        EmploymentQuestionnaireAdaptor(
            arrayListOf()
        )
    override val state: IEmploymentQuestionnaire.State =
        EmploymentQuestionnaireState()

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun questionnaires(forStatus: EmploymentStatus): ArrayList<QuestionUiFields> {
        val questionnairesComposer: ComplianceQuestionsItemsComposer = KYCComplianceComposer()
        return questionnairesComposer.compose(forStatus)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.etTinNumber -> { // on tin number change
                }
            }
        }
    }
}