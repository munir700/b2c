package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import androidx.databinding.ObservableField
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.enums.EmploymentStatus.*
import co.yap.yapcore.enums.SystemConfigurations
import co.yap.yapcore.managers.SessionManager


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
                        questionTitle = "Who is your employer?",
                        placeholder = "Employer name",
                        questionType = QuestionType.EDIT_TEXT_FIELD,
                        answer = ObservableField(status?.employerName ?: ""),
                        previousValue = ObservableField(status?.employerName),
                        tag = "EmploymentName"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your total monthly income?",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(
                            status?.monthlySalary ?: SessionManager.systemConfiguration.value?.get(
                                SystemConfigurations.DEFAULT_SALARY.key
                            )?.value
                        ),
                        previousValue = ObservableField(status?.monthlySalary),
                        tag = "MonthlySalary",
                        minimumValue = SessionManager.systemConfiguration.value?.get(
                            SystemConfigurations.MINIMUM_SALARY.key
                        )?.value
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit/receive on a monthly basis in cash deposit machine (ATM)? Please adjust amount as required - it should be less then your income.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(
                            status?.expectedMonthlyCredit
                                ?: SessionManager.systemConfiguration.value?.get(
                                    SystemConfigurations.DEFAULT_CASH_DEPOSIT.key
                                )?.value
                        ),
                        previousValue = ObservableField(status?.expectedMonthlyCredit),
                        tag = "CashDeposit",
                        minimumValue = SessionManager.systemConfiguration.value?.get(
                            SystemConfigurations.MINIMUM_CASH_DEPOSIT.key
                        )?.value
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
                        previousValue = ObservableField(status?.companyName),
                        tag = "CompanyName"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Type of self-employment:",
                        placeholder = "Select Type",
                        questionType = QuestionType.DROP_DOWN_FIELD,
                        answer = ObservableField(status?.typeOfSelfEmployment ?: ""),
                        previousValue = ObservableField(status?.typeOfSelfEmployment ?: ""),
                        tag = "typeOfSelfEmployment"
                    ),
                    key = EmploymentQuestionIdentifier.SELF_EMPLOYMENT
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Add an industry segment:",
                        placeholder = "Select industry segment",
                        questionType = QuestionType.DROP_DOWN_FIELD,
                        answer = ObservableField(),
                        previousValue = ObservableField(),
                        tag = "IndustrySegment"
                    ),
                    key = EmploymentQuestionIdentifier.INDUSTRY_SEGMENT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "Add all the countries your company does business with",
                        placeholder = "Search countries",
                        questionType = QuestionType.COUNTRIES_FIELD,
                        answer = ObservableField(),
                        previousValue = ObservableField(),
                        tag = "CompanyNameListWhereCompanyDoesBusiness"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your total monthly income?",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(
                            status?.monthlySalary ?: SessionManager.systemConfiguration.value?.get(
                                SystemConfigurations.DEFAULT_SALARY.key
                            )?.value
                        ),
                        previousValue = ObservableField(status?.monthlySalary),
                        tag = "MonthlySalary",
                        minimumValue = SessionManager.systemConfiguration.value?.get(
                            SystemConfigurations.MINIMUM_SALARY.key
                        )?.value
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit/receive on a monthly basis in cash deposit machine (ATM)? Please adjust amount as required - it should be less then your income.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(
                            status?.expectedMonthlyCredit
                                ?: SessionManager.systemConfiguration.value?.get(
                                    SystemConfigurations.DEFAULT_CASH_DEPOSIT.key
                                )?.value
                        ),
                        previousValue = ObservableField(status?.expectedMonthlyCredit),
                        tag = "CashDeposit",
                        minimumValue = SessionManager.systemConfiguration.value?.get(
                            SystemConfigurations.MINIMUM_CASH_DEPOSIT.key
                        )?.value
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
                        answer = ObservableField(status?.employmentTypeValue ?: ""),
                        previousValue = ObservableField(status?.employmentTypeValue ?: ""),
                        tag = "EmploymentStatus"
                    ), key = EmploymentQuestionIdentifier.EMPLOYMENT_TYPE
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Please enter the name of your sponsor",
                        placeholder = "Enter here",
                        questionType = QuestionType.EDIT_TEXT_FIELD,
                        answer = ObservableField(status?.sponsorName ?: ""),
                        previousValue = ObservableField(status?.sponsorName),
                        tag = "EmploymentName"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "Please answer these questions with the most accurate information that represents your expected transaction activity.",
                        placeholder = "",
                        questionType = QuestionType.DISPLAY_TEXT,
                        answer = ObservableField("no answer needed"),
                        previousValue = ObservableField("no answer needed"),
                        tag = "textField"
                    )
                ),
                QuestionUiFields(
                    question = Question(
                        questionTitle = "What is your total monthly income?",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.monthlySalary ?: "5000"),
                        previousValue = ObservableField(status?.monthlySalary),
                        tag = "MonthlySalary",
                        minimumValue = "0"
                    ),
                    key = EmploymentQuestionIdentifier.SALARY_AMOUNT
                ), QuestionUiFields(
                    question = Question(
                        questionTitle = "How much cash do you plan to deposit/receive on a monthly basis in cash deposit machine (ATM)? Please adjust amount as required - it should be less then your income.",
                        placeholder = "Enter the amount",
                        questionType = QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT,
                        answer = ObservableField(status?.expectedMonthlyCredit ?: "4000"),
                        previousValue = ObservableField(status?.expectedMonthlyCredit),
                        tag = "CashDeposit",
                        minimumValue = "0"
                    ), key = EmploymentQuestionIdentifier.DEPOSIT_AMOUNT
                )
            )
            NONE -> {
                //TODO()
                arrayListOf()
            }

        }
    }
}