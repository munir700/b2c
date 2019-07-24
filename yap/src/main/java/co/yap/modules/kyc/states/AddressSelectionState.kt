package co.yap.modules.kyc.states

import android.app.Application
import android.content.Context
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.StringUtils


class AddressSelectionState(application: Application) : BaseState(), IAddressSelection.State {

    val mContext: Context = application.applicationContext

    @get:Bindable
    override var headingTitle: String = ""
        get() = field
        set(value) {
//            field = value
//            notifyPropertyChanged(BR.headingTitle)

//            field = value
            field =
                Translator.getString(mContext, mContext.getString(R.string.screen_meeting_location_display_text_title))
            notifyPropertyChanged(BR.headingTitle)
        }

    @get:Bindable
    override var subHeadingTitle: String = ""
        get() = field
        set(value) {
//            field = value
            field = Translator.getString(
                mContext,
                mContext.getString(R.string.screen_meeting_location_display_text_subtitle)
            )
            notifyPropertyChanged(BR.subHeadingTitle)
        }

    @get:Bindable
    override var addressField: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.addressField)
        }

    @get:Bindable
    override var landmarkField: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.landmarkField)
        }

    @get:Bindable
    override var locationBtnText: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.locationBtnText)
        }
    @get:Bindable
    override var valid: Boolean = false
        get() = validate()

    //        set(value) {
//            field = value
//            notifyPropertyChanged(BR.valid)
//
//        }
    private fun validate(): Boolean {
        return StringUtils.validateName(addressField) && StringUtils.validateName(landmarkField)

    }

}