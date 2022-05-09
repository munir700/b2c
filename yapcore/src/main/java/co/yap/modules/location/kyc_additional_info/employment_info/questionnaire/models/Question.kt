package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models

import androidx.databinding.ObservableField
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType

data class Question(
    val questionTitle: String,
    val placeholder: String?,
    val questionType: QuestionType,
    val answer: ObservableField<String>,
    var previousValue: ObservableField<String?>,
    val multipleAnswers: ObservableField<ArrayList<String>> = ObservableField(arrayListOf()),
    var multiplePreviousAnswers: ObservableField<ArrayList<String>> = ObservableField(arrayListOf()),
    val tag: String?,
    val minimumValue: String? = null
)