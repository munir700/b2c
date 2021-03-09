package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.app.Application
import android.view.View
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.EmploymentQuestionnaireAdaptor
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.EmploymentSegment
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

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

    override fun onCreate() {
        super.onCreate()
    }

    override fun onResume() {
        super.onResume()
        setProgress(95)
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun questionnaires(forStatus: EmploymentStatus): ArrayList<QuestionUiFields> {
        val questionnairesComposer: ComplianceQuestionsItemsComposer = KYCComplianceComposer()
        return questionnairesComposer.compose(forStatus)
    }

    override fun employeeSegment(): List<EmploymentSegment> {
        val gson = GsonBuilder().create();
        val itemType = object : TypeToken<List<EmploymentSegment>>() {}.type
        return gson.fromJson<List<EmploymentSegment>>(
            context.getJsonDataFromAsset(
                "jsons/employment_describe_you_best.json"
            ), itemType
        )
    }

    override fun getEmploymentType(): List<EmploymentSegment> {
        val gson = GsonBuilder().create();
        val itemType = object : TypeToken<List<EmploymentSegment>>() {}.type
        return gson.fromJson<List<EmploymentSegment>>(
            context.getJsonDataFromAsset(
                "jsons/employment_describe_you_best.json"
            ), itemType
        )
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.etQuestionEditText -> {
                }
            }
        }
    }
}
