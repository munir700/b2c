package co.yap.modules.kyc.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.yap.R
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
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*


class MapDetailViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "MapDetailViewActivity"

    private lateinit var mMap: GoogleMap
    private val DEFAULT_ZOOM = 15
    private val mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    lateinit var icon: BitmapDescriptor
    private lateinit var mPlacesClient: PlacesClient


    companion object {

        fun newIntent(context: Context): Intent = Intent(context, MapDetailViewActivity::class.java)

    }

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    lateinit var mLastKnownLocation: Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val abc = resources.getDrawable(R.drawable.ic_pin)
//        icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)

        icon = this!!.bitmapDescriptorFromVector(this, R.drawable.ic_pin)!!

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(applicationContext, apiKey)
        mPlacesClient = Places.createClient(this)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(25.276987, 55.296249)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney").icon(icon))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.uiSettings.isZoomControlsEnabled = false

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.276987, 55.296249), 13f), 1, null)

        pickCurrentPlace()
        mMap.setOnMapClickListener { point ->
            Toast.makeText(applicationContext, point.toString(), Toast.LENGTH_SHORT).show()
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
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude()
                                ), DEFAULT_ZOOM.toFloat()
                            )
                        )
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        mMap.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat())
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

        val request = FindCurrentPlaceRequest.builder(placeFields).build()
        val placeResponse = mPlacesClient.findCurrentPlace(request)
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
                            }
                            var title: String = currPlace.name!!
                            var currentAddress: String = currPlace.address!!


                            mMap.addMarker(
                                MarkerOptions()
                                    .icon(icon)
                                    .title(title)
                                    .position(markerLatLng!!)
                                    .snippet(markerSnippet)
                            )
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng))

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

    private fun pickCurrentPlace() {
        if (mMap == null) {
            return
        }

        getDeviceLocation()
    }

}