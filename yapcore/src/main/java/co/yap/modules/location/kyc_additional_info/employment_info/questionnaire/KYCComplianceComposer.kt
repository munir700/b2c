package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import androidx.databinding.ObservableField
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.enums.EmploymentStatus.*


interface ComplianceQuestionsItemsComposer {
    fun compose(employmentStatus: EmploymentStatus): ArrayList<Question>
}

class KYCComplianceComposer : ComplianceQuestionsItemsComposer {
    override fun compose(employmentStatus: EmploymentStatus): ArrayList<Question> {
        return when (employmentStatus) {
            EMPLOYED -> arrayListOf(
                Question(
                    question = "Tell us where you work?",
                    placeholder = "Employer name",
                    questionType = QuestionType.EDIT_TEXT_FIELD,
                    answer = ObservableField()
                ),
                Question(
                    question = "What is your monthly salary? Don’t worry there is no minimum salary requirement.",
                    placeholder = "Enter the amount",
                    questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                    answer = ObservableField()
                ),
                Question(
                    question = "How much cash do you plan to deposit or receive monthly in a cash deposit machine (ATM)? If you don’t deal with cash, then enter AED 0.00",
                    placeholder = "Enter the amount",
                    questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                    answer = ObservableField()
                )
            )
            SELF_EMPLOYED -> TODO()
            SALARIED_AND_SELF_EMPLOYED -> TODO()
            OTHER -> TODO()
        }
    }
}