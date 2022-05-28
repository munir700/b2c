package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class EmploymentQuestionnaireAmendmentState : BaseState(), IEmploymentQuestionnaireAmendment.State {
    override var ruleValid = false
    override var rightButtonText: String? = null
    override var needToShowAdditionalDocumentDialogue: MutableLiveData<Boolean> =
        MutableLiveData(false)
    override var needToShowEmploymentStatusBottomSheet: MutableLiveData<Boolean> =
        MutableLiveData(false)
}