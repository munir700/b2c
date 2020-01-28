package co.yap.modules.kyc.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.states.AddressSelectionState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.requestdtos.UpdateAddressRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.MyUserManager
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

class AddressSelectionViewModel(application: Application) :
    BaseViewModel<IAddressSelection.State>(application),
    IAddressSelection.ViewModel, IRepositoryHolder<CardsRepository> {

    var city: String = ""
    var country: String = ""
    private val TAG = "AddressSelectionFragment"
    private lateinit var mMap: GoogleMap
    private var DEFAULT_ZOOM = 15
    override var mDefaultLocation = LatLng(25.276987, 55.296249)
    lateinit var icon: BitmapDescriptor
    private lateinit var placesClient: PlacesClient
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    override lateinit var mLastKnownLocation: Location
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
    lateinit var list: List<Address>

    override val repository: CardsRepository = CardsRepository
    override val onSuccess: MutableLiveData<Int> = MutableLiveData()

    override var checkGps: Boolean = true
        get() = field

    override val MARKER_CLICK_ID: Int = 2
        get() = field

    override val UPDATE_ADDRESS_EEVENT: Int = 5
        get() = field

    override val GPS_CLICK_EEVENT: Int = 200
        get() = field

    override val ON_UPDATE_ADDRESS_EVENT: Int = 300
        get() = field

    override val ON_ADD_NEW_ADDRESS_EVENT: Int = 500
        get() = field

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override var selectedLocationLatitude: Double = 0.0
    override var selectedLocationLongitude: Double = 0.0

    override lateinit var updateAddressRequest: UpdateAddressRequest

    var checkLocationUpdate: Boolean = false

    var locationSelectionStart: Boolean = false
//    var checkLocationUpdate: Boolean = false

    fun mapDetailViewActivity(): Activity {
        return Activity()
    }

    override var mapDetailViewActivity: Activity = mapDetailViewActivity()
        get() = field
        set(value) {

        }

    override var mapFragment: SupportMapFragment? = null
        get() = field
        set(value) {
        }

    override val state: AddressSelectionState = AddressSelectionState(application)

    override fun onCreate() {
        super.onCreate()
        MyUserManager.addressPhotoUrl = null
    }

    private fun requestOrderCard(id: Int) {
//        mDefaultLocation =
//            LatLng(
//                mLastKnownLocation.getLatitude(),
//                mLastKnownLocation.getLongitude()
//            )
//
//
//        setAddress(mDefaultLocation.latitude, mDefaultLocation.longitude)

        setAddress(mDefaultLocation.latitude, mDefaultLocation.longitude)
        var orderCardRequest: OrderCardRequest = OrderCardRequest(
            state.landmarkField,
            "",
            state.addressField,
            state.addressField,
            mDefaultLocation.latitude,
            mDefaultLocation.longitude,
            city, country
        )
        launch {
            state.loading = true
            when (val response = repository.orderCard(orderCardRequest)) {
                is RetroApiResponse.Success -> {
                    state.error = ""

//                    clickEvent.setValue(id)
                    clickEvent.setValue(ON_ADD_NEW_ADDRESS_EVENT)
                    state.loading = false
//if ( MyUserManager.userAddress!! != null){
//    MyUserManager.userAddress!!.address1 = state.addressField
//    MyUserManager.userAddress!!.address2 = state.landmarkField
////}

                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.error = response.error.message
//                    onSuccess.setValue(id)
                    clickEvent.setValue(id)
                }
            }
        }
    }

    override fun requestUpdateAddress(updateAddressRequest: UpdateAddressRequest) {
        state.error = ""
//        mDefaultLocation =
//            LatLng(
//                mLastKnownLocation.getLatitude(),
//                mLastKnownLocation.getLongitude()
//            )
//
//
//        setAddress(mDefaultLocation.latitude, mDefaultLocation.longitude)

        launch {
            state.loading = true
            when (val response = repository.editAddressRequest(updateAddressRequest)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    onSuccess.setValue(UPDATE_ADDRESS_EEVENT)
                    //clickEvent.setValue(UPDATE_ADDRESS_EEVENT)
                }

                is RetroApiResponse.Error -> {
                    state.error = response.error.message
                    state.loading = false
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
            checkLocationUpdate = true
            mMap!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
                override fun onMapClick(p0: LatLng?) {
//                    toggleMarkerVisibility()

                    if (p0 != null) {
                        mDefaultLocation = p0
                    }

                    var geocoder: Geocoder = Geocoder(getApplication())
                    list = geocoder.getFromLocation(p0!!.latitude, p0!!.longitude, 1)
                    try {
                        var selectedAddress: Address = list.get(0)
                        placeName =
                            selectedAddress.getAddressLine(0).split(",").toTypedArray().get(0)
//                    state.placeSubTitle= " "
                        placeSubTitle = selectedAddress.getAddressLine(0)

                        locationMarker!!.remove()
                        locationMarker!!.isVisible = false
                        setUpMarker(p0, placeName, selectedAddress.getAddressLine(0))
                        locationMarker = mMap.addMarker(markerOptions)
                        state.placeTitle = placeName
                        state.placePhoto = BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.location_place_holder
                        )
                        state.placeSubTitle = placeSubTitle

                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
//                        locationMarker!!.isVisible = false
                        toggleMarkerVisibility()
                    }
//                    locationMarker = mMap.addMarker(markerOptions)
//                    state.placeTitle = placeName
//                    state.placePhoto = BitmapFactory.decodeResource(
//                        context.resources,
//                        R.drawable.location_place_holder
//                    )
//                    state.placeSubTitle = placeSubTitle
//                    state.addressField = placeSubTitle
//                    state.landmarkField = placeName
                }

            })

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
        if (checkLocationUpdate) {
            MyUserManager.addressPhotoUrl = null
        }
//        if (state.isFromPersonalDetailView ) {
//
        locationSelectionStart = true
//        }
//        if (state.isFromPersonalDetailView && locationSelectionStart){

        state.landmarkField = this.placeName
        state.addressField = this.placeSubTitle
//        }
        state.headingTitle = this.placeName
        //        state.landmarkField = this.placeName
        toggleMarkerVisibility()
        state.placePhoto = this.placePhoto
        state.subHeadingTitle =
            Translator.getString(
                getApplication(),
                R.string.screen_meeting_location_display_text_selected_subtitle
            )
        state.locationBtnText =
            Translator.getString(
                getApplication(),
                R.string.screen_meeting_location_button_change_location
            )
    }

    override fun handlePressOnCloseMap(id: Int) {
        if (id == R.id.tvTermsAndConditions) {
            clickEvent.setValue(id)
        } else {
            state.isMapOnScreen = false
            clickEvent.setValue(id)
            toggleMarkerVisibility()
        }
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
        selectedLocationLatitude

//        mLastKnownLocation.latitude = mDefaultLocation.latitude
//        mLastKnownLocation.longitude = mDefaultLocation.longitude
        if ((::mLastKnownLocation.isInitialized && mLastKnownLocation != null)) {
            mLastKnownLocation.latitude = mDefaultLocation.latitude
            mLastKnownLocation.longitude = mDefaultLocation.longitude
        }


        if (state.isFromPhysicalCardsLayout) {
//           start old fragment by taking address address
            clickEvent.setValue(id)

        } else if (state.isFromPersonalDetailView) {
//           start old fragment by taking address address
            clickEvent.setValue(id)

        } else {
            requestOrderCard(id)
        }
    }

    @SuppressLint("MissingPermission")
    override fun getDefaultLocationMap(activity: Activity) {
        try {
            val locationResult = mFusedLocationProviderClient.getLastLocation()
            activity?.let {
                locationResult.addOnSuccessListener(
                    it,
                    OnSuccessListener<Location> { location ->
                        if (location != null) {
                            mLastKnownLocation = location
                            mDefaultLocation =
                                LatLng(
                                    mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude()
                                )
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    mDefaultLocation,
                                    DEFAULT_ZOOM.toFloat()
                                ),
                                animationFrequency,
                                null
                            )
                        }
                        getCurrentPlaceLikelihoods()
                    })
            }
        } catch (e: Exception) {
        }
    }

    @SuppressLint("MissingPermission")
    override fun getDeviceLocation(activity: Activity) {

        try {

            val locationResult = mFusedLocationProviderClient.getLastLocation()
            activity?.let {
                locationResult.addOnSuccessListener(
                    it,
                    OnSuccessListener<Location> { location ->
                        if (location != null) {
                            mLastKnownLocation = location
                            mDefaultLocation =
                                LatLng(
                                    mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude()
                                )
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    mDefaultLocation,
                                    DEFAULT_ZOOM.toFloat()
                                ),
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
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
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
                                markerSnippet = currentPlace.address
                                placeSubTitle = markerSnippet.toString()

                            }

                            if (state.isFromPersonalDetailView && !locationSelectionStart) {

                            }

                            placeName = currentPlace.name!!
                            placeTitle = currentPlace.address!!
                            var currentAddress: String = currentPlace.address!!
                            locationMarker!!.remove()
                            locationMarker!!.isVisible = false
//                            setUpMarker(p0, placeName, selectedAddress.getAddressLine(0))
                            setUpMarker(markerLatLng!!, "", "")

                            //                    }
                            locationMarker = mMap.addMarker(markerOptions)

//                            locationMarker = mMap.addMarker(markerOptions)
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
            MyUserManager.addressPhotoUrl = placePhoto

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
        if (SharedPreferenceManager(context!!).getThemeValue().equals(Constants.THEME_HOUSEHOLD)) {

            icon =
                this!!.bitmapDescriptorFromVector(getApplication(), R.drawable.ic_pin_house_hold)!!
        } else {
            icon = this!!.bitmapDescriptorFromVector(getApplication(), R.drawable.ic_pin)!!
        }
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

    fun setAddress(latitude: Double, longitude: Double) {
        try {
            var geocoder: Geocoder = Geocoder(getApplication())
            list = geocoder.getFromLocation(latitude, longitude, 1)

            if (!list.isNullOrEmpty()) {
                city = list[0].locality
                country = list[0].countryName
            } else {
                city = "Dubai"
                country = "UAE"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            city = "Dubai"
            country = "UAE"
        }
    }
}