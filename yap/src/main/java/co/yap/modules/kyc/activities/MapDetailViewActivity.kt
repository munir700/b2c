package co.yap.modules.kyc.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.widgets.photokit.RoundedCornersTransformation
import com.bumptech.glide.Glide
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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.*
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

    var placeName: String = ""
    var pictureURL: String = "abc"
    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap? = null
    var plcaOhotoUri: Uri? = null

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MapDetailViewActivity::class.java)

        //

//
//        PhotoMetadata
//        {
//            attributions = < a href =
//                "https://maps.google.com/maps/contrib/108951521001893080162/photos" >
//                        Md Emdadullah < / a >,
//            height = 1067,
//            width = 1600,
//            photoReference = CmRaAAAAf3mAHblb83dfK_4Vy4vnc56wrexiUtSWl_wAVXfuTR6lCpMNhI6ai_TGoZDS1X2WWvXPgjg6k14J7_WhCSqUMWJ8YWSN-Pt6uVNfqxVD-9NVnUSMxpkOfWyyX406PNRaEhDiBWIPJ5D2J0zZfmbnhtGhGhSRy_Bk7ayDnmpxZJjpomw-7LhNbw
//        }

        //
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        icon = this!!.bitmapDescriptorFromVector(this, R.drawable.ic_pin)!!

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(applicationContext, apiKey)
        placesClient = Places.createClient(this)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        Picasso.get()
            .load(R.drawable.black_white_tile)
            .resize(90, 90)
            .transform(RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL))
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.addMarker(MarkerOptions().position(mDefaultLocation).title("Marker in Sydney").icon(icon))
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()), 1500, null)
        mMap.setOnMapClickListener { point ->
            Toast.makeText(applicationContext, point.toString(), Toast.LENGTH_SHORT).show()
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
                                var photoMetadata: PhotoMetadata = currPlace.getPhotoMetadatas()!!.get(0)

                                ///
//                                getPhotoz(photoMetadata)
                                attemptFetchPhoto(currPlace)
//                                setImageBitmap
                            }

                            placeName = currPlace.name!!
                            placeTitle = currPlace.address!!
                            var currentAddress: String = currPlace.address!!
//                            placeTitle=currPlace.name!!
//here add picture
//                            if (null!=plcaOhotoUri) {
//
//                                Picasso.get()
//                                    .load(plcaOhotoUri)
//
//                                    .placeholder(R.drawable.black_white_tile)
//                                    .resize(90, 90)
//                                    .transform(
//                                        RoundedCornersTransformation(
//                                            10,
//                                            0,
//                                            RoundedCornersTransformation.CornerType.ALL
//                                        )
//                                    )
//                                    .into(ivLocationPhoto)
//                            }
//


                            mMap.addMarker(
                                MarkerOptions()
                                    .icon(icon)
                                    .title(placeName)
                                    .position(markerLatLng!!)
                                    .snippet(markerSnippet)
                            )
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
//        photoView!!.setImageBitmap(null)
//        setLoading(true).............///////////loader

//        val customPhotoReference = customPhotoReference
//        if (!TextUtils.isEmpty(customPhotoReference)) {
//            photoMetadata = PhotoMetadata.Builder.build()
//        }

        val photoRequestBuilder = FetchPhotoRequest.builder(photoMetadata)

        val photoTask = placesClient!!.fetchPhoto(photoRequestBuilder.build())

        photoTask.addOnSuccessListener { response ->
            //            photoView!!.setImageBitmap(response.bitmap)
//            StringUtil.prepend(responseView, StringUtil.stringify(response.bitmap))

            placePhoto = response.bitmap
            plcaOhotoUri = bitmapToFile(placePhoto!!)
            if (null != plcaOhotoUri) {
//                var drawable: Int = BitmapDrawable(getResources(), placePhoto)
//                Picasso.get().load(R.drawable.black_white_tile).into(ivLocationPhoto);
//                val resourceId = data.resourceId
//                Picasso.get()
//                    .asBitmap
//                    .load(R.drawable.black_white_tile)
//                    .error(R.drawable.black_white_tile)
//
//                    .placeholder(drawable)
//                    .resize(90, 90)
//                    .transform(
//                        RoundedCornersTransformation(
//                            10,
//                            0,
//                            RoundedCornersTransformation.CornerType.ALL
//                        )
//                    )
//                    .into(ivLocationPhoto)

//                ivLocationPhoto.setImageBitmap(bitmap
//                ivLocationPhoto.trans
            }
//            plcaOhotoUri= placePhoto?.let { getImageUri(this, it) }


//            Glide.with(this)
//                .load("http://example.com/imageurl")
//                .Bitmap
//                .into(ivLocationPhoto)

            Glide.with(this)
                .asBitmap().load(placePhoto)

                .into(ivLocationPhoto)
        }

        photoTask.addOnFailureListener { exception ->
            exception.printStackTrace()
//            StringUtil.prepend(responseView, "Photo: " + exception.message)
            val strPic = exception.message
        }

        photoTask.addOnCompleteListener {
            //                response -> setLoading(false)
        }
    }

    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap: Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
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