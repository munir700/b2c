package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import co.yap.yapcore.BaseState

class EmploymentQuestionnaireState : BaseState(), IEmploymentQuestionnaire.State {
    override var ruleValid: Boolean = true
}