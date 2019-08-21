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
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.states.AddressSelectionState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
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
    IAddressSelection.ViewModel, IRepositoryHolder<CardsRepository> {

    private val TAG = "AddressSelectionFragment"
    private lateinit var mMap: GoogleMap
    private var DEFAULT_ZOOM = 15
    private var mDefaultLocation = LatLng(25.276987, 55.296249)
    lateinit var icon: BitmapDescriptor
    private lateinit var placesClient: PlacesClient
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mLastKnownLocation: Location
    var animationFrequency: Int = 1
    var markerSnippet: String = ""
    var placeName: String = ""
    var placeTitle: String = ""
    var placeSubTitle: String = ""
    lateinit var markerOptions: MarkerOptions
    var locationMarker: Marker? = null
    var checkMapInit: Boolean = false
    var placePhoto: Bitmap = BitmapFactory.decodeResource(
        application.resources,
        R.drawable.location_place_holder
    )

    override val repository: CardsRepository = CardsRepository

    override var checkGps: Boolean = true
        get() = field

    override val MARKER_CLICK_ID: Int = 2
        get() = field

    override val GPS_CLICK_EEVENT: Int = 200
        get() = field

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    fun mapDetailViewActivity(): DocumentsDashboardActivity {
        return DocumentsDashboardActivity()
    }

    override var mapDetailViewActivity: DocumentsDashboardActivity = mapDetailViewActivity()
        get() = field
        set(value) {

        }

    override var mapFragment: SupportMapFragment? = null
        get() = field
        set(value) {
        }

    override val state: AddressSelectionState = AddressSelectionState(application)

    private fun requestOrderCard(id: Int) {
        var orderCardRequest: OrderCardRequest = OrderCardRequest(
            state.landmarkField,
            "",
            state.addressField,
            mDefaultLocation.latitude,
            mDefaultLocation.longitude
        )
        launch {
            state.loading = true
            when (val response = repository.orderCard(orderCardRequest)) {
                is RetroApiResponse.Success -> {
                    state.error = ""
                    clickEvent.setValue(id)
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.error = response.error.message
                    clickEvent.setValue(id)
                }
            }
        }
    }

    override fun initMap() {
        setUpMarker(mDefaultLocation, placeName, markerSnippet)
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(context, apiKey)
        placesClient = Places.createClient(context)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    }

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

            if (!(::mLastKnownLocation.isInitialized && mLastKnownLocation != null)) {

                getDefaultLocationMap(mapDetailViewActivity)
            }
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
        clickEvent.setValue(id)
        toggleMarkerVisibility()
    }

    override fun handlePressOnNext(id: Int) {
        requestOrderCard(id)
    }

    @SuppressLint("MissingPermission")
    override fun getDefaultLocationMap(activity: DocumentsDashboardActivity) {
        try {
            val locationResult = mFusedLocationProviderClient.getLastLocation()
            activity?.let {
                locationResult.addOnSuccessListener(
                    it,
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
                        }
                        getCurrentPlaceLikelihoods()
                    })
            }
        } catch (e: Exception) {
            Log.e("Exception: %s", "exception")
        }
    }

    @SuppressLint("MissingPermission")
    override fun getDeviceLocation(activity: DocumentsDashboardActivity) {

        try {

            val locationResult = mFusedLocationProviderClient.getLastLocation()
            activity?.let {
                locationResult.addOnSuccessListener(
                    it,
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
                            clickEvent.setValue(GPS_CLICK_EEVENT)
                        }
                        getCurrentPlaceLikelihoods()

                    })
            }
        } catch (e: Exception) {
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    @SuppressLint("MissingPermission", "LongLogTag")
    private fun getCurrentPlaceLikelihoods() {
        val placeFields = Arrays.asList(
            Place.Field.NAME, Place.Field.ADDRESS,
            Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS
        )
        state.loading = true

        val request = FindCurrentPlaceRequest.builder(placeFields).build()
        val placeResponse = placesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener(
            mapDetailViewActivity()!!,
            OnCompleteListener<FindCurrentPlaceResponse> { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    var i = 0
                    for (placeLikelihood in response!!.placeLikelihoods) {
                        if (i == 0) {
                            val currentPlace = placeLikelihood.place
                            if (currentPlace.attributions != null) {
                                currentPlace.attributions!!.joinToString(" ")
                            }
                            val markerLatLng = currentPlace.latLng
                            var markerSnippet = currentPlace.address

                            if (currentPlace.address != null) {
                                markerSnippet = markerSnippet + "\n" + currentPlace.address
                                placeSubTitle = markerSnippet
                            }
                            placeName = currentPlace.name!!
                            placeTitle = currentPlace.address!!
                            var currentAddress: String = currentPlace.address!!
                            setUpMarker(markerLatLng!!, placeName, markerSnippet)

                            locationMarker = mMap.addMarker(markerOptions)
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    mDefaultLocation,
                                    DEFAULT_ZOOM.toFloat()
                                ), animationFrequency, null
                            )

                            if (!currentPlace.photoMetadatas.isNullOrEmpty() && currentPlace.photoMetadatas!!.size > 0) {
                                attemptFetchPhoto(currentPlace)
                            } else {
                                state.loading = false
                                popUPcardFields()
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
            popUPcardFields()
            clickEvent.setValue(MARKER_CLICK_ID)

        }
    }

    fun popUPcardFields() {
        val VISIBLE: Int = 0x00000000
        if (null != this.placeSubTitle || null != this.placeName || null != this.placePhoto && (state.isMapOnScreen)) {
            state.errorVisibility = VISIBLE
            state.cardView = true
        } else {
            state.errorVisibility = VISIBLE
            state.cardView = false
        }
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

    fun handlePressOnChangeLocation() {
        state.locationBtnText = getString(R.string.screen_meeting_location_button_change_location)
        toggleMarkerVisibility()
    }

    override fun onResume() {
        super.onResume()
        getDefaultLocationMap(mapDetailViewActivity)
    }
}