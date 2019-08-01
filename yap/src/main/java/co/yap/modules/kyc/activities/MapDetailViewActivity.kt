package co.yap.modules.kyc.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.yap.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
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
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.ByteArrayOutputStream
import java.util.*


class MapDetailViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "MapDetailViewActivity"

    private lateinit var mMap: GoogleMap
    private val DEFAULT_ZOOM = 16
    private var mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    lateinit var icon: BitmapDescriptor
    private lateinit var placesClient: PlacesClient

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private var mLocationPermissionGranted: Boolean = false
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mLastKnownLocation: Location
    var animationFrequency: Int = 1                 //can be set to 2000

    var markerSnippet: String = ""
    var placeName: String = ""
    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap? = null
    lateinit var markerOptions: MarkerOptions

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MapDetailViewActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        icon = this!!.bitmapDescriptorFromVector(this, R.drawable.ic_pin)!!
        setUpMarker(mDefaultLocation, placeName, markerSnippet)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(applicationContext, apiKey)
        placesClient = Places.createClient(this)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        Glide.with(this)
            .asBitmap().load(R.drawable.black_white_tile)
            .transforms(CenterCrop(), RoundedCorners(15))
            .into(ivLocationPhoto)
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
        markerOptions = MarkerOptions()
            .icon(icon)
             .position(markerLatLng!!)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.addMarker(markerOptions)
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.uiSettings.isCompassEnabled=false
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()),
            animationFrequency,
            null
        )


        mMap.setOnMapClickListener { point ->
            cvLocationCard.visibility = View.VISIBLE
        }


        ivClose.setOnClickListener {
            cvLocationCard.visibility = View.GONE

        }

        if (!(::mLastKnownLocation.isInitialized && mLastKnownLocation != null)) {
            getLocationPermission()
            getDeviceLocation()
        }
    }


    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {

        try {
            val locationResult = mFusedLocationProviderClient.getLastLocation()
            locationResult.addOnSuccessListener(this,
                OnSuccessListener<Location> { location ->
                    if (location != null) {
                        mLastKnownLocation = location
                        Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude())
                        Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude())
                        mDefaultLocation = LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())


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

                        getLocationPermission()
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

        val request = FindCurrentPlaceRequest.builder(placeFields).build()
        val placeResponse = placesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener(this,
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

    private fun getImageUri(context: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
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

        tvAddressTitle.text = this.placeName
        tvAddressSubTitle.text = this.placeTitle
        Glide.with(this)
            .asBitmap().load(placePhoto)
            .transforms(CenterCrop(), RoundedCorners(15))
            .into(ivLocationPhoto)
    }

    private fun pickCurrentPlace() {
        if (mMap == null) {
            return
        }

        getDeviceLocation()
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                }
            }
        }
    }


}