package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import co.yap.yapcore.BaseState

class EmploymentQuestionnaireAmendmentState : BaseState(), IEmploymentQuestionnaireAmendment.State {
    override var ruleValid = true
    override var rightButtonText: String? = null
}