package co.yap.modules.kyc.amendments.passport

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import java.io.File
import java.util.*

class PassportAmendmentState : BaseState(), IPassportAmendment.State {
    override var issueDataCalender: Calendar? = Calendar.getInstance()
    override var expireDataCalender: Calendar? = Calendar.getInstance()
    override var passportNumber: MutableLiveData<String> = MutableLiveData("")
    override var issueDate: MutableLiveData<String> = MutableLiveData("")
    override var expireDate: MutableLiveData<String> = MutableLiveData("")
    override var mFile: MutableLiveData<File?> = MutableLiveData(null)
}