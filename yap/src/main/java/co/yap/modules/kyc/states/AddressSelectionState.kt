package co.yap.modules.kyc.states

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import com.google.android.gms.maps.GoogleMap


class AddressSelectionState(application: Application) : BaseState(), IAddressSelection.State {

    val mContext: Context = application.applicationContext

    val VISIBLE: Int = 0x00000000
    val GONE: Int = 0x00000008

//fun toggleVisisbilityCard(boo){
//
//}


    @get:Bindable
    override var googleMap: GoogleMap? = null
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.googleMap)
        }

    @get:Bindable
    override var placePhoto: Bitmap? =
        BitmapFactory.decodeResource(mContext.resources, R.drawable.black_white_tile) //R.drawable.black_white_tile
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.placePhoto)
        }

    @get:Bindable
    override var placeTitle: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.placeTitle)
        }

    @get:Bindable
    override var placeSubTitle: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.placeSubTitle)
        }

    @get:Bindable
    override var closeCard: Boolean = false
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.closeCard)
        }

    @get:Bindable
    override var isMapOnScreen: Boolean = false
        set(value) {
            field = value
//            notifyPropertyChanged(BR.isMapOnScreen)
        }

    @get:Bindable
    override var errorVisibility: Int = VISIBLE
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.handleBackPress)
//            notifyPropertyChanged(BR.cardView)
        }
    @get:Bindable
    override var errorChecked: Boolean = false
        get() = field
        set(value) {
            field = value

                notifyPropertyChanged(BR.errorChecked)

         }

    @get:Bindable
    override var cardView: Boolean = false
        get() = field
        set(value) {


            if (value) {
                errorVisibility = VISIBLE
                notifyPropertyChanged(BR.errorVisibility)

            }
            field = value
            errorChecked=value
//            else{
//
//                errorVisibility = GONE
//                notifyPropertyChanged(BR.errorVisibility)
//            }
            notifyPropertyChanged(BR.cardView)

        }

    @get:Bindable
    override var confirmLocationButton: Boolean = false
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmLocationButton)
        }

    @get:Bindable
    override var headingTitle: String =
        Translator.getString(application, R.string.screen_meeting_location_display_text_title)
        set(value) {

            field = value
            notifyPropertyChanged(BR.headingTitle)
        }

    @get:Bindable
    override var subHeadingTitle: String =
        Translator.getString(application, R.string.screen_meeting_location_display_text_subtitle)
        set(value) {
            field = value
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
        set(value) {
            field = value
            notifyPropertyChanged(BR.landmarkField)
        }

    @get:Bindable
    override var checked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
            valid = validateAddress()
        }

    @get:Bindable
    override var locationBtnText: String =
        Translator.getString(application, R.string.screen_meeting_location_button_confirm_location)
        set(value) {
            field = value
            notifyPropertyChanged(BR.locationBtnText)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }


    private fun validateAddress(): Boolean {
        return addressField.isNotEmpty() && addressField.length >= 2 && checked
    }
}