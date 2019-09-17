package co.yap.modules.kyc.interfaces

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

interface IAddressSelection {

    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        var checkGps: Boolean
        var mapFragment: SupportMapFragment?
//        var mapDetailViewActivity: DocumentsDashboardActivity
        var mapDetailViewActivity: Activity
        val clickEvent: SingleClickEvent
        val MARKER_CLICK_ID: Int
        val GPS_CLICK_EEVENT: Int
        fun handlePressOnNext(id: Int)
        fun handlePressOnSelectLocation(id: Int)
        fun handlePressOnCardSelectLocation(id: Int)
        fun handlePressOnCloseMap(id: Int)
        fun initMap()
        fun onMapInit(p0: GoogleMap?)
//        fun getDeviceLocation(activity: DocumentsDashboardActivity)
//        fun getDefaultLocationMap(activity: DocumentsDashboardActivity)
        fun getDeviceLocation(activity: Activity)
        fun getDefaultLocationMap(activity: Activity)
        fun onLocatioenSelected()
        fun toggleMarkerVisibility()
        fun setUpCardFields()
    }

    interface State : IBase.State {
        var headingTitle: String
        var subHeadingTitle: String
        var addressField: String
        var landmarkField: String
        var locationBtnText: String
        var nextActionBtnText: String
        var valid: Boolean
        var checked: Boolean

        //map detail

        var placePhoto: Bitmap?
        var placeTitle: String
        var placeSubTitle: String
        var closeCard: Boolean
        var cardView: Boolean
        var confirmLocationButton: Boolean
        var isMapOnScreen: Boolean
        var googleMap: GoogleMap?
        var errorVisibility: Int
        var checkBoxLayoutVisibility: Int
        var errorChecked: Boolean
        var setDrawable: Drawable?
        var addressTitlesColor: Int
        var landMarkTitleColor: Int
        var onDrawableClick: Boolean
    }
}