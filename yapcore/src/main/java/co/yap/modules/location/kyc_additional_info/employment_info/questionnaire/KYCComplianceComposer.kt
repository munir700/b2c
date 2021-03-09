package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import androidx.databinding.ObservableField
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.enums.EmploymentStatus.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader


interface ComplianceQuestionsItemsComposer {
    fun compose(employmentStatus: EmploymentStatus): ArrayList<QuestionUiFields>
}

class KYCComplianceComposer : ComplianceQuestionsItemsComposer {
    override fun compose(employmentStatus: EmploymentStatus): ArrayList<QuestionUiFields> {
        return when (employmentStatus) {
            EMPLOYED -> arrayListOf(
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Tell us where you work?",
                        placeholder = "Employer name",
                        questionType = QuestionType.COUNTRIES_FIELD,
                        answer = ObservableField()
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your monthly salary? Don’t worry there is no minimum salary requirement.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField()
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit or receive monthly in a cash deposit machine (ATM)? If you don’t deal with cash, then enter AED 0.00",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField()
                    ),
                    key = EmploymentQuestionIdentifier.DEPOSIT_AMOUNT
                )

            )
            SELF_EMPLOYED -> TODO()
            SALARIED_AND_SELF_EMPLOYED -> TODO()
            OTHER -> arrayListOf(
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Which of the following statements describes you best?",
                        placeholder = "Select from list",
                        questionType = QuestionType.DROP_DOWN_FIELD,
                        answer = ObservableField()
                    )
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your monthly salary? Don’t worry there is no minimum salary requirement.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField()
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit or receive monthly in a cash deposit machine (ATM)? If you don’t deal with cash, then enter AED 0.00",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField()
                    ),
                    key = EmploymentQuestionIdentifier.DEPOSIT_AMOUNT
                )
            )
        }
    }

    /*private fun getQuestionnaire(): CoreBottomSheetData {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<QuestionnaireItem>() {}.type

        return gson.fromJson<QuestionnaireItem>(readJsonFile(), itemType)
    }

    @Throws(IOException::class)
    private fun readJsonFile(): String? {
        val br =
            BufferedReader(InputStreamReader(FileInputStream("../yapcore/src/main/assets/jsons/employment_questionaires.json")))
        val sb = StringBuilder()
        var line: String? = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }*/
}