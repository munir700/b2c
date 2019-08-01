package co.yap.modules.kyc.interfaces

import android.graphics.Bitmap
import android.widget.TextView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.google.android.gms.maps.GoogleMap

interface IAddressSelection {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnNext(id: Int)
        fun handlePressOnSelectLocation(id: Int)
        fun handlePressOnCardSelectLocation(id: Int)
        fun handlePressOnCloseMap(id: Int)
        fun onEditorActionListener(): TextView.OnEditorActionListener
    }

    interface State : IBase.State {
        var headingTitle: String
        var subHeadingTitle: String
        var addressField: String
        var landmarkField: String
        var locationBtnText: String
        var valid: Boolean

        //map detail

        var placePhoto: Bitmap?
        var placeTitle: String
        var placeSubTitle: String
        var closeCard: Boolean
        var cardView: Boolean
        var confirmLocationButton: Boolean
        var googleMap: GoogleMap?

    }
}