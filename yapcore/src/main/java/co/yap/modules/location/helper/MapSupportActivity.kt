package co.yap.modules.location.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.modules.location.viewmodels.LocationSelectionViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.R
import co.yap.yapcore.helpers.PermissionHelper
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

open class MapSupportActivity : BaseBindingActivity<ILocationSelection.ViewModel>() {

    private var mMap: GoogleMap? = null
    private var defaultZoom = 15
    private var placesClient: PlacesClient? = null
    private var icon: BitmapDescriptor? = null
    protected var mDefaultLocation: LatLng? = null
    private var markerOptions: MarkerOptions? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationMarker: Marker? = null
    lateinit var context: Context
    var permissionHelper: PermissionHelper? = null

    override val viewModel: LocationSelectionViewModel
        get() = ViewModelProviders.of(this).get(LocationSelectionViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_address_selection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this // do remove this line
        initMap()
    }

    private fun getCurrentLocation() {
        mFusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
            if (location != null) {
                mDefaultLocation = LatLng(location.latitude, location.longitude)
                viewModel.address?.latitude = location.latitude
                viewModel.address?.longitude = location.longitude
                setupMapOptions()
            } else {
                startLocationUpdates()
            }
        }
    }

    protected fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = googleMap
            if (mDefaultLocation != null) {
                setupMapOptions()
            } else {
                getCurrentLocation()
            }
        }
    }

    private fun setupMapOptions() {
        createMarker(mDefaultLocation)
        mMap?.uiSettings?.isZoomControlsEnabled = false
        mMap?.uiSettings?.isMapToolbarEnabled = false
        mMap?.uiSettings?.isCompassEnabled = false
        mDefaultLocation?.let {
            animateCameraToLocation(it)
        }
        mMap?.setOnMapClickListener {
            it?.let { latLng ->
                viewModel.launch {
                    //viewModel.viewModelBGScope.async {
                    //mDefaultLocation = latLng
                    setSelectedMapLocation(latLng)
                    //}.await()
                }
            }
        }
        getCurrentPlaceLikelihoods()
    }

    protected fun initMap() {
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(context, apiKey)
        placesClient = Places.createClient(context)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        getCurrentLocation()
    }

    private fun setSelectedMapLocation(location: LatLng) {
        val geocoder = Geocoder(context)
        try {
            val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val selectedAddress: Address = list[0]
            val placeName =
                selectedAddress.getAddressLine(0).split(",").toTypedArray()[0]
            val placeSubTitle = selectedAddress.getAddressLine(0)

            animateCameraToLocation(location)
            viewModel.address?.latitude = location.latitude
            viewModel.address?.longitude = location.longitude
            viewModel.state.placeTitle.set(placeName)
            viewModel.state.placeSubTitle.set(placeSubTitle)
            viewModel.state.placePhoto.set(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.location_place_holder
                )
            )
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    private fun createMarker(
        markerLatLng: LatLng?
    ) {
        locationMarker?.remove()
        icon = bitmapDescriptorFromVector(context, R.drawable.ic_location_pin)
        markerLatLng?.let {
            markerOptions = MarkerOptions()
                .icon(icon)
                .position(it)
            locationMarker = mMap?.addMarker(markerOptions)
        }

    }

    protected fun animateCameraToLocation(location: LatLng) {
        createMarker(location)
        val cameraPosition: CameraPosition = CameraPosition.Builder()
            .target(location)
            .zoom(defaultZoom.toFloat()).build()
        mMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition)
        )
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

    private fun getCurrentPlaceLikelihoods() {
        val placeFields = listOf(
            Place.Field.NAME, Place.Field.ADDRESS,
            Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS
        )

        val request = FindCurrentPlaceRequest.builder(placeFields).build()
        val placeResponse = placesClient?.findCurrentPlace(request)
        placeResponse?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = task.result
                response?.placeLikelihoods?.let {
                    for (placeLikelihood in it.iterator()) {
                        val currentPlace = placeLikelihood.place
                        if (currentPlace.attributions != null) {
                            currentPlace.attributions?.joinToString(" ")
                        }
                        val markerLatLng = currentPlace.latLng
                        if (currentPlace.address != null) {
                            viewModel.state.placeSubTitle.set(currentPlace.address.toString())
                        }

                        viewModel.state.placeTitle.set(currentPlace.address)
                        createMarker(markerLatLng)
                        mDefaultLocation?.let {
                            animateCameraToLocation(it)
                        }

                        if (!currentPlace.photoMetadatas.isNullOrEmpty() && currentPlace.photoMetadatas?.size ?: 0 > 0) {
                            attemptFetchPhoto(currentPlace)
                        } else {
                            viewModel.state.isShowLocationCard.set(true)
                        }
                        break
                    }
                }
            }
        }
    }

    private fun attemptFetchPhoto(place: Place) {
        val photoMetaData = place.photoMetadatas
        if (photoMetaData != null && photoMetaData.isNotEmpty()) {
            fetchPhoto(photoMetaData[0])
        }
    }

    private fun fetchPhoto(photoMetadata: PhotoMetadata) {
        val photoRequestBuilder = FetchPhotoRequest.builder(photoMetadata)

        val photoTask = placesClient?.fetchPhoto(photoRequestBuilder.build())

        photoTask?.addOnSuccessListener { response ->

            viewModel.state.placePhoto.set(response.bitmap)
//            MyUserManager.addressPhotoUrl = viewModel.state.placePhoto.get()
        }

        photoTask?.addOnFailureListener { exception ->
            exception.printStackTrace()
            viewModel.state.loading = false
        }

        photoTask?.addOnCompleteListener {
            viewModel.state.loading = false
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = (10000 / 2).toLong()
        mFusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            if (!locationResult.locations.isNullOrEmpty()) {
                mDefaultLocation = LatLng(
                    locationResult.locations.first().latitude,
                    locationResult.locations.first().longitude
                )
                if (mMap != null) {
                    setupMapOptions()
                    stopLocationUpdates()
                }
            }
        }
    }

    private fun stopLocationUpdates() {
        mFusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

}