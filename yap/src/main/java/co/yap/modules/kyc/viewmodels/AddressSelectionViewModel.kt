package co.yap.modules.kyc.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.modules.kyc.activities.AddressSelectionActivity
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
import com.google.android.gms.maps.model.*
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

    var locationMarker: Marker? = null
    override val MARKER_CLICK_ID: Int = 2
        get() = field

    override val clickEvent: SingleClickEvent = SingleClickEvent()


    fun mapDetailViewActivity(): AddressSelectionActivity {
        return AddressSelectionActivity()
    }

    override var mapDetailViewActivity: AddressSelectionActivity = mapDetailViewActivity()
        get() = field
        set(value) {

        }
    var checkMapInit: Boolean = false

    override var mapFragment: SupportMapFragment? = null
        get() = field
        set(value) {

        }


    private val TAG = "AddressSelectionActivity"

    private lateinit var mMap: GoogleMap
    private var DEFAULT_ZOOM = 15
    private var mDefaultLocation = LatLng(25.276987, 55.296249)
    lateinit var icon: BitmapDescriptor
    private lateinit var placesClient: PlacesClient

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mLastKnownLocation: Location
    var animationFrequency: Int = 1                 //can be set to 2000

    var markerSnippet: String = ""
    var placeName: String = ""
    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap = BitmapFactory.decodeResource(
        application.resources,
        R.drawable.black_white_tile
    ) //should add place holder here, adding dummy placeholder right now

    lateinit var markerOptions: MarkerOptions
    override val state: AddressSelectionState = AddressSelectionState(application)


    override fun onMapInit(googleMap: GoogleMap?) {
        initMap()
        if (googleMap != null) {
            mMap = googleMap
            locationMarker = mMap.addMarker(markerOptions)
            toggleMarkerVisibility()

            mMap.uiSettings.isZoomControlsEnabled = false
            mMap.uiSettings.isMapToolbarEnabled = false
            mMap.uiSettings.isCompassEnabled = false
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
                animationFrequency,
                null
            )

//            mMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
//                override fun onMarkerClick(marker: Marker): Boolean {
//                    state.cardView = true
//
//                    clickEvent.setValue(MARKER_CLICK_ID)
//                    return false
//                }
//            })

//            if (!(::mLastKnownLocation.isInitialized && mLastKnownLocation != null)) {
//
//                getDeviceLocation()
//            }
        } else {

        }
    }

    override fun toggleMarkerVisibility() {
        if (!state.isMapOnScreen) {
            locationMarker!!.isVisible = false
        } else {
            locationMarker!!.isVisible = true
        }
    }

    override fun initMap() {
        setUpMarker(mDefaultLocation, placeName, markerSnippet)
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
//        getDeviceLocation()
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

    @SuppressLint("MissingPermission")
    override fun getDeviceLocation() {

        try {
            val locationResult = mFusedLocationProviderClient.getLastLocation()
            locationResult.addOnSuccessListener(
                mapDetailViewActivity,
                OnSuccessListener<Location> { location ->
                    if (location != null) {
                        mLastKnownLocation = location
                        mDefaultLocation =
                            LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
                            animationFrequency,
                            null
                        )
                    } else {

                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
                            animationFrequency,
                            null
                        )

                    }

                    getCurrentPlaceLikelihoods()
                })
        } catch (e: Exception) {
            Log.e("Exception: %s", e.message)
        }

    }


    @SuppressLint("MissingPermission")
    private fun getCurrentPlaceLikelihoods() {
        val placeFields = Arrays.asList(
            Place.Field.NAME, Place.Field.ADDRESS,
            Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS
        )
        state.loading = true

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
                            placeName = currPlace.name!!
                            placeTitle = currPlace.address!!
                            var currentAddress: String = currPlace.address!!
                            setUpMarker(markerLatLng!!, placeName, markerSnippet)

                            locationMarker = mMap.addMarker(markerOptions)
//                            toggleMarkerVisibility()
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    mDefaultLocation,
                                    DEFAULT_ZOOM.toFloat()
                                ), animationFrequency, null
                            )

                            if (!currPlace.photoMetadatas.isNullOrEmpty() && currPlace.photoMetadatas!!.size > 0) {
                                attemptFetchPhoto(currPlace)
                            } else {
                                toggleMarkerVisibility()
                                state.cardView = true
                                clickEvent.setValue(MARKER_CLICK_ID)
                            }
                        } else {
                            break
                        }
                        break
                    }
                } else {
                    state.loading = false
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
            state.loading = false
        }

        photoTask.addOnCompleteListener {
            toggleMarkerVisibility()
            state.loading = false
            state.cardView = true
            clickEvent.setValue(MARKER_CLICK_ID)

        }
    }

    override fun setUpCardFields() {
        state.placeTitle = this.placeName
        state.placePhoto = this.placePhoto
        state.placeSubTitle = this.placeSubTitle
    }

    override fun onLocatioenSelected() {
        state.headingTitle = this.placeName
        state.addressField = this.placeName + ", " + this.placeTitle
        state.landmarkField = this.placeName
        toggleMarkerVisibility()
        state.placePhoto = this.placePhoto
        state.subHeadingTitle =
            Translator.getString(getApplication(), R.string.screen_meeting_location_display_text_selected_subtitle)
        state.locationBtnText =
            Translator.getString(getApplication(), R.string.screen_meeting_location_button_change_location)
    }

    override fun handlePressOnCloseMap(id: Int) {
        state.isMapOnScreen = false
        state.cardView = false
        clickEvent.setValue(id)
        toggleMarkerVisibility()

    }

    override fun handlePressOnCardSelectLocation(id: Int) {
        state.isMapOnScreen = false
        clickEvent.setValue(id)
        toggleMarkerVisibility()
    }

    override fun handlePressOnSelectLocation(id: Int) {
        state.closeCard = true
        state.isMapOnScreen = true
        clickEvent.setValue(id)
        toggleMarkerVisibility()
    }

    override fun handlePressOnNext(id: Int) {
        clickEvent.setValue(id)
    }

    fun handlePressOnChangeLocation() {
        state.locationBtnText = getString(R.string.screen_meeting_location_button_change_location)
        state.isMapOnScreen = true
        toggleMarkerVisibility()
    }
}