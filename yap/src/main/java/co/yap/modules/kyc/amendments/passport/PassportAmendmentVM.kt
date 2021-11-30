package co.yap.modules.kyc.amendments.passport

import android.app.Application
import co.yap.R
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class PassportAmendmentVM(application: Application) :
    BaseViewModel<IPassportAmendment.State>(application), IPassportAmendment.ViewModel, IValidator {
    override val state = PassportAmendmentState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var validator: Validator? = Validator(null)

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


    override fun getDatePicker(
        currentCalendar: Calendar?,
        minCalendar: Calendar?,
        maxCalendar: Calendar?,
        callBack: DatePickerDialog.OnDateSetListener
    ): DatePickerDialog {
        val dpd =
            DatePickerDialog.newInstance(callBack, currentCalendar)
        maxCalendar?.let { dpd.maxDate = maxCalendar }
        minCalendar?.let { dpd.minDate = minCalendar }
        dpd.version = DatePickerDialog.Version.VERSION_2
        return dpd
    }
}