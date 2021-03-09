package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.enums.EmploymentQuestionIdentifier

data class QuestionUiFields(
    val key: EmploymentQuestionIdentifier? = null,
    val question: Question,
    val isFocusInput: ObservableBoolean = ObservableBoolean(false)
)