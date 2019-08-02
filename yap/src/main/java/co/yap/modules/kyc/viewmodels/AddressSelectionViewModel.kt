package co.yap.modules.kyc.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.modules.kyc.activities.MapDetailViewActivity
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.states.AddressSelectionState
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*

class AddressSelectionViewModel(application: Application) : BaseViewModel<IAddressSelection.State>(application),
    IAddressSelection.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    var appz: Application = application

    fun mapDetailViewActivity(): MapDetailViewActivity {
        return MapDetailViewActivity()
    }

    override var mapDetailViewActivity: MapDetailViewActivity = mapDetailViewActivity()
        get() = field
        set(value) {

        }

    override var mLocationPermissionGranted: Boolean = false
        get() = field
        set(value) {}
    override var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 1
        get() = field
        set(value) {}
    override var mapFragment: SupportMapFragment? = null
        get() = field
        set(value) {

        }


    private val TAG = "MapDetailViewActivity"

    private lateinit var mMap: GoogleMap
    private val DEFAULT_ZOOM = 16
    private var mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    lateinit var icon: BitmapDescriptor
    private lateinit var placesClient: PlacesClient

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mLastKnownLocation: Location
    var animationFrequency: Int = 1                 //can be set to 2000

    var markerSnippet: String = ""
    var placeName: String = ""
    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap? = null
    lateinit var markerOptions: MarkerOptions

    override fun handlePressOnCloseMap(id: Int) {
        state.cardView = state.gone
        //collapse maap
    }

    override fun handlePressOnCardSelectLocation(id: Int) {
        // close map
    }

    override val state: AddressSelectionState = AddressSelectionState(application)


    //map deaatil work
    override fun onMapInit(googleMap: GoogleMap?) {
     initMap()

        if (googleMap != null) {
            mMap = googleMap
            mMap.addMarker(markerOptions)
            mMap.uiSettings.isZoomControlsEnabled = false
            mMap.uiSettings.isMapToolbarEnabled = false
            mMap.uiSettings.isCompassEnabled = false
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
                animationFrequency,
                null
            )


            mMap.setOnMapClickListener { point ->
                //            cvLocationCard.visibility = View.VISIBLE

                state.cardView = state.visible
            }


//        ivClose.setOnClickListener {
////            cvLocationCard.visibility = View.GONE
//            state.cardView =state.gone
//        }

            if (!(::mLastKnownLocation.isInitialized && mLastKnownLocation != null)) {
                getPermissions()
                getDeviceLocation()
            }
        } else {
            if (!(::mLastKnownLocation.isInitialized && mLastKnownLocation != null)) {
                getPermissions()
                getDeviceLocation()
            }
        }
    }

    override fun initMap() {
        setUpMarker(mDefaultLocation, placeName, markerSnippet)
//        getDeviceLocation()
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(context, apiKey)
        placesClient = Places.createClient(context)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onResume() {
        super.onResume()
        getDeviceLocation()
    }

    fun setUpMarker(
        markerLatLng: LatLng?,
        placeName: String?,
        markerSnippet: String?
    ) {
        icon = this!!.bitmapDescriptorFromVector(getApplication(), R.drawable.ic_pin)!!

        markerOptions = MarkerOptions()
            .icon(icon)
            .position(markerLatLng!!)

    }

//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        mMap.addMarker(markerOptions)
//        mMap.uiSettings.isZoomControlsEnabled = false
//        mMap.uiSettings.isMapToolbarEnabled = false
//        mMap.uiSettings.isCompassEnabled = false
//        mMap.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
//            animationFrequency,
//            null
//        )
//
//
//        mMap.setOnMapClickListener { point ->
//            //            cvLocationCard.visibility = View.VISIBLE
//
//            state.cardView = state.visible
//        }
//
//
////        ivClose.setOnClickListener {
//////            cvLocationCard.visibility = View.GONE
////            state.cardView =state.gone
////        }
//
//        if (!(::mLastKnownLocation.isInitialized && mLastKnownLocation != null)) {
//            getPermissions()
//            getDeviceLocation()
//        }
//    }


    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
//        if (null != mapDetailViewActivity) {
        try {
            val locationResult = mFusedLocationProviderClient.getLastLocation()
            locationResult.addOnSuccessListener(
                mapDetailViewActivity,
                OnSuccessListener<Location> { location ->
                    if (location != null) {
                        mLastKnownLocation = location
                        Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude())
                        Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude())
                        mDefaultLocation =
                            LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())


                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
                            animationFrequency,
                            null
                        )

                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")

                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
                            animationFrequency,
                            null
                        )
//                        IAddressSelection.View=  IBase.View<AddressSelectionViewModel>
                        getPermissions()  // getLocationPermission()
                    }

                    getCurrentPlaceLikelihoods()
                })
        } catch (e: Exception) {
            Log.e("Exception: %s", e.message)
        }
//        } else {
//            clickEvent.setValue(2)
    }




override fun getPermissions() {
    clickEvent.setValue(1)
}

@SuppressLint("MissingPermission")
private fun getCurrentPlaceLikelihoods() {
    val placeFields = Arrays.asList(
        Place.Field.NAME, Place.Field.ADDRESS,
        Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS
    )

    val request = FindCurrentPlaceRequest.builder(placeFields).build()
    val placeResponse = placesClient.findCurrentPlace(request)
    placeResponse.addOnCompleteListener(
        this!!.mapDetailViewActivity!!,
        OnCompleteListener<FindCurrentPlaceResponse> { task ->
            if (task.isSuccessful) {
                val response = task.result
                var i = 0
                for (placeLikelihood in response!!.placeLikelihoods) {
                    if (i == 0) {
                        val currPlace = placeLikelihood.place
                        if (currPlace.attributions != null) {
                            currPlace.attributions!!.joinToString(" ")
                        }
                        val markerLatLng = currPlace.latLng
                        var markerSnippet = currPlace.address

                        if (currPlace.address != null) {
                            markerSnippet = markerSnippet + "\n" + currPlace.address
                            placeSubTitle = markerSnippet
                        }
                        if (!currPlace.photoMetadatas.isNullOrEmpty() && currPlace.photoMetadatas!!.size > 0) {
                            attemptFetchPhoto(currPlace)
                        }

                        placeName = currPlace.name!!
                        placeTitle = currPlace.address!!
                        var currentAddress: String = currPlace.address!!

                        setUpMarker(markerLatLng!!, placeName, markerSnippet)
                        mMap.addMarker(markerOptions)

                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                mDefaultLocation,
                                DEFAULT_ZOOM.toFloat()
                            ), animationFrequency, null
                        )

                    } else {
                        break
                    }
                    break

                }

            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    val apiException = exception as ApiException?
                    Log.e(TAG, "Place not found: " + apiException!!.statusCode)
                }
            }
        })
}

private fun attemptFetchPhoto(place: Place) {
    val photoMetadatas = place.getPhotoMetadatas()
    if (photoMetadatas != null && !photoMetadatas!!.isEmpty()) {
        fetchPhoto(photoMetadatas!!.get(0))
    }
}

/**
 * Fetches a Bitmap using the Places API and displays it.
 *
 * @param photoMetadata from a [Place] instance.
 */
private fun fetchPhoto(photoMetadata: PhotoMetadata) {
    var photoMetadata = photoMetadata
    val photoRequestBuilder = FetchPhotoRequest.builder(photoMetadata)

    val photoTask = placesClient!!.fetchPhoto(photoRequestBuilder.build())

    photoTask.addOnSuccessListener { response ->

        placePhoto = response.bitmap

        setUpCardFields()
    }

    photoTask.addOnFailureListener { exception ->
        exception.printStackTrace()
        //hide loader here
    }

    photoTask.addOnCompleteListener {
        //hide loader here
    }
}

private fun setUpCardFields() {
    state.headingTitle = this.placeName
    state.subHeadingTitle = this.placeTitle
    state.placePhoto = this.placePhoto

}

private fun pickCurrentPlace() {
    if (mMap == null) {
        return
    }

    getDeviceLocation()
}


//


fun onLocatioenSelected() {
    // aalso visible faade in location button
    state.headingTitle = Translator.getString(getApplication(), R.string.screen_meeting_location_display_text_title)
    state.subHeadingTitle =
        Translator.getString(getApplication(), R.string.screen_meeting_location_display_text_selected_subtitle)
    state.locationBtnText =
        Translator.getString(getApplication(), R.string.screen_meeting_location_button_change_location)
}


override fun handlePressOnSelectLocation(id: Int) {
    clickEvent.setValue(id)
    onLocatioenSelected()
}

override fun handlePressOnNext(id: Int) {
    clickEvent.setValue(id)

    //            onLocatioenSelected()
//           start new fragment in sequeence
}

fun handlePressOnChangeLocation() {
    state.locationBtnText = getString(R.string.screen_meeting_location_button_change_location)
}

override fun onEditorActionListener(): TextView.OnEditorActionListener {
    return object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (state.valid) {
//           start new fragment in sequeence
                }
            }
            return false
        }
    }
}

}