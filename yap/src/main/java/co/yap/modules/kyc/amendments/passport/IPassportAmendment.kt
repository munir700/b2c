package co.yap.modules.kyc.amendments.passport

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.PassportRequest
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.io.File
import java.util.*

class IPassportAmendment {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        fun getUploadDocumentOptions(isShowRemovePhoto: Boolean): ArrayList<BottomSheetItem>
        fun getCustomerDocuments(accountUuid: String?)
        fun uploadPassportAmendments(passportRequest: PassportRequest)
        fun getDatePicker(
            currentCalendar: Calendar?,
            minCalendar: Calendar? = Calendar.getInstance(),
            maxCalendar: Calendar? = Calendar.getInstance(),
            callBack: DatePickerDialog.OnDateSetListener
        ): DatePickerDialog
    }

    interface State : IBase.State {
        var issueDataCalender: Calendar?
        var expireDataCalender: Calendar?
        var passportNumber: MutableLiveData<String>
        var issueDate: MutableLiveData<String>
        var expireDate: MutableLiveData<String>
        var previousPassportNumber: MutableLiveData<String>
        var previousIssueDate: MutableLiveData<String>
        var previousExpireDate: MutableLiveData<String>
        var mFile: MutableLiveData<File?>
        var amendmentMap: HashMap<String?, List<String>?>?
    }
}
