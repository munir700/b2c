package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import androidx.databinding.ObservableField
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.enums.EmploymentStatus.*


interface ComplianceQuestionsItemsComposer {
    fun compose(
        employmentStatus: EmploymentStatus,
        status: EmploymentInfoAmendmentResponse?
    ): ArrayList<QuestionUiFields>
}

class KYCComplianceComposer :
    ComplianceQuestionsItemsComposer {
    override fun compose(
        employmentStatus: EmploymentStatus,
        status: EmploymentInfoAmendmentResponse?
    ): ArrayList<QuestionUiFields> {
        return when (employmentStatus) {
            EMPLOYED -> arrayListOf(
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Tell us where you work?",
                        placeholder = "Employer name",
                        questionType = QuestionType.EDIT_TEXT_FIELD,
                        answer = ObservableField(status?.employerName ?: ""),
                        previousValue = ObservableField(status?.employerName ?: ""),
                        tag = "EmploymentName"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your monthly salary? Don’t worry there is no minimum salary requirement.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.monthlySalary ?: ""),
                        previousValue = ObservableField(status?.monthlySalary ?: ""),
                        tag = "MonthlySalary"
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit or receive monthly in a cash deposit machine (ATM)? If you don’t deal with cash, then enter AED 0.00",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.expectedMonthlyCredit ?: ""),
                        previousValue = ObservableField(status?.expectedMonthlyCredit ?: ""),
                        tag = "CashDeposit"
                    ),
                    key = EmploymentQuestionIdentifier.DEPOSIT_AMOUNT
                )

            )
            SALARIED_AND_SELF_EMPLOYED, SELF_EMPLOYED -> arrayListOf(
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Tell us the name of your company?",
                        placeholder = "Company name",
                        questionType = QuestionType.EDIT_TEXT_FIELD,
                        answer = ObservableField(status?.companyName ?: ""),
                        previousValue = ObservableField(status?.companyName ?: ""),
                        tag = "CompanyName"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Add an industry segment:",
                        placeholder = "Select industry segment",
                        questionType = QuestionType.DROP_DOWN_FIELD,
                        answer = ObservableField(),
                        previousValue = ObservableField(""),
                        tag = "IndustrySegment"
                    ),
                    key = EmploymentQuestionIdentifier.INDUSTRY_SEGMENT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "Add all the countries your company does business with",
                        placeholder = "Search countries",
                        questionType = QuestionType.COUNTRIES_FIELD,
                        answer = ObservableField(),
                        previousValue = ObservableField(""),
                        tag = "CompanyNameListWhereCompanyDoesBusiness"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your monthly salary? Don’t worry there is no minimum salary requirement.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.monthlySalary ?: ""),
                        previousValue = ObservableField(status?.monthlySalary ?: ""),
                        tag = "MonthlySalary"
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit or receive monthly in a cash deposit machine (ATM)? If you don’t deal with cash, then enter AED 0.00",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.expectedMonthlyCredit ?: ""),
                        previousValue = ObservableField(status?.expectedMonthlyCredit ?: ""),
                        tag = "CashDeposit"
                    ),
                    key = EmploymentQuestionIdentifier.DEPOSIT_AMOUNT
                )

            )
            OTHER -> arrayListOf(
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Which of the following statements describes you best?",
                        placeholder = "Select from list",
                        questionType = QuestionType.DROP_DOWN_FIELD,
                        answer = ObservableField(),
                        previousValue = ObservableField(""),
                        tag = ""
                    ), key = EmploymentQuestionIdentifier.EMPLOYMENT_TYPE
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Please enter the name of your sponsor",
                        placeholder = "Enter here",
                        questionType = QuestionType.EDIT_TEXT_FIELD,
                        answer = ObservableField(status?.sponsorName ?: ""),
                        previousValue = ObservableField(status?.sponsorName ?: ""),
                        tag = ""
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your monthly salary? Don’t worry there is no minimum salary requirement.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.monthlySalary ?: ""),
                        previousValue = ObservableField(status?.monthlySalary ?: ""),
                        tag = "MonthlySalary"
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit or receive monthly in a cash deposit machine (ATM)? If you don’t deal with cash, then enter AED 0.00",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.expectedMonthlyCredit ?: ""),
                        previousValue = ObservableField(status?.expectedMonthlyCredit ?: ""),
                        tag = "CashDeposit"
                    ), key = EmploymentQuestionIdentifier.DEPOSIT_AMOUNT
                )
            )
            NONE -> TODO()
        }
    }
}