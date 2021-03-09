package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models

import androidx.databinding.ObservableBoolean

data class QuestionUiFields(
    val key: String? = null,
    val question: Question,
    val isFocusInput: ObservableBoolean = ObservableBoolean(false)
)