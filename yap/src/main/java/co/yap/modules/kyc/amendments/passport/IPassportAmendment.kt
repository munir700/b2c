package co.yap.modules.kyc.amendments.passport

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.amendments.missinginfo.MissingInfoAdapter
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class IPassportAmendment {
    interface View : IBase.View<ViewModel>{
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        fun getUploadDocumentOptions(isShowRemovePhoto: Boolean): ArrayList<BottomSheetItem>
        fun  getDatePicker(currentCalendar: Calendar?,minCalendar: Calendar?=Calendar.getInstance(),maxCalendar: Calendar?=Calendar.getInstance(), selectedCalendar: (Calendar?) -> Unit): DatePickerDialog
    }

    interface State : IBase.State {
        var issueDataCalender:Calendar?
        var expireDataCalender:Calendar?
        var passportNumber:MutableLiveData<String>
        var issueDate:MutableLiveData<String>
        var expireDate:MutableLiveData<String>
    }
}