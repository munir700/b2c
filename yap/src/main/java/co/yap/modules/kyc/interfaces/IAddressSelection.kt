package co.yap.modules.kyc.interfaces

import android.graphics.Bitmap
import android.widget.TextView
import co.yap.modules.kyc.activities.MapDetailViewActivity
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

interface IAddressSelection {

    interface View : IBase.View<ViewModel> {
        fun getLocationPermission()

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnNext(id: Int)
        fun handlePressOnSelectLocation(id: Int)
        fun handlePressOnCardSelectLocation(id: Int)
        fun handlePressOnCloseMap(id: Int)
        fun getPermissions()
        fun onEditorActionListener(): TextView.OnEditorActionListener
        var mapFragment: SupportMapFragment?
        var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int
        var mLocationPermissionGranted: Boolean
        var mapDetailViewActivity: MapDetailViewActivity
        fun initMap()
        fun onMapInit(p0: GoogleMap?)
        fun  getDeviceLocation()
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
        var cardView: String
        var confirmLocationButton: Boolean
        var googleMap: GoogleMap?

    }
}