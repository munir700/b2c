package co.yap.modules.kyc.amendments.passport

import android.app.Application
import co.yap.R
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.DateUtils
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class PassportAmendmentVM(application: Application) :
    BaseViewModel<IPassportAmendment.State>(application), IPassportAmendment.ViewModel {
    override val state = PassportAmendmentState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun onCreate() {
        super.onCreate()
        state.issueDataCalender = Calendar.getInstance()
        state.expireDataCalender = Calendar.getInstance()
    }
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
    override fun getUploadDocumentOptions(isShowRemovePhoto: Boolean): ArrayList<BottomSheetItem> {
        val list = arrayListOf<BottomSheetItem>()
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_camera,
                title = getString(Strings.screen_update_profile_photo_display_text_open_camera),
                subTitle = getString(Strings.screen_upload_documents_display_sheet_text_scan_single_document),
                tag = PhotoSelectionType.CAMERA.name
            )
        )
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_file_manager,
                title = getString(Strings.screen_upload_documents_display_sheet_text_upload_from_files),
                subTitle = getString(Strings.screen_upload_documents_display_sheet_text_upload_from_files_descriptions),
                tag = PhotoSelectionType.GALLERY.name
            )
        )
        if (isShowRemovePhoto)
            list.add(
                BottomSheetItem(
                    icon = R.drawable.ic_remove,
                    title = getString(Strings.screen_update_profile_photo_display_text_remove_photo),
                    tag = PhotoSelectionType.REMOVE_PHOTO.name
                )
            )

        return list
    }


    override fun getDatePicker(currentCalendar: Calendar?,
                               minCalendar: Calendar?,
                               maxCalendar: Calendar?,
                               selectedCalendar: (Calendar?) -> Unit): DatePickerDialog {
        val dpd =
            DatePickerDialog.newInstance({ _, year, monthOfYear, dayOfMonth ->
                currentCalendar?.set(Calendar.YEAR, year)
                currentCalendar?.set(Calendar.MONTH, monthOfYear)
                currentCalendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedCalendar.invoke(currentCalendar)

            }, currentCalendar)
        dpd.maxDate = Calendar.getInstance()
        dpd.minDate = Calendar.getInstance()
        dpd.version = DatePickerDialog.Version.VERSION_2
        return  dpd
    }
}